package org.namu.apiPayload.error;

import jakarta.validation.ConstraintViolation;
import org.namu.apiPayload.code.dto.ErrorReasonDTO;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.namu.apiPayload.handler.ExceptionAdviceHandler;
import org.namu.apiPayload.response.BaseResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The Exception Handler for RestController, this contains ConstraintViolationException, MethodArgumentNotValidException, ServerApplicationException, Exception
 * @param <E> ErrorReasonDTO, The type of data returned by BaseErrorCode in ServerException
 */
@Slf4j
@Component
@RestControllerAdvice(annotations = {RestController.class})
public class ExceptionAdvice<E extends ErrorReasonDTO> {

    private final ExceptionAdviceHandler<E> exceptionAdviceHandler;
    private final Class<E> type;

    /**
     * The constructor with ExceptionAdviceHandler, type
     * @param exceptionAdviceHandler The class that generate responses with ErrorReasonDTO
     * @param type The Type of ErrorReasonDTO
     */
    public ExceptionAdvice(ExceptionAdviceHandler<E> exceptionAdviceHandler, Class<E> type) {
        this.exceptionAdviceHandler = exceptionAdviceHandler;
        this.type = type;
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public BaseResponse validation(ConstraintViolationException e) {
        String errorMessage = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("ConstraintViolationException 추출 도중 에러 발생"));

        return exceptionAdviceHandler.handleConstraintViolationException(e, errorMessage);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public BaseResponse handleMethodArgumentNotValid(MethodArgumentNotValidException e) {

        Map<String, String> errors = new LinkedHashMap<>();

        e.getBindingResult().getFieldErrors()
                .forEach(fieldError -> {
                    String fieldName = fieldError.getField();
                    String errorMessage = Optional.ofNullable(fieldError.getDefaultMessage()).orElse("");
                    errors.merge(fieldName, errorMessage, (existingErrorMessage, newErrorMessage) -> existingErrorMessage + ", " + newErrorMessage);
                });

        return exceptionAdviceHandler.handleMethodArgumentNotValidException(e, errors);
    }

    @ExceptionHandler(value = ServerApplicationException.class)
    public BaseResponse onThrowException(ServerApplicationException serverApplicationException) {
        ErrorReasonDTO errorReasonHttpStatus = serverApplicationException.getErrorReason();
        try {
            return exceptionAdviceHandler.handleServerApplicationException(serverApplicationException, type.cast(errorReasonHttpStatus));
        } catch (Exception e) {
            return exceptionAdviceHandler.handleException(e);
        }
    }

    @ExceptionHandler(value = Exception.class)
    public BaseResponse exception(Exception e) {
        e.printStackTrace();

        return exceptionAdviceHandler.handleException(e);
    }

}