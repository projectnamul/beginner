# api-payload-webmvc

**Spring WebMVC Implementation for Standardized API Payloads**

This module provides the practical implementation of `api-payload-core` specifically for the **Spring WebMVC** environment. It includes wrappers for Servlet-based requests/responses and automated advice for global exception handling.

---

## 🛠️ Key Classes & Components

### 1. HttpServlet Wrapper Classes
These classes bridge the gap between standard Java Servlet APIs and the framework's internal logic.
* **`HttpServletWebRequestWrapper`**: Implements `WebRequestWrapper` using `HttpServletRequest`. It provides a unified way to access request metadata (Headers, URI, Method, etc.) without direct dependency on the Servlet API.
* **`HttpServletWebResponseWrapper`**: Implements `WebResponseWrapper` using `HttpServletResponse`, allowing the framework to write standardized payloads directly to the HTTP response body.

### 2. HttpServletErrorCodeExceptionHandlerConfigurer
A specialized implementation of `ErrorCodeExceptionHandlerConfigurer`.
* It simplifies the registration of common errors that typically occur within the **Dispatcher Servlet** environment.
* It provides a fluent API to map various exception types to their corresponding `BaseErrorCode` definitions.

### 3. ExceptionRestControllerAdvice
A pre-implemented `@RestControllerAdvice` that serves as the global interceptor for exceptions.
* **Plug-and-Play**: Once registered as a Bean (automatically handled by the Starter), it catches all unhandled exceptions and converts them into standardized JSON payloads.
* **Extensible**: If you need custom behavior, you can easily extend this class and override its methods.

---

## 💡 Advanced Usage



### Customizing the Exception Handler
You can use the `HttpServlet` specific configurer to define how different exceptions should be mapped:

```java
@Configuration
public class WebExceptionConfig {

    @Bean
    public ErrorCodeExceptionHandler<DefaultBaseErrorCode> errorCodeExceptionHandler(
            FailureResponseWriter<DefaultBaseErrorCode> failureWriter,
            List<AdditionalExceptionHandler<DefaultBaseErrorCode>> handlers
    ) {
        // Specifically designed for Spring WebMVC environment
        return new HttpServletErrorCodeExceptionHandlerConfigurer<>(failureWriter)
                .addServerApplication(DefaultResponseErrorCode.BAD_REQUEST)
                .addGlobalException(DefaultResponseErrorCode.INTERNAL_SERVER_ERROR)
                .addAdditionalExceptionHandlers(handlers)
                .build();
    }
}
```
---
## ⚠️ Recommendation: Use Starter
While you can manually register these beans, it is highly recommended to use the [api-payload-webmvc-starter](https://github.com/projectnamul/beginner/tree/develop/api-payload-webmvc-starter) module. The Starter automatically handles the registration of writers and exception advice, allowing you to focus on business logic with zero manual configuration.

---

## 🔗 Related Modules
- api-payload-core: Foundation interfaces and core engine.

- api-payload-webmvc-starter: Auto-configuration for instant integration.

---
© 2026 [Project Namul - Beginner](https://github.com/projectnamul/beginner)