package com.ex.app__jwt.jwt;

import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 클래스 빈 등록 후 내부의 @Bean이 붙은 메서드 빈 등록
public class JwtConfig {

  @Value("${custom.jwt.secretKey}")
  private String plainSecretKey;

  // @Bean : 기본적으로 싱글톤이기 때문에 여러 번 호출돼도 하나의 객체만 리턴되는 것이 보장됨
  @Bean
  public SecretKey getSecretKey() {
    String encodedKey = Base64.getEncoder().encodeToString(plainSecretKey.getBytes());
    return Keys.hmacShaKeyFor(encodedKey.getBytes());
  }
}
