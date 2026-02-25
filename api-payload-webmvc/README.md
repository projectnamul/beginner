# api-payload-webmvc

It is a dependency that helps users easily use functions in a Spring MVC environment by using it as 'api-payload-core'.


## Classes

### HttpServletWebRequestWrapper
- A class that implements WebRequestWrapper's method using HttpServletRequest. It makes it easy to change and use HttpServletRequestWrapper to WebRequestWrapper through a constructor or static method.

### HttpServletWebResponseWrapper
- A class that implements WebResponseWrapper's method using HttpServletResponse. It makes it easy to change and use HttpServletResponse to WebResponseWrapper through a constructor or static method.

### HttpServletErrorCodeExceptionHandlerConfigurer
- This class implements the ErrorCodeExceptionHandlerConfigurer, which supports methods that make it easy to add errors that usually occur in the Dispatcher Servlet environment.

### ExceptionRestControllerAdvice
- RestControllerAdvice is a class implemented using ErrorCodeExceptionHandler that helps developers add it through Bean rather than directly. If it needs to be modified, you can override it. Of course, you can implement it yourself without using it.