package com.example.jwt_restapi.cache;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.jwt_restapi.cache.dto.Cache;
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
  @DisplayName("캐시 사용 후_Cacheable")
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

  /**
   * parameter로 들어온 값이 key 값으로 설정됨
   * parameter 실행됨
   * 10
   * parameter 실행됨
   * 5
   * 10
   */
  @Test
  @DisplayName("parameter_Cacheable")
  void t5() {
    int cachedInt = cacheTestService.parameter(10);
    assertThat(cachedInt).isEqualTo(10);
    System.out.println(cachedInt);

    cachedInt = cacheTestService.parameter(5);
    assertThat(cachedInt).isEqualTo(5);
    System.out.println(cachedInt);

    cachedInt = cacheTestService.parameter(10);
    assertThat(cachedInt).isEqualTo(10);
    System.out.println(cachedInt);
  }

  @Test
  @DisplayName("key 설정_Cacheable")
  void t6() {
    Cache cache1 = new Cache(1L, "cache1");
    Cache cache2 = new Cache(1L, "cache2");
    String cacheName = cacheTestService.getCacheName(cache1);
    String cacheName2 = cacheTestService.getCacheName(cache2);
    assertThat(cacheName).isEqualTo(cacheName2);
  }
}
