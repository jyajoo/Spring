package com.example.jwt_restapi.member.controller;

import com.example.jwt_restapi.base.dto.RsData;
import com.example.jwt_restapi.member.entity.Member;
import com.example.jwt_restapi.member.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
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
  public <T> ResponseEntity<RsData<T>> login(@RequestBody LoginDto loginDto) {

    if (loginDto.isNotValid()) {
      return ResponseEntity.badRequest()
          .body(RsData.of("F-1", "아이디 또는 비밀번호를 입력해주세요."));
    }

    Member member = memberService.findByUsername(loginDto.getUsername()).orElse(null);

    if (member == null || !passwordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
      return ResponseEntity.badRequest()
          .body(RsData.of("F-2", "아이디 또는 비밀번호가 옳지 않습니다."));
    }

    return ResponseEntity.ok()
        .header("AccessToken", "AccessToken")
        .body(RsData.of("S-1", "로그인 성공"));
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
