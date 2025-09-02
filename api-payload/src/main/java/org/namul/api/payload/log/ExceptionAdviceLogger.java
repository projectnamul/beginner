package org.namul.api.payload.log;

import org.namul.api.payload.code.dto.ErrorReasonDTO;

public interface ExceptionAdviceLogger {

    /**
     * The method for logging with exception and ErrorReasonDTO in application
     * @param e The Exception
     * @param r The ErrorReasonDTO
     * @param message The message for logging
     * @param <E> The exception type
     */
    <E extends Exception> void log(E e, ErrorReasonDTO r, Object message);
}
