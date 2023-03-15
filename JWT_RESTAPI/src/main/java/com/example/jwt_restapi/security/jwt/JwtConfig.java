package com.example.jwt_restapi.security.jwt;

import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 클래스 빈 등록 후, 내부 @Bean이 붙은 메서드 빈 등록
public class JwtConfig {

  @Value("${custom.jwt.secretKey}")
  private String plainSecretKey;

  @Bean
  public SecretKey getSecretKey() {
    String encodedKey = Base64.getEncoder().encodeToString(plainSecretKey.getBytes());
    return Keys.hmacShaKeyFor(encodedKey.getBytes());
  }
}
