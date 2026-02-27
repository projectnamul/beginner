# api-payload-core

**The Core Engine for Standardized API Responses and Exception Handling**

`api-payload-core` provides the foundational interfaces and engine logic to manage consistent API response structures and centralized exception handling. It is designed to be framework-agnostic, allowing for flexible implementation across various environments.

---

## 🛠️ Classes and Interfaces

### 1. Response Framework
* **`BaseResponse`**: The root interface for all server responses. Implement this to define your standardized JSON envelope (e.g., status, message, result).
* **`BaseErrorCode`**: An interface that defines the data required for a response. It links specific error states to their corresponding HTTP status and messages.

### 2. Exception Handling Engine
* **`ErrorCodeExceptionHandler`**: The core processor that intercepts exceptions, identifies the appropriate `BaseErrorCode`, and generates a `BaseResponse`. It also handles asynchronous execution of additional logic.
* **`ErrorCodeExceptionHandlerConfigurer`**: A fluent builder class to configure the `ErrorCodeExceptionHandler` with custom error mappings and handlers.
* **`ServerApplicationException`**: A specialized runtime exception that carries a `BaseErrorCode`. Use this to throw business-specific errors within your service logic.

### 3. Response Writing & Extension
* **`FailureResponseWriter`**: Responsible for converting a `Throwable` and `BaseErrorCode` into a final `BaseResponse`.
* **`AdditionalExceptionHandler`**: An interface for side-effect logic (e.g., logging, Discord/Slack alerts) that runs asynchronously during the exception handling process.
* **`WebRequest` & `WebResponse`**: Abstractions for environment-specific request and response objects, allowing the engine to remain decoupled from specific technologies like Servlet or WebFlux.

---

## 💡 How to Use



### 1. Basic Configuration
You can set up the global exception handler using the `Configurer`.

```java
@Configuration
public class ErrorHandlerConfig {

    @Bean
    public ErrorCodeExceptionHandler<DefaultBaseErrorCode> errorCodeExceptionHandler(
            FailureResponseWriter<DefaultBaseErrorCode> failureResponseWriter,
            List<AdditionalExceptionHandler<DefaultBaseErrorCode>> additionalHandlers
    ) {
        return new ErrorCodeExceptionHandlerConfigurer<>(failureResponseWriter)
                .addServerApplication(DefaultResponseErrorCode.BAD_REQUEST)
                .addGlobalException(DefaultResponseErrorCode.INTERNAL_SERVER_ERROR)
                .addAdvice(CustomException.class, CustomErrorCode.CUSTOM_ERROR_CODE)
                .addAdditionalExceptionHandlers(additionalHandlers)
                .build();
    }
}
```
---
### 2. Throwing Exceptions in Business Logic
Simply throw the `ServerApplicationException` with a predefined error code. The engine will catch it and map it to the correct response automatically.

```java
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public Article findArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new ServerApplicationException(DefaultResponseErrorCode.NOT_FOUND));
    }
}
```

---

### 3. Implementing Additional Logic (Optional)
Create an asynchronous handler for tasks like logging, Discord notifications, or analytics by implementing AdditionalExceptionHandler.

```java
@Component
public class LogAdditionalHandler implements AdditionalExceptionHandler<DefaultBaseErrorCode> {
    @Override
    public void doHandle(WebRequestWrapper req, WebResponseWrapper res, Throwable e, DefaultBaseErrorCode code) {
        log.info("Error occurred at URI: {}, Method: {}", 
            req.getRequestURI(), 
            req.getMethod());
    }
}
```

---

## ⚙️ Customization

You can define your own response format by implementing `BaseResponse`, `BaseErrorCode`, and `FailureResponseWriter`. This allows you to customize exactly how the JSON payload is structured for your specific business requirements.



### Step-by-Step Customization
1.  **Define the Frame**: Implement `BaseResponse` to create your custom JSON structure (e.g., adding a `timestamp` or `transactionId`).
2.  **Define the Data**: Extend `BaseErrorCode` to include specific methods required for your custom response (e.g., `getInternalMessage()`).
3.  **Implement the Writer**: Implement `FailureResponseWriter` using your custom response and error code classes to define the final output logic.
4.  **Register**: Register your custom writer as a Bean and use the `Configurer` to link it to the `ErrorCodeExceptionHandler`.

> [!TIP]
> **Advanced Setup**: Use `BaseResponse` to define the architectural frame of the response and extend `BaseErrorCode` to provide the specific data needed by your custom writers. This separation of concerns ensures that your error logic remains clean and maintainable.

---

## ⚠️ Recommendation: Use a Starter
For most Spring Boot applications, **it is highly recommended to use a dedicated Starter module** (e.g., `api-payload-webmvc-starter`).

The Starter automatically handles the registration of these core components (Writers, Exception Handlers, etc.), allowing you to focus on your business logic with zero manual bean registration and minimal configuration.

---
© 2026 [Project Namul - Beginner](https://github.com/projectnamul/beginner)