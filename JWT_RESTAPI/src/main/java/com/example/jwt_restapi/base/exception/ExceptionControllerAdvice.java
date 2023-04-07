package com.example.jwt_restapi.base.exception;

import com.example.jwt_restapi.base.dto.ResultCode;
import com.example.jwt_restapi.base.dto.RsData;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

  @ExceptionHandler({MethodArgumentNotValidException.class})
  public ResponseEntity<RsData<Object>> exceptionHandling(MethodArgumentNotValidException e) {

    String msg = e
        .getBindingResult()
        .getAllErrors()
        .stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.joining(", "));

    String data = e
        .getBindingResult()
        .getAllErrors()
        .stream()
        .map(DefaultMessageSourceResolvable::getCode)
        .collect(Collectors.joining(", "));

    return ResponseEntity.badRequest()
        .body(RsData.of(ResultCode.FAIL, msg, data));
  }

  @ExceptionHandler({MemberException.class, BoardException.class})
  public ResponseEntity<RsData<Object>> handleCustomException(CustomException e) {

    return ResponseEntity.status(e.getErrorCode().getStatus())
        .body(RsData.of(ResultCode.FAIL, e.getErrorCode().getMsg(), null));
  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<RsData<Object>> handleException(Exception e) {
    return ResponseEntity.badRequest()
        .body(RsData.of(ResultCode.FAIL, e.getMessage(), null));
  }
}
