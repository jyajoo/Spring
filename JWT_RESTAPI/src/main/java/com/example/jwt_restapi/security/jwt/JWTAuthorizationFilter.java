package com.example.jwt_restapi.security.jwt;

import com.example.jwt_restapi.member.entity.Member;
import com.example.jwt_restapi.member.repository.MemberRepository;
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
  private final MemberRepository memberRepository;

  public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
      JwtProvider jwtProvider, MemberRepository memberRepository) {

    super(authenticationManager);
    this.jwtProvider = jwtProvider;
    this.memberRepository = memberRepository;
  }

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
        Member member = memberRepository.findById(Long.valueOf(memberId))
            .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 계정입니다."));
        Authentication authentication = jwtProvider.getAuthentication(member);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }
    chain.doFilter(request, response);
  }
}
