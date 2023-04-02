package com.example.jwt_restapi.security.jwt;

import com.example.jwt_restapi.member.entity.Member;
import com.example.jwt_restapi.member.service.MemberService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

  private final JwtProvider jwtProvider;
  private final MemberService memberService;

  public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
      JwtProvider jwtProvider, MemberService memberService) {

    super(authenticationManager);
    this.jwtProvider = jwtProvider;
    this.memberService = memberService;
  }

  /**
   * filter 실행 시
   * token의 유효시간 체크 후
   * token의 claims를 통해 DB에 있는 회원 정보를 가지고 온다.
   * => token 발행 후에 회원의 정보가 변하더라도,
   * => claims를 통해 DB에 있는 회원 정보를 조회해오므로 문제를 해결할 수 있다.
   * member에 저장된 accessToken과 명확히 일치하는지 확인
   * => 유효 시간이 지나지 않았지만, member의 accessToken이 새로 발행되어 일치하지 않는 경우
   * => 허용되지 않도록 함 (보안 높임)
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws IOException, ServletException {
    log.info("JWTAuthorizationFilter 실행");

    String bearerToken = request.getHeader("Authorization");

    if (bearerToken != null) {
      String token = bearerToken.substring("Bearer ".length());

      if (jwtProvider.verifyToken(token)) {
        Claims claims = jwtProvider.getClaims(token);
        String memberId = claims.getSubject();
        Member member = memberService.findMemberById(Long.valueOf(memberId))
            .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 계정입니다."));

        if (memberService.verifyWithMemberToken(member, token)) {
          Authentication authentication = jwtProvider.getAuthentication(member);
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }
    }
    chain.doFilter(request, response);
  }
}
