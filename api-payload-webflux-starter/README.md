# api-payload-webflux-starter

**The Ultimate Spring Boot Starter for Standardized API Payloads in Reactive Environments**

`api-payload-webflux-starter` is a comprehensive bundler that combines the foundational logic of `api-payload-core` with the Spring WebFlux-specific implementations of `api-payload-webflux`. By leveraging Spring Boot's **Auto-configuration**, it allows developers to integrate a robust, non-blocking API response system with zero manual bean registration.

---

## ✨ Why Use This Starter?

This module is designed to eliminate boilerplate code and provide a "plug-and-play" experience for WebFlux developers:

* **Auto-Registration**: Automatically configures reactive success and failure response writers as Spring Beans.
* **Version Alignment**: Maintains synchronized versioning between `api-payload-core` and `api-payload-webflux`, ensuring guaranteed compatibility and a consistent feature set across the reactive implementation.
* **Ready-to-use Reactive Advice**: Activates `ExceptionRestControllerAdvice` out of the box to intercept and standardize all exceptions within the reactive pipeline.
* **Reactive-Native Integration**: Seamlessly bridges `ServerWebExchange` with the framework's internal `WebRequest/ResponseWrapper` specifically for non-blocking environments.
* **Non-blocking Error Handling**: Automatically configures the `ReactiveErrorCodeExceptionHandler` to ensure error processing remains within the Project Reactor stream.
---

## 💡 Quick Start

Add the following dependency to your `build.gradle` (or `pom.xml`):

### Gradle
```gradle
dependencies {
    // Includes Core, WebFlux, and Auto-configuration
    implementation 'org.namul:api-payload-webflux-starter:0.9.3' 
}
```

### Maven
```XML
<dependency>
<groupId>org.namul</groupId>
<artifactId>api-payload-webflux-starter</artifactId>
<version>0.9.3</version>
</dependency>
```

---


## ⚙️ Configuration
The starter automatically detects all AdditionalExceptionHandler beans in the application context and registers them to the reactive exception handler, ensuring any side-effect logic is executed safely.

---

## 🔗 Related Modules
api-payload-core: Foundation interfaces and core engine.

api-payload-webflux: Reactive implementation and wrappers.

---

© 2026 Project Namul - Beginner