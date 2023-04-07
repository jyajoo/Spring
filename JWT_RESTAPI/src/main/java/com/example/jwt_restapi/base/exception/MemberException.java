package com.example.jwt_restapi.base.exception;

public class MemberException extends CustomException{

  public MemberException(ErrorCode errorCode) {
    super(errorCode);
  }
}
