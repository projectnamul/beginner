# Api Payload
You can make and use class to make response about several exceptions.

---

## Content of Table
[1. Classes & Interfaces](#classes-and-interfaces)

[2. How to use](#how-to-use)

[2 - 1. Default](#default)

[2 - 2 Custom](#custom)

----

## Classes and Interfaces
Descriptions of classes and Interfaces

### BaseResponse
- This interface is just response type which you want to send to client. You can make type of server response with implementing this interface. Make a response class and declare variable you want to contain in response.

### BaseErrorCode
- This interface returns data that needs a response. After creating a response type with the BaseResponse interface, extend the BaseErrorCode interface to create an interface that can return the data that needs a response that you created. Then, implement other interfaces with the interface you implemented with BaseErrorCode.

### FailureResponseWriter
- This interface responds with throwable (Exception) and BaseErrorCode. Implement to return BaseResponse using BaseErrorCode's method and Throwable.

### AdditionalExceptionHandler
- This interface performs additional logic except for generating response by implementing this interfaace. Like logging, send discord or alarm... etc.
> If you add it to ErrorCodeExceptionHandler with ErrorCodeExceptionHandlerConfigurer, you must control consistency problem. Because it runs asynchronously in ErrorCodeExceptionHandler's handle method.

### WebRequest & WebResponse
- This interface offer some method can use in AdditionalExceptionHandler. You can implement it yourself or use a class already implemented from another module.

### ErrorCodeExceptionHandler
- Based on the transmitted exception, this class finds the appropriate BaseErrorCode, generates responses, and asynchronously executes additional logic. It also provides a way to find the right BaseErrorCode for Throwable.

### ErrorCodeExceptionHandlerConfigurer
- This class make ErrorCodeExceptionHandler with several methods. Developer can set ErrorCodeExceptionHandler with this class by using classes that developer created.

### ServerApplicationException
- This class is just exception class that contain BaseErrorCode. Developer can make another exception class by extending it. Also, It can get cause(Throwable) for stacktrace 


## How to use
### Default
Just add one config file. 

Because `DefaultResponseWriter` is already registered in Bean, If you have not registered class which implement that `FailureResponseWriter` interfaces to bean

```java
@Configuration
public class ErrorHandlerConfig {

    @Bean
    public ErrorCodeExceptionHandler<DefaultBaseErrorCode> errorCodeExceptionHandler(
            FailureResponseWriter<DefaultBaseErrorCode> failureResponseWriter,
            List<AdditionalExceptionHandler<DefaultBaseErrorCode>> additionalExceptionHandlerList
    ) {
        ErrorCodeExceptionHandlerConfigurer<DefaultBaseErrorCode> configurer = new ErrorCodeExceptionHandlerConfigurer<>(failureResponseWriter);
        return configurer
                .addServerApplication(DefaultResponseErrorCode.BAD_REQUEST)
                .addGlobalException(DefaultResponseErrorCode.INTERNAL_SERVER_ERROR)
                .addAdvice(CustomException.class, CustomErrorCode.CUSTOM_ERROR_CODE)
                // ...
                .addAdditionalExceptionHandlers(additionalExceptionHandlerList)
                .build();
    }

}
```

### How to use DefaultResponse in RestController

You can use static method of DefaultResponse or constructor

```java
@RestController
public class TestController {

    @GetMapping("/success")
    public DefaultResponse<String> success() {
         return DefaultResponse.ok("success");
    }
}
```

or
```java
    @GetMapping("/success")
    public BaseResponse success() {
        return successResponseWriter.onSuccess(DefaultResponseSuccessCode.OK, "Success");
    }
```

> ⚠️ Setting the return value to BaseResponse may prevent the Swagger's response format from appearing properly.

### How to add AdditionalExceptionHandler
You can add additional logic with implementing AdditionalExceptionHandler and add it to bean.

```java
public class LogAdditionalHandler implements AdditionalExceptionHandler<DefaultBaseErrorCode> {

    @Override
    public void doHandle(WebRequestWrapper webRequestWrapper, WebResponseWrapper webResponseWrapper, Throwable e, DefaultBaseErrorCode defaultBaseErrorCode) {
        log.info("""
                        Cookie: {},
                        URI: {},
                        Header: {},
                        Method: {},
                        Address: {},
                        UserAgent: {},
                        """,
                webRequestWrapper.getCookie("JSESSIONID"),
                webRequestWrapper.getRequestURI(),
                webRequestWrapper.getHeader("content-type"),
                webRequestWrapper.getMethod(),
                webRequestWrapper.getRemoteAddress().get(),
                webRequestWrapper.getUserAgent().get());

    }
}
```

### How to add Additional DefaultBaseErrorCode
You can make class or enum with implementing DefaultBaseErrorCode and methods `getHttpStatus(), getCode(), getMessage()`
```java
@Getter
@AllArgsConstructor
public enum TestErrorCode implements DefaultBaseErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "TEST404", "테스트 404 에러 발생")
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
```

### In business logic

```java
@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readonly = true)
    public Article findArticle(Long articleId) {
        return articleRepository.findById(articleId).orElseThrow(() -> new ServerApplicationException(DefaultResponseErrorCode.NOT_FOUND));
    }
}
```

## Custom
### How to make your custom response

You can make custom response by implementing BaseResponse, BaseErrorCode and FailureResponseWriter.

1. Make custom response class with BaseResponse
2. Make custom interface with BaseErrorCode. It has several method which you need to create response
3. Make custom FailureResponseWriter with BaseResponse, BaseErrorCode which you implemented.
4. Register your custom Writer in bean and create ErrorCodeExceptionHandler with Configurer.

> Use BaseResponse to make frame of the response and expand the BaseErrorCode to define the data needed to generate the response as a method. Using the expanded BaseErrorCode, Implement the FailureResponseWriter which creates the response. Then, If you implement the Custom BaseErrorCode class and pass it to ServerApplicationException, the ExceptionAdvice can make Response and return it.

### Example
#### BaseResponse
Let's assume that we implemented it as below.

```java
@Getter
public class CustomResponse<T> implements BaseResponse { // you can use AbstractBaseResponse

    private final HttpStatus httpStatus;
    private final String message;
    private final T result;

    public CustomResponse(HttpStatus httpStatus, String message, T result) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.result = result;
    }

    public static <T> CustomResponse<T> ok(T result) {
        return new CustomResponse<>("성공", result);
    }
}
```

#### BaseErrorCode
You just implement the method needed to generate the response. (`HttpStatus` getter method is already exist in BaseErrorCode.)

```java
public interface CustomBaseErrorCode extends BaseErrorCode {
    String getMessage();
}
```

#### CustomResponseFailureWriter
You implement `CustomResponseFailureWriter` to make CustomResponse with CustomBaseErrorCode

```java
@Component
public class CustomFailureResponseWriter implements FailureResponseWriter<CustomBaseErrorCode> {

    @Override
    public BaseResponse onFailure(Throwable e, CustomBaseErrorCode code) {
        return new CustomResponse<>(code.getHttpStatus(), code.getMessage(), null);
    }
}
```

#### Error Code Class
You can implement class with CustomBaseErrorCode to process error with it.

```java
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CustomErrorCode implements CustomBaseErrorCode {

    ERROR(HttpStatus.BAD_REQUEST, "Error"),
    BAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "BAD_ERROR"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "Not found resource"),
    // ...
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
```

#### Config

```java
@Configuration
public class ExceptionAdviceConfig {
    @Bean
    ErrorCodeExceptionHandler<CustomBaseErrorCode> customErrorCodeExceptionHandler(
            FailureResponseWriter<CustomBaseErrorCode> failureResponseWriter,
            List<AdditionalExceptionHandler<CustomBaseErrorCode>> additionalExceptionHandlers
    ) {
        ErrorCodeExceptionHandlerConfigurer<CustomBaseErrorCode> configurer = new ErrorCodeExceptionHandlerConfigurer<>(failureResponseWriter);
        return configurer
                .addServerApplication(CustomErrorCode.ERROR)
                .addGlobalException(CustomErrorCode.BAD_ERROR)
                .addAdvice(CustomNotFoundException.class, CustomErrorCode.NOT_FOUND)
                // ...
                .addAdditionalExceptionHandlers(additionalExceptionHandlerList)
                .build();
    }
}
```

#### In business logic

```java
@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readonly = true)
    public Article findArticle(Long articleId){
        return articleRepository.findById(articleId).orElseThrow(() -> new CustomException(CustomErrorCode.NOT_FOUND));
    }
}
```

#### AdditionalExceptionHandler
if you make and register it  by using `addAdditionalExceptionHandler` or `addAdditionalExceptionHandlers` method of configurer, It will operate asynchronously in the middle of error processing logic.
```java
@Slf4j
@Component
public class ExceptionAdviceLoggingHandler implements AdditionalExceptionHandler<CustomBaseErrorCode> {

    @Override
    public void doHandle(HttpServletRequest request, HttpServletResponse response, Exception e, CustomBaseErrorCode code) {
        log.warn("Error occur: class: {}, message: {}", e.getClass(), code.getMessage());
    }
}
```