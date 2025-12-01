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
    ExceptionAdvice defaultExceptionAdviceConfigurer(
            FailureResponseWriter failureResponseWriter,
            List<AdditionalExceptionHandler> additionalExceptionHandlers
    ) {
        return new DefaultExceptionAdviceConfigurer(failureResponseWriter)
                .addAdditionalExceptionHandlers(additionalExceptionHandlers)
//                .addAdditionalExceptionHandler(logAdditionalExceptionHandler())
                .build();
    }

    // If you want to make log
    @Bean
    LogAdditionalExceptionHandler logAdditionalExceptionHandler() {
        return new LogAdditionalExceptionHandler();
    }
}
```

### How to use DefaultResponse in RestController

You can use DefaultResponseWriter which is already registered in bean with DI

```java
import org.namul.api.payload.response.DefaultResponse;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final DefaultSuccessResponseWriter responseWriter;

    @GetMapping("/success")
    public DefaultResponse<String> success() {
        return responseWriter.ok("success"); // you can use DTO class instead of "success"
        // or
        // return DefaultResponse.ok("success");
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
    @Bean
    ExceptionAdvice defaultExceptionAdviceConfigurer(
                FailureResponseWriter failureResponseWriter, // Custom FailureResponseWriter
                List<AdditionalExceptionHandler> additionalExceptionHandlers
            ) {
        return new ExceptionAdviceConfigurer(failureResponseWriter)
            .withDefault(CustomErrorCode.ERROR, CustomErrorCode.BAD_ERROR) // register error code for 4xx, 5xx error
            .addMissingPathVariable(CustomErrorCode.BAD_ERROR) // Register BaseErrorCode for MissingPathVariableException
            .addAdditionalExceptionHandlers(additionalExceptionHandlers) // Register additional handler
            .build();
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