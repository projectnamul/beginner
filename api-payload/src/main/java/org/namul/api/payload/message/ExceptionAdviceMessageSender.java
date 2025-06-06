package org.namul.api.payload.message;

public interface ExceptionAdviceMessageSender<T extends ExceptionAdviceMessage> {

    /**
     * Send error message to another platform
     * @param t The message content will be sent
     */
    void sendMessage(T t);

    /**
     * Check whether to send message to another platform. it can check scope of properties with cls parameter
     * @param cls The exception class that occurs
     * @return Whether to send message
     * @param <E> The type of exception class
     */
    <E extends Exception> boolean isEnable(Class<E> cls);
}
