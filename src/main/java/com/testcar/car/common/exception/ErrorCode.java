package com.testcar.car.common.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** 에러 코드를 관리하기 위한 서버 공통 Enum 입니다 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode implements BaseErrorCode {
    INTERNAL_SERVER_ERROR("C000", "서버 에러, 관리자에게 문의 바랍니다."),
    BAD_REQUEST("C001", "잘못된 요청입니다."),
    UNAUTHORIZED("C002", "권한이 없습니다."),
    EMPTY_TOKEN("C003", "토큰이 존재하지 않습니다."),
    INVALID_TOKEN("C004", "유효하지 않은 토큰입니다.");

    private final String code;
    private final String message;
}
