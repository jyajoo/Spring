package com.ex.app__jwt.jwt;

import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component   // 클래스 빈 등록
public class JwtProvider {

  private SecretKey cachedSecretKey;
  @Value("${custom.jwt.secretKey}")
  private String plainSecretKey;

  private SecretKey getPrivateSecretKey() {
    String encodedKey = Base64.getEncoder().encodeToString(plainSecretKey.getBytes());
    return Keys.hmacShaKeyFor(encodedKey.getBytes());
  }

  // private인 _getSecretKey 함수가 단 한 번만 실행되도록 함
  public SecretKey getSecretKey() {
    if (cachedSecretKey == null) {
      cachedSecretKey = getPrivateSecretKey();
    }
    return cachedSecretKey;
  }
}
