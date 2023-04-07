package com.example.jwt_restapi.member.service;

import static com.example.jwt_restapi.base.exception.ErrorCode.*;
import com.example.jwt_restapi.base.exception.MemberException;
import com.example.jwt_restapi.member.dto.LoginRequest;
import com.example.jwt_restapi.member.entity.Member;
import com.example.jwt_restapi.member.entity.MemberRole;
import com.example.jwt_restapi.member.repository.MemberRepository;
import com.example.jwt_restapi.security.jwt.JwtProvider;
import com.example.jwt_restapi.security.jwt.dto.JwtDto;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor // final, @NotNull 붙은 필드의 생성자 자동 생성
public class MemberService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;


  public Member join(String username, String password, String email) {
    Member member = Member.builder()
        .username(username)
        .password(password)
        .email(email)
        .build();

    Set<MemberRole> roles = new HashSet<>();
    roles.add(MemberRole.ROLE_MEMBER);
    member.setRoleSet(roles);
    memberRepository.save(member);
    return member;
  }

  /**
   * 로그인 기능
   * 회원에 accessToken이 없는 경우, 토큰 발행
   */
  @Transactional
  public JwtDto login(LoginRequest loginRequest) {

    Member member = memberRepository.findMemberByUsername(loginRequest.getUsername())
        .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));

    if (member == null || !passwordEncoder.matches(loginRequest.getPassword(),
        member.getPassword())) {
      throw new MemberException(AUTHENTICATION_FAILED);
    }

    String accessToken = jwtProvider.generateAccessToken(member);
    String refreshToken = jwtProvider.generateRefreshToken(member);
    member.setAccessToken(accessToken);
    member.setRefreshToken(refreshToken);
    return JwtDto.from(member);
  }

  public Optional<Member> findMemberById(Long id) {
    return memberRepository.findMemberById(id);
  }

  public boolean verifyWithMemberToken(Member member, String token) {
    return member.getAccessToken().equals(token);
  }

  @Transactional
  public JwtDto reissue(String refreshToken) {
    String token = jwtProvider.resolveToken(refreshToken);
    Member member = memberRepository.findByRefreshToken(token)
        .orElseThrow(() -> new MemberException(INVALID_TOKEN));

    if (!jwtProvider.verifyRefreshToken(token)) {
      throw new MemberException(INVALID_TOKEN);
    }

    String accessToken = jwtProvider.generateAccessToken(member);
    member.setAccessToken(accessToken);
    return JwtDto.from(member);
  }
}
