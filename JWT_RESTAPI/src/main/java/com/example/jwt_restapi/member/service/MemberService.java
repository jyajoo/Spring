package com.example.jwt_restapi.member.service;

import com.example.jwt_restapi.member.entity.Member;
import com.example.jwt_restapi.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // final, @NotNull 붙은 필드의 생성자 자동 생성
public class MemberService {

  private final MemberRepository memberRepository;

  public Member join(String username, String password, String email) {
    Member member = Member.builder()
        .username(username)
        .password(password)
        .email(email)
        .build();

    memberRepository.save(member);
    return member;
  }
}
