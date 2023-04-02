package com.example.jwt_restapi.cache;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.jwt_restapi.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional // "트랜잭션"이 되도록 보장
@ActiveProfiles("test") // 테스트 수행 시, 실행할 프로필 지정
class CacheTest {

  @Autowired
  private MemberService memberService;

  /**
   * getInt 실행됨
   * 5
   * getInt 실행됨
   * 5
   */
  @Test
  @DisplayName("캐시 사용 전")
  void t1() {
    int cachedInt = memberService.getInt();
    assertThat(cachedInt).isEqualTo(5);
    System.out.println(cachedInt);

    cachedInt = memberService.getInt();
    assertThat(cachedInt).isEqualTo(5);
    System.out.println(cachedInt);
  }

  /**
   * getCachedInt 실행됨
   * 5
   * 5
   */
  @Test
  @DisplayName("캐시 사용 후")
  void t2() {
    int cachedInt = memberService.getCachedInt();
    assertThat(cachedInt).isEqualTo(5);
    System.out.println(cachedInt);

    cachedInt = memberService.getCachedInt();
    assertThat(cachedInt).isEqualTo(5);
    System.out.println(cachedInt);
  }

  @Test
  @DisplayName("캐시 삭제_CacheEvict")
  void t3() {
    int cachedInt = memberService.getCachedInt();
    assertThat(cachedInt).isEqualTo(5);
    System.out.println(cachedInt);

    cachedInt = memberService.getCachedInt();
    assertThat(cachedInt).isEqualTo(5);
    System.out.println(cachedInt);

    memberService.deleteCache();

    cachedInt = memberService.getCachedInt();
    assertThat(cachedInt).isEqualTo(5);
    System.out.println(cachedInt);
  }
}
