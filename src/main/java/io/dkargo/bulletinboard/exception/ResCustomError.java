package io.dkargo.bulletinboard.exception;

import lombok.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class ResCustomError {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final int code;
    private final String name;
    private final String message;

    public static ResponseEntity<ResCustomError> toResponseEntity(ErrorCodeEnum errorCodeEnum) {
        return ResponseEntity
                .status(errorCodeEnum.getHttpStatus())
                .body(ResCustomError.builder()
                        .status(errorCodeEnum.getHttpStatus().value())
                        .error(errorCodeEnum.getHttpStatus().name())
                        .code(errorCodeEnum.getCode())
                        .name(errorCodeEnum.name())
                        .message(errorCodeEnum.getDetail())
                        .build()
                );
    }
}
