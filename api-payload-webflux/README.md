# api-payload-webflux

**Spring WebFlux Implementation for Standardized API Payloads**

This module provides the practical implementation of `api-payload-core` specifically for the **Spring WebFlux** environment. It integrates seamlessly with the Reactive Streams (`Mono`, `Flux`) pipeline, offering non-blocking request/response wrappers and global exception handling mechanisms.

---

## 🛠️ Key Classes & Components

### 1. Reactive Wrapper Classes
These classes bridge the gap between Spring WebFlux's `ServerWebExchange` and the framework's internal logic.
* **`ReactiveWebRequestWrapper`**: Implements `WebRequestWrapper` using `ServerHttpRequest` in `ServerWebExchange`. It provides a unified way to access request metadata in a reactive environment.
* **`ReactiveWebResponseWrapper`**: Implements `WebResponseWrapper` using `ServerHttpResponse` in `ServerWebExchange`, ensuring response metadata is handled within the reactive flow.

### 2. ReactiveErrorCodeExceptionHandler
A specialized exception handler engine optimized for reactive pipelines.
* **Non-blocking Pipeline**: Extends the core handler to wrap result generation in a `Mono<BaseResponse>`, maintaining the reactive stream.
* **`handleToMono`**: Returns the exception processing result as a `Mono`, allowing for declarative integration with Controller Advice or Filters.

### 3. ReactiveErrorCodeExceptionHandlerConfigurer
A specialized implementation of `ErrorCodeExceptionHandlerConfigurer` for the WebFlux environment.
* It simplifies the registration of common reactive errors, such as `WebExchangeBindException` (validation) and `DecodingException`.
* **Fluent API**: Provides a `withDefault` method and various `add` methods for highly readable, chained configuration.

### 4. ExceptionRestControllerAdvice
A pre-implemented `@RestControllerAdvice` that serves as the global exception handler for the reactive web layer.
* **Standardized Response**: Returns a `ResponseEntity<BaseResponse>`, which is automatically handled by Spring WebFlux's reactive result handling mechanism.
* **Plug-and-Play**: Once registered as a Bean, it automatically catches unhandled exceptions across your controllers and transforms them into consistent error responses.

---

## 💡 Advanced Usage

### Customizing the Reactive Exception Handler
You can use the WebFlux-specific configurer to define how reactive exceptions should be mapped:

```java
@Configuration
public class WebFluxExceptionConfig {

    @Bean
    public ReactiveErrorCodeExceptionHandler<DefaultBaseErrorCode> errorCodeExceptionHandler(
            FailureResponseWriter<DefaultBaseErrorCode> failureWriter,
            List<AdditionalExceptionHandler<DefaultBaseErrorCode>> handlers
    ) {
        // Specifically designed for Spring WebFlux (Reactive) environment
        return new ReactiveErrorCodeExceptionHandlerConfigurer<>(failureWriter)
                .withDefault(DefaultResponseErrorCode.BAD_REQUEST, DefaultResponseErrorCode.INTERNAL_SERVER_ERROR)
                .addAdditionalExceptionHandlers(handlers)
                .build();
    }
}
```

---

## 🚀 Key Features for Reactive Environment

* **Static Factory Methods**: Provides intuitive `wrap(exchange)` methods to improve code readability and express the intent of wrapping raw reactive components. (`ReactiveWebRequestWrapper`, `ReactiveWebResponseWrapper`)
* **Support for Reactive Handling**: Offers a `handleToMono` method within `ReactiveErrorCodeExceptionHandler`, enabling seamless integration with Project Reactor's non-blocking pipelines.
* **Pre-configured RestControllerAdvice**: Includes a ready-to-use `@RestControllerAdvice` that bridges reactive exceptions to the standardized `api-payload` format with zero extra code.
* **Specialized Reactive Configurer**: Provides a fluent API through `ReactiveErrorCodeExceptionHandlerConfigurer` with dedicated methods to map WebFlux-specific exceptions (e.g., `WebExchangeBindException`, `DecodingException`) efficiently.
---

## 🔗 Related Modules
api-payload-core: Foundation interfaces and core engine.


---
© 2026 [Project Namul - Beginner](https://github.com/projectnamul/beginner)