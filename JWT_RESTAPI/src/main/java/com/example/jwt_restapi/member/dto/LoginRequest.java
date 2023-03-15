package com.example.jwt_restapi.member.dto;

import lombok.Data;

@Data
public class LoginRequest {
  String username;
  String password;

  public boolean isNotValid() {
    return username == null || password == null || username.trim().length() == 0
        || password.trim().length() == 0;
  }
}