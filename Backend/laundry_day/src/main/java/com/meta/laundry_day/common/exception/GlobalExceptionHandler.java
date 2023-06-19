package com.meta.laundry_day.common.exception;

import com.meta.laundry_day.common.dto.ResponseDto;
import com.meta.laundry_day.common.message.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.meta.laundry_day.common.message.ErrorCode.INTERNAL_SERVER_ERROR;
import static com.meta.laundry_day.common.message.ErrorCode.INVALID_EMAIL_PATTERN_ERROR;
import static com.meta.laundry_day.common.message.ErrorCode.INVALID_NICKNAME_PATTERN;
import static com.meta.laundry_day.common.message.ErrorCode.INVALID_PASSWORD_PATTERN_ERROR;
import static com.meta.laundry_day.common.message.ErrorCode.INVALID_PHONENUMBER_PATTERN;
import static com.meta.laundry_day.common.message.ErrorCode.USER_AUTHORIZATION_FAIL_ERROR;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<ResponseDto<ErrorCode>> handleCustomException(CustomException ex) {
        log.info(String.valueOf(ex.getErrorCode()));
        log.warn("CustomException occur: ", ex);
        return errorResponseEntity(ex.getErrorCode());
    }

    //접근 권한 예외처리
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ResponseDto<ErrorCode>> handleAccessDeniedException(AccessDeniedException ex) {
        log.info(String.valueOf(USER_AUTHORIZATION_FAIL_ERROR));
        log.warn("AccessDeniedException occur: ", ex);
        return errorResponseEntity(USER_AUTHORIZATION_FAIL_ERROR);
    }

    //Validation 예외처리
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ResponseDto<ErrorCode>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();

        ErrorCode errorCode = null;
        for (FieldError error : result.getFieldErrors()) {
            if (error.getField().equals("email")) {
                errorCode=INVALID_EMAIL_PATTERN_ERROR; break;
            } else if (error.getField().equals("password")) {
                errorCode=INVALID_PASSWORD_PATTERN_ERROR; break;
            } else if (error.getField().equals("nickname")) {
                errorCode=INVALID_NICKNAME_PATTERN; break;
            } else{
                errorCode = INVALID_PHONENUMBER_PATTERN; break;
            }
        }
        log.info(String.valueOf(errorCode));
        log.warn("MethodArgumentNotValidException occur: ", ex);
        return errorResponseEntity(errorCode);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ResponseDto<ErrorCode>> handleServerException(Exception ex) {
        log.info(String.valueOf(INTERNAL_SERVER_ERROR));
        log.warn("Exception occur: ", ex);
        return errorResponseEntity(INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ResponseDto<ErrorCode>> errorResponseEntity(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getStatusCode())
                .body(new ResponseDto<>(errorCode, errorCode));
    }
}
