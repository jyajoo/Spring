package com.example.jwt_restapi.base.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

  private final ErrorCode errorCode;

  public CustomException(ErrorCode errorCode) {
    this.errorCode = errorCode;
  }
}
