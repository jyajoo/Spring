package com.example.jwt_restapi.member.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
    String body = "username : %s, password : %s".formatted(loginDto.getUsername(),
        loginDto.getPassword());
    return ResponseEntity.ok()
        .header("Authentication", "JWT")
        .body(body);
  }

  @Data
  public static class LoginDto {
    String username;
    String password;
  }

}
