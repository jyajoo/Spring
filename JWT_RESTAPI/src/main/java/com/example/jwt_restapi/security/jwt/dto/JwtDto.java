package com.example.jwt_restapi.security.jwt.dto;

import com.example.jwt_restapi.member.entity.Member;
import lombok.Getter;

@Getter
public class JwtDto {

  private String accessToken;
  private String refreshToken;

  public static JwtDto from(Member member) {
    JwtDto jwtDto = new JwtDto();
    jwtDto.accessToken = member.getAccessToken();
    jwtDto.refreshToken = member.getRefreshToken();
    return jwtDto;
  }
}
