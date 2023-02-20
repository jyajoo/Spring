package com.ex.app__jwt.jwt;

import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component   // 클래스 빈 등록
public class JwtProvider {

  @Value("${custom.jwt.secretKey}")
  private String plainSecretKey;

  public SecretKey getSecretKey() {
    String encodedKey = Base64.getEncoder().encodeToString(plainSecretKey.getBytes());
    return Keys.hmacShaKeyFor(encodedKey.getBytes());
  }
}
