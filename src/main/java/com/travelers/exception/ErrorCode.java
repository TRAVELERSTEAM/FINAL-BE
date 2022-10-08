package com.travelers.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    INVALID_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰이 유효하지 않습니다"),
    MISMATCH_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰의 유저 정보가 일치하지 않습니다"),
    MISMATCH_ACCESS_TOKEN(BAD_REQUEST, "액세스 토큰의 유저 정보가 일치하지 않습니다"),
    CLIENT_BAD_REQUEST(BAD_REQUEST, "잘못된 요청입니다."),
    CANT_RESERVE_TRAVEL(BAD_REQUEST, "남은 수용 인원보다 요청 인원이 많습니다."),
    CANT_WRITE_REVIEW(BAD_REQUEST, "다녀오신 여행지가 아니거나 이미 작성하신 리뷰 입니다."),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "권한 정보가 없는 토큰입니다"),
    UNAUTHORIZED_MEMBER(UNAUTHORIZED, "현재 내 계정 정보가 존재하지 않습니다"),

    /* 403 FORBIDDEN : 인증은 하였으나 접근권한이 없을때 */
    PASSWORD_NOT_MATCHING(FORBIDDEN, "비밀번호가 일치하지 않습니다"),
    CURRENT_PASSWORD_NOT_MATCHING(FORBIDDEN, "현재 비밀번호와 일치하지 않습니다"),
    NO_PERMISSIONS(FORBIDDEN, "접근 권한이 없습니다."),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    MEMBER_NOT_FOUND(NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다"),
    TOKEN_NOT_FOUND(NOT_FOUND, "토큰 정보가 없습니다"),
    ACCESS_TOKEN_NOT_FOUND(NOT_FOUND, "현재 로그인 상태가 아닙니다"),
    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "로그아웃 된 사용자입니다"),
    KEY_NOT_FOUND(NOT_FOUND, "이메일 인증키가 유효하지 않습니다."),
    RESOURCE_NOT_FOUND(NOT_FOUND, "요청하신 자원을 찾을 수 없습니다."),
    PRODUCT_NOT_FOUND(NOT_FOUND, "해당 상품을 찾을 수 없습니다."),
    RESERVATION_NOT_FOUND(NOT_FOUND, "예약하신 상품이 없습니다."),
    IMAGE_NOT_FOUND(NOT_FOUND, "이미지를 찾을 수 없습니다."),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다"),
    DUPLICATE_ACCOUNT(CONFLICT, "이미 가입되어 있는 유저입니다");

    private final HttpStatus httpStatus;
    private final String detail;
}