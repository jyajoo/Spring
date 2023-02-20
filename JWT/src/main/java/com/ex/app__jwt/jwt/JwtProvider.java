package com.ex.app__jwt.jwt;

import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component   // 클래스 빈 등록
@RequiredArgsConstructor
public class JwtProvider {

  private final SecretKey secretKey;

  // private인 _getSecretKey 함수가 단 한 번만 실행되도록 함
  public SecretKey getSecretKey() {
    return secretKey;
  }
}
