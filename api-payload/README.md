# Api Payload
The module to unify response you can use DefaultResponse and your custom response

## Default
### How to add ExceptionAdvice with DefaultResponse
Just add one config file. 

Because DefaultResponseWriteUtil and DefaultResponseWriter is already registered in Bean, If you have not registered a bean object which implement that interfaces

```java
@Configuration
@RequiredArgsConstructor
public class ExceptionAdviceConfig {

    @Bean
    ExceptionAdviceConfigurer exceptionAdviceConfigurer(ExceptionHandlerRegistry exceptionHandlerRegistry) {
        ExceptionAdviceConfigurer exceptionAdviceConfigurer = new ExceptionAdviceConfigurer(exceptionHandlerRegistry);
        return exceptionAdviceConfigurer
                .withDefault()
                .enableMethodArgumentNotValidExceptionAdvice(DefaultResponseErrorCode._BAD_REQUEST)
                .enableConstraintViolationExceptionAdvice(DefaultResponseErrorCode._BAD_REQUEST)
                .enableGlobalExceptionAdvice(DefaultResponseErrorCode._INTERNAL_SERVER_ERROR)
                ;
    }
}
```

### How to use DefaultResponse in RestController

You can use DefaultResponseWriter which is already registered in bean with DI

```java
@RestController
@RequiredArgsConstructor
public class TestController {

    private final DefaultSuccessResponseWriter responseWriter;

    @GetMapping("/success")
    public DefaultResponse<String> success() {
        return responseWriter.ok("success"); // you can use DTO class instead of "success"
    }
}
```

## Custom
### How to make your custom response

You can make custom response by implementing BaseResponse, ErrorReasonDTO, SuccessReasonDTO, ResponseWriteUtil, ResponseWriter and ExceptionAdviceHandler

1. Make custom BaseResponse
2. Make custom ErrorReasonDTO, SuccessReasonDTO
3. Make custom SuccessResponseWriter, FailureResponseWriter 
4. Register your custom Util, Handler, Writer in bean