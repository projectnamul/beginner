package org.namu.api.payload.handler;

import jakarta.validation.ConstraintViolationException;
import org.namu.api.payload.code.dto.ErrorReasonDTO;
import org.namu.api.payload.error.ServerApplicationException;
import org.namu.api.payload.response.BaseResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * The class that handle exceptions to unify responses, Commonly used by ExceptionAdvice
 * @param <E> ErrorReasonDTO, The type of data returned by BaseErrorCode in ServerException
 */
public interface ExceptionAdviceHandler<E extends ErrorReasonDTO> {

    /**
     * The method handle ConstraintViolationException to unify response when it occurs
     * @param e The ConstraintViolationException
     * @param result The result value, like error message
     * @return The unified response
     * @param <T> The type of result
     */
    <T> BaseResponse handleConstraintViolationException(ConstraintViolationException e, T result);

    /**
     * The method handle MethodArgumentNotValidException to unify response when it occurs
     * @param e The MethodArgumentNotValidException
     * @param result The result value, like error message
     * @return The unified response
     * @param <T> The type of result
     */
    <T> BaseResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e, T result);

    /**
     * The method handle ServerApplicationException to unify response when it occurs
     * @param e The ServerApplicationException
     * @param errorReasonDTO The data about failure
     * @return The unified response
     */
    BaseResponse handleServerApplicationException(ServerApplicationException e, E errorReasonDTO);

    /**
     * The method handle Exception to unify response when it occurs
     * @param e The Exception
     * @return The unified response
     */
    BaseResponse handleException(Exception e);
}
