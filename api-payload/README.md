# Api Payload
The module to unify response you can use DefaultResponse and your custom response

## Default
### How to add ExceptionAdvice with DefaultResponse
Just add one config file. 

Because DefaultResponseWriteUtil and DefaultResponseWriter is already registered in Bean, If you have not registered a bean object which implement that interfaces

```java
@Configuration
public class ExceptionAdviceConfig {

    @Bean
    ExceptionAdviceConfigurer<DefaultResponseErrorReasonDTO> defaultExceptionAdviceConfigurer(FailureResponseWriter<DefaultResponseErrorReasonDTO> failureResponseWriter) {
        return new DefaultExceptionAdviceConfigurer(failureResponseWriter);
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
or

```java
import org.namul.api.payload.response.DefaultResponse;

@RestController
public class TestController {

    @GetMapping("/success")
    public DefaultResponse<String> success() {
        return DefaultResponse.ok("success"); // you can use DTO class instead of "success"
    }
}
```

## Custom
### How to make your custom response

You can make custom response by implementing BaseResponse, ErrorReasonDTO, SuccessReasonDTO, ResponseWriteUtil, ResponseWriter and ExceptionAdviceHandler

1. Make custom BaseResponse
2. Make custom ErrorReasonDTO, SuccessReasonDTO
3. Make custom SuccessResponseWriter, FailureResponseWriter 
4. Register your custom Handler, Writer in bean

### how to add custom handler in ExceptionAdvice
```java
@Configuration
@RequiredArgsConstructor
public class ExceptionAdviceConfig {

    private final CustomFailureResponseWriter customFailureResponseWriter;
    private final CustomServerApplicationExceptionHandler customServerApplicationExceptionHandler;

    @Bean
    ExceptionAdviceConfigurer<CustomErrorReasonDTO> exceptionAdviceConfigurer() {
        ExceptionAdviceConfigurer<CustomErrorReasonDTO> configurer = new ExceptionAdviceConfigurer<>(customFailureResponseWriter); // Apply response writer when exception occurs
        configurer.addConstraintViolation(CustomErrorCode.ERROR.getReason()); // apply with default handler
        configurer.addMethodArgumentNotValid(CustomErrorCode.ERROR.getReason());
        configurer.addServerApplication(customServerApplicationExceptionHandler, CustomErrorCode.ERROR.getReason()); // apply custom handler
        configurer.addGlobalException(CustomErrorCode.BAD_ERROR.getReason());
        return configurer;
    }

}
```

## ExceptionAdvice Message sender
You can configure message sender with properties and generator. If you don't implement Generator, it will be default.
```YAML
beginner:
  api:
    payload:
      discord:
        scope:
          - Exception
          - ServerApplicationException
        web-hook-url: ${DISCORD_WEBHOOK_URL}
        enable: false
      slack:
        enable: true
        scope:
          - ServerApplicationException
        web-hook-url: ${SLACK_WEBHOOK_URL}

```

If you add enable, scope, url, you can set it as you want. The reason for setting it as YML is to allow you to change scope, url, and enable without code modification.