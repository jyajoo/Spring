package com.example.jwt_restapi.security.jwt;

import com.example.jwt_restapi.member.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component // 클래스를 빈 등록
@RequiredArgsConstructor
public class JwtProvider {

  private final SecretKey secretKey;
  private static final Long ACCESS_VALID_TIME = 1000L * 60 * 60; // (1시간)

  private SecretKey getSecretKey() {
    return secretKey;
  }

  public String generateAccessKey(Member member) {

    Claims claims = Jwts.claims().setSubject(String.valueOf(member.getId()));
    claims.put("member_roles", member.getRoleList());

    Date now = new Date();

    return Jwts.builder()
        .setClaims(claims)
        .setExpiration(new Date(now.getTime() + ACCESS_VALID_TIME))
        .signWith(secretKey, SignatureAlgorithm.HS512)
        .compact();
  }

  public boolean verifyToken(String jwt) {
    Jws<Claims> claimsJws = Jwts.parserBuilder()
        .setSigningKey(getSecretKey())
        .build()
        .parseClaimsJws(jwt);

    return !claimsJws.getBody().getExpiration().before(new Date());
  }

  public Claims getClaims(String jwt) {
    return Jwts.parserBuilder()
        .setSigningKey(getSecretKey())
        .build()
        .parseClaimsJws(jwt)
        .getBody();
  }
}
