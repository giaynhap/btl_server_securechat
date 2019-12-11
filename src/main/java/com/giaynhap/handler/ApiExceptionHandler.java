package com.giaynhap.handler;

import com.giaynhap.config.AppConstant;
import com.giaynhap.model.ApiResponse;
import com.giaynhap.model.JwtRequest;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)

    public ResponseEntity<Object> handle(Exception ex,
                                         HttpServletRequest request, HttpServletResponse response) {
        if (ex instanceof NullPointerException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.getErrorResponse(1, AppConstant.BAD_REQUEST_MESSAGE));
        }
        JwtRequest jqt = new JwtRequest();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.getErrorResponse(1,ex.getMessage()));
    }
}
