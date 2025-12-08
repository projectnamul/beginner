# Api Payload
The module to unify response you can use DefaultResponse or your custom response

## Default
### How to add ExceptionAdvice with DefaultResponse
Just add one config file. 

Because `DefaultResponseWriter` is already registered in Bean, If you have not registered class which implement that `FailureResponseWriter` interfaces to bean

```java
@Configuration
public class ExceptionAdviceConfig {

    @Bean
    ExceptionAdvice<DefaultBaseErrorCode> defaultExceptionAdviceConfigurer(
            FailureResponseWriter<DefaultBaseErrorCode> failureResponseWriter,
            List<AdditionalExceptionHandler<DefaultBaseErrorCode>> additionalExceptionHandlers
    ) {
        return new DefaultExceptionAdviceConfigurer(failureResponseWriter)
                .addAdditionalExceptionHandlers(additionalExceptionHandlers)
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

### How to add AdditionalExceptionHandler
You can add additional logic with implementing AdditionalExceptionHandler and add it to bean.

```java
@Slf4j
@Component
public class ExceptionAdviceLoggingHandler implements AdditionalExceptionHandler<DefaultBaseErrorCode> {

    @Override
    public void doHandle(HttpServletRequest request, HttpServletResponse response, Exception e, DefaultBaseErrorCode code) {
        log.warn("Error occur: class: {}, message: {}", e.getClass(), code.getMessage());
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

1. Make custom BaseResponse
2. Make custom BaseErrorCode
3. Make custom FailureResponseWriter 
4. Register your custom Writer in bean and implement custom BaseErrorCode

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
    public BaseResponse onFailure(Exception e, CustomBaseErrorCode code) {
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
    ExceptionAdvice<CustomBaseErrorCode> defaultExceptionAdviceConfigurer(
            FailureResponseWriter<CustomBaseErrorCode> failureResponseWriter,
            List<AdditionalExceptionHandler<CustomBaseErrorCode>> additionalExceptionHandlers
    ) {
        return new ExceptionAdviceConfigurer<>(failureResponseWriter)
                .withDefault(CustomErrorCode.ERROR, CustomErrorCode.BAD_ERROR)
                .addAdditionalExceptionHandlers(additionalExceptionHandlers)
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
        return articleRepository.findById(articleId).orElseThrow(() -> new ServerApplicationException(CustomErrorCode.NOT_FOUND));
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