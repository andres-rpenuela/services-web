package org.tokio.spring.servicesweb.core.advice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.tokio.spring.servicesweb.core.constans.ErrorCode;
import org.tokio.spring.servicesweb.core.response.ResponseError;
import org.tokio.spring.servicesweb.dto.ErrorDTO;

@RestControllerAdvice
@Slf4j
public class CaptureRestControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseError<ErrorDTO>> genericExceptionHandler(Exception ex, HttpServletRequest request) {
        log.error(ex.getMessage(),ex);
        final ResponseError<ErrorDTO> responseError = ErrorDTO.errorResponse(
                ErrorCode.INTERNAL_ERROR,
                "Error in request: %s, because: %s, message: %s".formatted(
                        request.getRequestURI(),
                        ErrorCode.INTERNAL_ERROR_MESSAGE,
                        ex.getMessage()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseError);
    }
}
