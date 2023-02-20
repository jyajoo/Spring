package com.ex.app__jwt.jwt;

import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component   // 클래스 빈 등록
@RequiredArgsConstructor
public class JwtProvider {

  private final SecretKey secretKey;

  private SecretKey getSecretKey() {
    return secretKey;
  }
}
