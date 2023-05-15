package io.dkargo.bulletinboard.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {

    /* 400 Bad Request */
    PHONE_NUM_ERROR(HttpStatus.BAD_REQUEST, 1001, "핸드폰 번호가 잘못되었습니다."),
    PASSWORD_NOT(HttpStatus.BAD_REQUEST,1002, "잘못된 비밀번호 입니다."),
    UPDATE_ONLY_WRITER(HttpStatus.BAD_REQUEST,1003, "작성자만 업데이트 할수 있습니다."),


    /* 404 Not Found */
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, 1003, "해당 게시물을 찾을수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, 1004, "해당 유저를 찾을수 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, 1004, "해당 댓글을 찾을수 없습니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, 1004, "해당 카테고리를 찾을수 없습니다."),

    /* 429 Too Many Requests */
    FILES_TOTO(HttpStatus.TOO_MANY_REQUESTS, 1004, "파일은 최대 3개까지 등록할수 있습니다."),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, 4001, "이메일이 이미 존재합니다."),
    POST_NOT_CREATE_COMMENT(HttpStatus.CONFLICT, 1005,"해당 게시물은 댓글을 작성할수 없습니다."),
    IF_REPLY_IT_NOT_DELETE(HttpStatus.CONFLICT, 1005,"답글이 달린 댓글은 삭제할수 없습니다."),

//    INVALID_JWT_TOKEN(HttpStatus.UNAUTHORIZED, 1006,"잘못된 JWT 토큰입니다"),
//    EXPIRED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, 1006,"만료된 JWT 토큰입니다"),
//    UNSUPPORTED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, 1006,"지원되지 않는 JWT 토큰입니다."),
//    JWT_CLAIMS_IS_EMPTY(HttpStatus.UNAUTHORIZED, 1006,"클레임 문자열이 비어 있습니다."),


    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, 1006,"권한 정보가 없는 토큰입니다");


//    UNAUTHORIZED_MEMBER(UNAUTHORIZED, "현재 내 계정 정보가 존재하지 않습니다"),

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String detail;
}
