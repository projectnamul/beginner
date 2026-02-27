# Beginner

**Standardized Dependency Framework for Spring Boot Applications**

---

[![GitHub License](https://img.shields.io/badge/license-Apache2.0-blue.svg)](./LICENSE)
![Java version](https://img.shields.io/badge/Java%20version-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green)

## 💡 Why Use This?

Developing consistent APIs in Spring Boot often leads to repetitive boilerplate code. **Beginner** provides a pre-configured architecture to handle the essential parts of API development:

* **Unified API Envelopes:** Every response follows a consistent JSON structure for easier client consumption.
* **Centralized Exception Handling:** Automatically map internal business exceptions to standardized HTTP error responses.
* **Starter-based Auto-configuration:** Get up and running instantly with zero manual bean registration using our Starter.
* **Modular Architecture:** Choose between pure logic (Core) or full Spring integration (Starter).

---

## 📦 Project Modules

| Module | Description |
| :--- | :--- |
| **[api-payload-core](./api-payload-core)** | **The Engine.** Provides foundational interfaces and models for responses and exceptions. |
| **[api-payload-webmvc](./api-payload-webmvc)** | **The Implementation.** Contains Spring WebMVC specific utilities, interceptors, and response wrappers. |
| **[api-payload-webmvc-starter](./api-payload-webmvc-starter)** | **The Bundler.** Combines Core and WebMVC with **Auto-configuration** for seamless Spring Boot integration. |

> [!IMPORTANT]
> For detailed class documentation and customization guides, please refer to the individual README files in each module directory.

---

## 🚀 Getting Started (Recommended)

To minimize manual configuration, **we highly recommend using the Starter module.** It automatically handles the registration of all necessary beans and components for your Spring Boot environment.

### 1. Add Dependency
Simply add the following starter to your `build.gradle`:

```gradle
dependencies {
    // Example: Standardized API Payload Starter
    implementation 'org.namul:api-payload-webmvc-starter:0.9.1'
}
```

---
© 2026 [Project Namul - Beginner](https://github.com/projectnamul/beginner)