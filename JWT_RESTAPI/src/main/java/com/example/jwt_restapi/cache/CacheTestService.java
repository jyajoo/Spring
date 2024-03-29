package com.example.jwt_restapi.cache;

import com.example.jwt_restapi.cache.dto.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CacheTestService {

  public int getInt() {
    log.info("getInt 실행됨");
    return 5;
  }

  @Cacheable("cachedInt")
  public int getCachedInt() {
    log.info("getCachedInt 실행됨");
    return 5;
  }

  @CacheEvict("cachedInt")
  public void deleteCache() {
    log.info("deleteCache 실행됨");
  }

  @CachePut("cachedInt")
  public int cachePut() {
    log.info("cachePut 실행됨");
    return 10;
  }

  @Cacheable("parameter")
  public int parameter(int a) {
    log.info("parameter 실행됨");
    return a;
  }

  @Cacheable(value = "getCacheName", key = "#cache.id")
  public String getCacheName(Cache cache) {
    log.info("getCacheName 실행됨");
    return cache.getName();
  }
}
