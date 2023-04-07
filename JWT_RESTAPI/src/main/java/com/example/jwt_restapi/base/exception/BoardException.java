package com.example.jwt_restapi.base.exception;

public class BoardException extends CustomException{

  public BoardException(ErrorCode errorCode) {
    super(errorCode);
  }
}
