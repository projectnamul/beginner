package org.namul.api.payload.registry;

import org.namul.api.payload.handler.ExceptionAdviceHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The class which stores ExceptionHandler for ExceptionAdvice
 */
public class ExceptionHandlerRegistry {

    private final Map<Class<? extends Exception>, ExceptionAdviceHandler<? extends Exception>> handlers = new ConcurrentHashMap<>();

    /**
     * Add Handler to handle for Exception Class
     * @param cls The exception class which the handler handle
     * @param handler The handler that handle it when cls type exception occurs
     */
    public <T extends Exception> void addHandler(Class<T> cls, ExceptionAdviceHandler<T> handler) {
        handlers.put(cls, handler);
    }

    /**
     * Get handler to use handle method in handler
     * @param cls The type of exception
     * @return The handler can handle exception
     */
    public <T extends Exception> ExceptionAdviceHandler<T> getHandler(Class<T> cls) {
        return (ExceptionAdviceHandler<T>) handlers.get(cls);
    }

    /**
     * Check exception class is registered
     * @param cls Exception class which you want to check
     * @return If handler of class is registered, return true. return false if it isn't
     */
    public boolean isRegisteredClass(Class<? extends Exception> cls) {
        return handlers.containsKey(cls);
    }

    /**
     * Check handler class is registered
     * @param cls Handler class which you want to check
     * @return If handler class is registered, return true. return false if it isn't
     */
    public boolean isRegisteredHandler(Class<? extends ExceptionAdviceHandler<? extends Exception>> cls) {
        return handlers.values().stream().anyMatch(cls::isInstance);
    }

}
