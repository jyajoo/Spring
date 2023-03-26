package com.example.jwt_restapi.base.exception;

public class MemberException extends RuntimeException{

  public MemberException() {
  }

  public MemberException(String message) {
    super(message);
  }
}
