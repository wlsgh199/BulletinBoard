package io.dkargo.bulletinboard.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

//    //jpa 관련 메세지 ?
//    @ExceptionHandler(value = { ConstraintViolationException.class, DataIntegrityViolationException.class})
//    protected ResponseEntity<ResCustomError> handleDataException() {
//        log.error("handleDataException throw Exception : {}", CustomErrorCode.DUPLICATE_RESOURCE);
//        return ResCustomError.toResponseEntity(CustomErrorCode.DUPLICATE_RESOURCE);
//    }

    //api error 메세지?
    @ExceptionHandler(value = { CustomException.class })
    protected ResponseEntity<ResCustomError> handleCustomException(CustomException e) {
        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return ResCustomError.toResponseEntity(e.getErrorCode());
    }
}
