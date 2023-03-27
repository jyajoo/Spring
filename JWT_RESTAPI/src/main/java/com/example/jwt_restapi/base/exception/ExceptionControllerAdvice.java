package com.example.jwt_restapi.base.exception;

import com.example.jwt_restapi.base.dto.ResultCode;
import com.example.jwt_restapi.base.dto.RsData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

  @ExceptionHandler({MemberException.class, BoardException.class})
  public ResponseEntity<RsData<Object>> exceptionHandling(Exception e) {

    return ResponseEntity.badRequest()
        .body(RsData.of(ResultCode.FAIL, e.getMessage(), null));
  }
}
