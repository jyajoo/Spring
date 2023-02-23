package com.ex.app__jwt.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Map;
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

  public String generateAccessToken(Map<String, Object> claims, int seconds) {
    long now = new Date().getTime();
    Date accessExpireTime = new Date(now + 1000L * seconds);

        return Jwts.builder()
            .setClaims(claims)
            .setExpiration(accessExpireTime)
            .signWith(getSecretKey(), SignatureAlgorithm.HS512)
            .compact();
  }
}
