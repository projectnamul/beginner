package org.namul.api.payload.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.namul.api.payload.code.dto.ErrorReasonDTO;
import org.namul.api.payload.error.configurer.ExceptionAdviceConfigurer;
import org.namul.api.payload.handler.ExceptionAdviceHandler;
import org.namul.api.payload.log.ExceptionAdviceLogger;
import org.namul.api.payload.message.ExceptionAdviceMessageManager;
import org.namul.api.payload.response.BaseResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * The class for Error handling when error occurs.
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice<R extends ErrorReasonDTO> {

    private final ExceptionAdviceConfigurer<R> exceptionAdviceConfigurer;
    private final ExceptionAdviceLogger exceptionAdviceLogger;
    private final ExceptionAdviceMessageManager exceptionAdviceMessageManager;

    /**
     * Handle method when exception occurs
     * @param e The Exception type
     * @return The Response value after handling exception
     */
    @ExceptionHandler
    public <E extends Exception> BaseResponse handle(E e, HttpServletRequest request, HttpServletResponse response) {
        ExceptionAdviceRegistry<E, R> registry = this.exceptionAdviceConfigurer.findRegistry(e.getClass());
        if (registry == null) {
            throw new IllegalArgumentException("The appropriate handler was not found.");
        }

        exceptionAdviceMessageManager.sendMessageAsync(request, registry.getCls(), e);
        return handleDelegated(e, request, response, registry);
    }

    /**
     * Create BaseResponse with handler and errorReasonDTO
     * @param e The exception class
     * @param request The HttpServletRequest
     * @param response The HttpServletResponse
     * @param registry The registry contains handler and ErrorReasonDTO
     * @return The created response
     * @param <E> The exception type
     */
    private <E extends Exception> BaseResponse handleDelegated(E e, HttpServletRequest request, HttpServletResponse response, ExceptionAdviceRegistry<E, R> registry) {
        R reasonDTO = registry.getErrorReasonDTO();
        ExceptionAdviceHandler<E, R> handler = registry.getHandler();

        response.setStatus(reasonDTO.getHttpStatus().value());
        exceptionAdviceLogger.log(e, reasonDTO, handler.getMessage(e, reasonDTO));

        return handler.handleException(e, request, response, reasonDTO);
    }
}
