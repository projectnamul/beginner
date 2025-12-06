package org.namul.api.payload.code.supports;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * The default enum containing basic failure data
 */
@Getter
@AllArgsConstructor
public enum DefaultResponseErrorCode implements DefaultBaseErrorCode {
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _HTTP_MESSAGE_NOT_READABLE(HttpStatus.BAD_REQUEST, "COMMON400_1", "요청 본문이 올바르지 않거나 형식이 맞지 않습니다."),
    _HTTP_REQUEST_METHOD_NOT_SUPPORTED(HttpStatus.BAD_REQUEST, "COMMON400_2", "해당 경로에 지원하지 않는 경로입니다."),
    _NO_RESOURCE_FOUND(HttpStatus.BAD_REQUEST, "COMON400_3", "존재하지 않는 API 엔드포인트입니다."),
    _MISSING_REQUEST_VALUE(HttpStatus.BAD_REQUEST, "COMMON400_4", "요청 변수가 잘못되었습니다."),
    _TYPE_MISMATCH(HttpStatus.BAD_REQUEST, "COMMON400_5", "요청 파라미터의 타입이 올바르지 않습니다."),
    _CONSTRAINT_VIOLATION(HttpStatus.BAD_REQUEST, "COMMON400_6", "요청 파라미터가 유효하지 않습니다."),
    _METHOD_ARGUMENT_NOT_VALID(HttpStatus.BAD_REQUEST, "COMMON400_7", "요청 데이터가 유효하지 않습니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
    _NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON404", "리소스를 찾을 수 없습니다."),
    _IS_ALREADY(HttpStatus.BAD_REQUEST, "COMMON400", "이미 해당 리소스가 존재합니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
