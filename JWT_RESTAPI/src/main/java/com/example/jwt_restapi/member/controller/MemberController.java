package com.example.jwt_restapi.member.controller;

import com.example.jwt_restapi.base.dto.RsData;
import com.example.jwt_restapi.member.dto.LoginRequest;
import com.example.jwt_restapi.member.entity.Member;
import com.example.jwt_restapi.member.service.MemberService;
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
  public ResponseEntity<RsData<String>> login(@RequestBody LoginRequest loginRequest) {

    if (loginRequest.isNotValid()) {
      return ResponseEntity.badRequest()
          .body(RsData.of("F-1", "아이디 또는 비밀번호를 입력해주세요.", null));
    }

    Member member = memberService.findByUsername(loginRequest.getUsername()).orElse(null);

    if (member == null || !passwordEncoder.matches(loginRequest.getPassword(),
        member.getPassword())) {
      return ResponseEntity.badRequest()
          .body(RsData.of("F-2", "아이디 또는 비밀번호가 옳지 않습니다.", null));
    }

    String accessToken = "Access-Token";
    return ResponseEntity.ok()
        .header("AccessToken", "AccessToken")
        .body(RsData.of("S-1", "로그인 성공", accessToken));
  }
}