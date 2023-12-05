package com.testcar.car.domains.member.exception;


import com.testcar.car.common.exception.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements BaseErrorCode {
    MEMBER_NOT_FOUND("MEM001", "해당 사용자를 찾을 수 없습니다."),
    INVALID_PASSWORD("MEM002", "잘못된 비밀번호 입니다.");

    private final String code;
    private final String message;
}
