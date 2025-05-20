package org.namu.apiPayload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.namu.apiPayload.code.BaseSuccessCode;
import org.namu.apiPayload.code.SuccessStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The classes for Unifying Responses
 * @param <T> The type of data which to be sent to client
 */
@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {

    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final String code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    /**
     * The method which make unified response with OK status and be used when successful
     * @param result The data which to be sent to client
     * @return The unified response contain response data
     * @param <T> The type of data which to be sent to client
     */
    public static <T> ApiResponse<T> onSuccess(T result){
        return new ApiResponse<>(true, SuccessStatus._OK.getCode() , SuccessStatus._OK.getMessage(), result);
    }

    /**
     * The method which make unified response with custom status and be used when successful
     * @param code The custom status
     * @param result The data which to be sent to client
     * @return The unified response contain response data
     * @param <T> The type of data which to be sent to client
     */
    public static <T> ApiResponse<T> of(BaseSuccessCode code, T result){
            return new ApiResponse<>(true, code.getReasonHttpStatus().getCode() , code.getReasonHttpStatus().getMessage(), result);
    }

    /**
     * The method which make unified response with custom status and be used when it fails
     * @param code The code data about failure
     * @param message The message for the reason of failure
     * @param data The data which to be sent to client
     * @return The unified response contain response data
     * @param <T> The type of data which to be sent to client
     */
    public static <T> ApiResponse<T> onFailure(String code, String message, T data){
        return new ApiResponse<>(false, code, message, data);
    }
}