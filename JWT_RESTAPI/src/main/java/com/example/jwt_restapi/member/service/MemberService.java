package com.example.jwt_restapi.member.service;

import com.example.jwt_restapi.base.exception.MemberException;
import com.example.jwt_restapi.member.dto.LoginRequest;
import com.example.jwt_restapi.member.entity.Member;
import com.example.jwt_restapi.member.entity.MemberRole;
import com.example.jwt_restapi.member.repository.MemberRepository;
import com.example.jwt_restapi.security.jwt.JwtProvider;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
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
  public String login(LoginRequest loginRequest) {

    Member member = memberRepository.findMemberByUsername(loginRequest.getUsername())
        .orElseThrow(() -> new MemberException("회원이 존재하지 않습니다."));

    if (member == null || !passwordEncoder.matches(loginRequest.getPassword(),
        member.getPassword())) {
      throw new MemberException("아이디 또는 비밀번호가 옳지 않습니다.");
    }

    String accessToken = jwtProvider.generateAccessKey(member);
    member.setAccessToken(accessToken);
    return accessToken;
  }

  @Cacheable(value = "member")
  public Optional<Member> findMemberById(Long id) {
    return memberRepository.findMemberById(id);
  }

  public boolean verifyWithMemberToken(Member member, String token) {
    return member.getAccessToken().equals(token);
  }
}
