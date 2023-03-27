package com.example.jwt_restapi.member.controller;

import com.example.jwt_restapi.base.dto.ResultCode;
import com.example.jwt_restapi.base.dto.RsData;
import com.example.jwt_restapi.member.dto.LoginRequest;
import com.example.jwt_restapi.member.dto.MemberContext;
import com.example.jwt_restapi.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController {

  private final MemberService memberService;

  @PostMapping("/login")
  public ResponseEntity<RsData<String>> login(@RequestBody LoginRequest loginRequest) {

    String accessToken = memberService.login(loginRequest);

    return ResponseEntity.ok()
        .header("AccessToken", accessToken)
        .body(RsData.of(ResultCode.SUCCESS, "로그인 성공", accessToken));
  }

  @GetMapping("/me")
  public ResponseEntity<RsData<MemberContext>> findMember(
      @AuthenticationPrincipal MemberContext memberContext) {

    return ResponseEntity.ok()
        .body(RsData.of(ResultCode.SUCCESS, "현재 로그인한 회원 정보", memberContext));
  }
}