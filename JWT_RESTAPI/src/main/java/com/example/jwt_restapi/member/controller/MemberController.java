package com.example.jwt_restapi.member.controller;

import com.example.jwt_restapi.base.dto.ResultCode;
import com.example.jwt_restapi.base.dto.RsData;
import com.example.jwt_restapi.member.dto.LoginRequest;
import com.example.jwt_restapi.member.dto.MemberContext;
import com.example.jwt_restapi.member.entity.Member;
import com.example.jwt_restapi.member.service.MemberService;
import com.example.jwt_restapi.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
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
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;

  @PostMapping("/login")
  public ResponseEntity<RsData<String>> login(@RequestBody LoginRequest loginRequest) {

    if (loginRequest.isNotValid()) {
      return ResponseEntity.badRequest()
          .body(RsData.of(ResultCode.FAIL, "아이디 또는 비밀번호를 입력해주세요.", null));
    }

    Member member = memberService.findMemberByUsername(loginRequest.getUsername()).orElse(null);

    if (member == null || !passwordEncoder.matches(loginRequest.getPassword(),
        member.getPassword())) {
      return ResponseEntity.badRequest()
          .body(RsData.of(ResultCode.FAIL, "아이디 또는 비밀번호가 옳지 않습니다.", null));
    }

    String accessToken = jwtProvider.generateAccessKey(member);

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