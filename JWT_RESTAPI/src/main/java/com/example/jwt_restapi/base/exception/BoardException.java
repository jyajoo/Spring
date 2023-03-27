package com.example.jwt_restapi.base.exception;

public class BoardException extends RuntimeException{

  public BoardException() {
  }

  public BoardException(String message) {
    super(message);
  }
}
