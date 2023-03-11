package com.example.jwt_restapi.member.controller;

import com.example.jwt_restapi.member.entity.Member;
import com.example.jwt_restapi.member.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController {

  private final MemberService memberService;
  private final PasswordEncoder passwordEncoder;

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {

    if (loginDto.isNotValid()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    Member member = memberService.findByUsername(loginDto.getUsername()).orElse(null);

    if (member == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    if (!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

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

    public boolean isNotValid() {
      return username == null || password == null || username.trim().length() == 0
          || password.trim().length() == 0;
    }
  }

}
