package com.example.jwt_restapi.cache;

import static org.assertj.core.api.Assertions.assertThat;

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
  private CacheTestService cacheTestService;

  /**
   * getInt 실행됨
   * 5
   * getInt 실행됨
   * 5
   */
  @Test
  @DisplayName("캐시 사용 전")
  void t1() {
    int cachedInt = cacheTestService.getInt();
    assertThat(cachedInt).isEqualTo(5);
    System.out.println(cachedInt);

    cachedInt = cacheTestService.getInt();
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
    int cachedInt = cacheTestService.getCachedInt();
    assertThat(cachedInt).isEqualTo(5);
    System.out.println(cachedInt);

    cachedInt = cacheTestService.getCachedInt();
    assertThat(cachedInt).isEqualTo(5);
    System.out.println(cachedInt);
  }

  /**
   * getCachedInt 실행됨
   * 5
   * 5
   * deleteCache 실행됨
   * getCachedInt 실행됨
   * 5
   */
  @Test
  @DisplayName("캐시 삭제_CacheEvict")
  void t3() {
    int cachedInt = cacheTestService.getCachedInt();
    assertThat(cachedInt).isEqualTo(5);
    System.out.println(cachedInt);

    cachedInt = cacheTestService.getCachedInt();
    assertThat(cachedInt).isEqualTo(5);
    System.out.println(cachedInt);

    cacheTestService.deleteCache();

    cachedInt = cacheTestService.getCachedInt();
    assertThat(cachedInt).isEqualTo(5);
    System.out.println(cachedInt);
  }

  /**
   * getCachedInt 실행됨
   * 5
   * cachePut 실행됨
   * 10
   * cachePut 실행됨
   * 10
   */
  @Test
  @DisplayName("캐시 업데이트_CachePut")
  void t4() {
    int cachedInt = cacheTestService.getCachedInt();
    assertThat(cachedInt).isEqualTo(5);
    System.out.println(cachedInt);

    cachedInt = cacheTestService.cachePut();
    assertThat(cachedInt).isEqualTo(10);
    System.out.println(cachedInt);

    cachedInt = cacheTestService.cachePut();
    assertThat(cachedInt).isEqualTo(10);
    System.out.println(cachedInt);
  }
}
