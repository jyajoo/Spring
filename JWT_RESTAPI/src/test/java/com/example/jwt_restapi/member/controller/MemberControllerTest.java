package com.example.jwt_restapi.member.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc // @WebMvcTest와 유사한 어노테이션, 컨트롤러뿐만 아니라 서비스, 리포지토리 객체도 메모리에 올림
@Transactional // "트랜잭션"이 되도록 보장
@ActiveProfiles("test") // 테스트 수행 시, 실행할 프로필 지정
class MemberControllerTest {

  @Autowired
  private MockMvc mvc; // 요청과 응답을 의미하는 가짜 객체로 실제 서버에 배포하지 않고 테스트를 위한 라이브러리

  @Test
  @DisplayName("POST /member/login 은 로그인 처리 URL")
  void t1() throws Exception {
    mvc.perform(post("/member/login")
            .content("""
                {
                  "username" : "user1",
                  "password" : "1234"
                }
                """.stripIndent())
            .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
        .andDo(print())
        .andExpect(status().is2xxSuccessful());
  }

  @Test
  @DisplayName("POST /member/login의 ok 응답으로 JWT 발급")
  void t2() throws Exception {
    MockHttpServletResponse response = mvc.perform(
            post("/member/login")
                .content("""
                    {
                      "username" : "user1",
                      "password" : "1234"
                    }
                    """.stripIndent())
                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
        .andDo(print())
        .andExpect(status().is2xxSuccessful())
        .andReturn().getResponse();

    String accessToken = response.getHeader("AccessToken");

    assertThat(accessToken).isNotEmpty();
  }

  @Test
  @DisplayName("POST /member/login로 유효하지 않은 값은 Bad Request(400)")
  void t3() throws Exception {
    mvc.perform(
            post("/member/login")
                .content("""
                {
                  "username" : "",
                  "password" : "1234"
                }
                """.stripIndent())
                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
        .andDo(print())
        .andExpect(status().is4xxClientError());

    mvc.perform(
            post("/member/login")
                .content("""
                {
                  "username" : "user1",
                  "password" : ""
                }
                """.stripIndent())
                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
        .andDo(print())
        .andExpect(status().is4xxClientError());
  }

  @Test
  @DisplayName("POST /member/login username & password가 올바르지 않을 경우 Bad Request(400)")
  void t4() throws Exception {
    mvc.perform(post("/member/login")
            .content("""
                {
                  "username" : "user3",
                  "password" : "1234"
                }
                """.stripIndent())
            .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
        .andDo(print())
        .andExpect(status().is4xxClientError());

    mvc.perform(post("/member/login")
            .content("""
                {
                  "username" : "user1",
                  "password" : "12345"
                }
                """.stripIndent())
            .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
        .andDo(print())
        .andExpect(status().is4xxClientError());
  }

  @Test
  @DisplayName("AccessToken을 통해 Member 찾기")
  void t5() throws Exception {
    MockHttpServletResponse response = mvc.perform(post("/member/login")
            .content("""
                {
                  "username" : "user1",
                  "password" : "1234"
                }
                """.stripIndent())
            .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
        .andDo(print())
        .andExpect(status().is2xxSuccessful())
        .andReturn().getResponse();

    String accessToken = response.getHeader("AccessToken");

    mvc.perform(get("/member/me")
            .header("Authorization", "Bearer " + accessToken))
        .andDo(print())
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.resultCode").value("SUCCESS"))
        .andExpect(jsonPath("$.msg").value("현재 로그인한 회원 정보"))
        .andExpect(jsonPath("$.data.id").value(1))
        .andExpect(jsonPath("$.data.username").value("user1"))
        .andExpect(jsonPath("$.data.email").value("user1@test.com"))
        .andExpect(jsonPath("$.data.createdDate").isNotEmpty())
        .andExpect(jsonPath("$.data.updatedDate").isNotEmpty())
        .andExpect(jsonPath("$.data.roleSet").isNotEmpty());
    // json 비교 코드 추가 하기
  }
}