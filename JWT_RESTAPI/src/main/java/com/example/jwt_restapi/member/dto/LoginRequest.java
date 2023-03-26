package com.example.jwt_restapi.member.dto;

import lombok.Getter;

@Getter
public class LoginRequest {
  private String username;
  private String password;

  public boolean isNotValid() {
    return username == null || password == null || username.trim().length() == 0
        || password.trim().length() == 0;
  }
}