package org.namu.apiPayload.code;

import org.namu.apiPayload.code.dto.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * The enum containing basic success data
 */
@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseSuccessCode {
    _OK(HttpStatus.OK, "COMMON200", "성공입니다."),
    _CREATED(HttpStatus.CREATED, "COMMON201", "요청 성공 및 리소스 생성되었습니다."),
    _DELETED(HttpStatus.NO_CONTENT, "COMMON204", "성공적으로 삭제했습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder()
                .message(this.message)
                .code(this.code)
                .isSuccess(true)
                .build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .message(this.message)
                .code(this.code)
                .isSuccess(true)
                .httpStatus(this.httpStatus)
                .build()
                ;
    }
}
