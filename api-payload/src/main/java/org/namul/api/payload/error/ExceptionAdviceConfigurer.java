package org.namul.api.payload.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.namul.api.payload.code.BaseErrorCode;
import org.namul.api.payload.code.DefaultResponseErrorCode;
import org.namul.api.payload.registry.ExceptionHandlerRegistry;


@Getter
@RequiredArgsConstructor
public class ExceptionAdviceConfigurer {

    private final ExceptionHandlerRegistry exceptionHandlerRegistry;
    private ConstraintViolationExceptionAdvice constraintViolationExceptionAdvice = null;
    private MethodArgumentNotValidExceptionAdvice methodArgumentNotValidExceptionAdvice = null;
    private ServerApplicationExceptionAdvice serverApplicationExceptionAdvice = null;
    private GlobalExceptionAdvice globalExceptionAdvice = null;

    public ExceptionAdviceConfigurer withDefault() {
        return this.enableGlobalExceptionAdvice(DefaultResponseErrorCode._INTERNAL_SERVER_ERROR)
                .enableServerApplicationExceptionAdvice();
    }

    public ExceptionAdviceConfigurer enableConstraintViolationExceptionAdvice() {
        this.enableConstraintViolationExceptionAdvice(DefaultResponseErrorCode._BAD_REQUEST);
        return this;
    }

    public ExceptionAdviceConfigurer enableConstraintViolationExceptionAdvice(BaseErrorCode baseErrorCode) {
        this.constraintViolationExceptionAdvice = this.constraintViolationExceptionAdvice == null ? new ConstraintViolationExceptionAdvice(exceptionHandlerRegistry, baseErrorCode) : this.constraintViolationExceptionAdvice;
        return this;
    }

    public ExceptionAdviceConfigurer enableMethodArgumentNotValidExceptionAdvice() {
        this.enableMethodArgumentNotValidExceptionAdvice(DefaultResponseErrorCode._BAD_REQUEST);
        return this;
    }

    public ExceptionAdviceConfigurer enableMethodArgumentNotValidExceptionAdvice(BaseErrorCode baseErrorCode) {
        this.methodArgumentNotValidExceptionAdvice = this.methodArgumentNotValidExceptionAdvice == null ? new MethodArgumentNotValidExceptionAdvice(exceptionHandlerRegistry, baseErrorCode) : this.methodArgumentNotValidExceptionAdvice;
        return this;
    }

    public ExceptionAdviceConfigurer enableServerApplicationExceptionAdvice() {
        this.serverApplicationExceptionAdvice = this.serverApplicationExceptionAdvice == null ? new ServerApplicationExceptionAdvice(exceptionHandlerRegistry) : this.serverApplicationExceptionAdvice;
        return this;
    }

    public ExceptionAdviceConfigurer enableGlobalExceptionAdvice(BaseErrorCode baseErrorCode) {
        this.globalExceptionAdvice = this.globalExceptionAdvice == null ? new GlobalExceptionAdvice(exceptionHandlerRegistry, baseErrorCode) : this.globalExceptionAdvice;
        return this;
    }

    public ExceptionAdviceConfigurer disableConstraintViolationExceptionAdvice() {
        this.constraintViolationExceptionAdvice = null;
        return this;
    }

    public ExceptionAdviceConfigurer disableMethodArgumentNotValidExceptionAdvice() {
        this.methodArgumentNotValidExceptionAdvice = null;
        return this;
    }

    public ExceptionAdviceConfigurer disableServerApplicationExceptionAdvice() {
        this.serverApplicationExceptionAdvice = null;
        return this;
    }

    public ExceptionAdviceConfigurer disableGlobalExceptionAdvice() {
        this.globalExceptionAdvice = null;
        return this;
    }
}
