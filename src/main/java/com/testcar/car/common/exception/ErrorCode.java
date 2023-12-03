package com.testcar.car.common.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** 에러 코드를 관리하기 위한 Enum 입니다 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode implements BaseErrorCode {

    /* 공통 오류 */
    _INTERNAL_SERVER_ERROR("C000", "서버 에러, 관리자에게 문의 바랍니다"),
    _BAD_REQUEST("C001", "잘못된 요청입니다"),
    _UNAUTHORIZED("C002", "권한이 없습니다");

    private final String code;
    private final String message;
}
