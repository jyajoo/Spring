package com.example.jwt_restapi;

import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc // @WebMvcTest와 유사한 어노테이션, 컨트롤러뿐만 아니라 서비스, 리포지토리 객체도 메모리에 올림
@Transactional // "트랜잭션"이 되도록 보장
@ActiveProfiles("test") // 테스트 수행 시, 실행할 프로필 지정
class JwtRestapiApplicationTests {

  @Autowired
  private MockMvc mvc; // 요청과 응답을 의미하는 가짜 객체로 실제 서버에 배포하지 않고 테스트를 위한 라이브러리

  /*
  Post /member/login에 JSON 형태의 content를 담아 요청하면, 결과가 성공이어야 한다.

   */
  @Test
  @DisplayName("POST /member/login 은 로그인 처리 URL")
  void t1() throws Exception {
    ResultActions resultActions = mvc.perform(
        MockMvcRequestBuilders.post("/member/login")
            .content("""
                {
                  "username" : "user1",
                  "password" : "1234"
                }
                """.stripIndent())
            .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
    ).andDo(MockMvcResultHandlers.print());

    resultActions.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }
}
