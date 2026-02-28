# api-payload-webmvc-starter

**The Ultimate Spring Boot Starter for Standardized API Payloads & Global Exception Handling**

`api-payload-webmvc-starter` is a comprehensive bundler that combines the foundational logic of `api-payload-core` with the Spring WebMVC-specific implementations of `api-payload-webmvc`. By leveraging Spring Boot's **Auto-configuration**, it allows developers to integrate a robust API response system with zero manual bean registration.

---

## ✨ Why Use This Starter?

This module is designed to eliminate boilerplate code and provide a "plug-and-play" experience for Spring Boot developers:

* **Auto-Registration**: Automatically configures `DefaultSuccessResponseWriter` and `DefaultFailureResponseWriter` as Spring Beans.
* **Ready-to-use Advice**: Activates `ExceptionRestControllerAdvice` out of the box to intercept and standardize all controller exceptions.
* **Web-Native Integration**: Seamlessly bridges `HttpServletRequest/Response` with the framework's internal `WebRequest/ResponseWrapper` for cleaner code.
* **Asynchronous Processing**: Automatically detects and registers all `AdditionalExceptionHandler` beans to run side-effect logic (logging, notifications) asynchronously.

---

## 💡 Quick Start

Add the following dependency to your `build.gradle` (or `pom.xml`):

### Gradle
```gradle
dependencies {
    // Includes Core, WebMVC, and Auto-configuration
    implementation 'org.namul:api-payload-webmvc-starter:0.9.1' 
}
```

### Maven
```XML
<dependency>
<groupId>org.namul</groupId>
<artifactId>api-payload-webmvc-starter</artifactId>
<version>0.9.1</version>
</dependency>
```

---

## 🔗 Related Modules
- api-payload-core: Foundation interfaces and core engine.

- api-payload-webmvc-starter: Auto-configuration for instant integration.

---
© 2026 [Project Namul - Beginner](https://github.com/projectnamul/beginner)