package com.example.jwt_restapi.member.controller;

import com.example.jwt_restapi.base.dto.ResultCode;
import com.example.jwt_restapi.base.dto.RsData;
import com.example.jwt_restapi.member.dto.LoginRequest;
import com.example.jwt_restapi.member.dto.MemberContext;
import com.example.jwt_restapi.member.service.MemberService;
import com.example.jwt_restapi.security.jwt.dto.JwtDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "MemberController", description = "로그인 기능, 회원 정보 제공 컨트롤러")
public class MemberController {

  private final MemberService memberService;

  @PostMapping("/login")
  @Operation(summary = "회원 로그인 기능", description = "회원 로그인 시 토큰을 부여합니다.")
  public ResponseEntity<RsData<JwtDto>> login(@Valid @RequestBody LoginRequest loginRequest) {

    JwtDto jwtDto = memberService.login(loginRequest);

    return ResponseEntity.ok()
        .header("AccessToken", jwtDto.getAccessToken())
        .header("RefreshToken", jwtDto.getRefreshToken())
        .body(RsData.of(ResultCode.SUCCESS, "로그인 성공", jwtDto));
  }

  @GetMapping("/me")
  @Operation(summary = "회원 정보 제공 기능", description = "로그인한 회원의 정보를 제공합니다.")
  public ResponseEntity<RsData<MemberContext>> findMember(
      @AuthenticationPrincipal MemberContext memberContext) {

    return ResponseEntity.ok()
        .body(RsData.of(ResultCode.SUCCESS, "현재 로그인한 회원 정보", memberContext));
  }
}