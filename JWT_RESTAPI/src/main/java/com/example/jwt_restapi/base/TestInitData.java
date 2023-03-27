package com.example.jwt_restapi.base;

import com.example.jwt_restapi.board.entity.Board;
import com.example.jwt_restapi.board.service.BoardService;
import com.example.jwt_restapi.member.entity.Member;
import com.example.jwt_restapi.member.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration //클래스 빈 등록 후,내부 @Bean 붙은 메서드 자동 빈 등록
@Profile("test")// test Profile이 active될 때만 스프링 컨테이너에 포함된다.
public class TestInitData {

  @Bean
  CommandLineRunner initTestData(MemberService memberService, PasswordEncoder passwordEncoder, BoardService boardService) {
    String password = passwordEncoder.encode("1234");
    return args -> {
      Member user1 = memberService.join("user1", password, "user1@test.com");
      Member user2 = memberService.join("user2", password, "user2@test.com");

      Board board1 = boardService.create(user1, "subject1", "content1");
      Board board2 = boardService.create(user1, "subject2", "content2");
      Board board3 = boardService.create(user2, "subject3", "content3");
      Board board4 = boardService.create(user2, "subject4", "content4");
    };




  }
}