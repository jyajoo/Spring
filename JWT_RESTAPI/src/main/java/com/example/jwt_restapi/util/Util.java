package com.example.jwt_restapi.util;


import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public class Util {

  public static class Spring {
    public static <T> ResponseEntity<T> responseEntityOf(HttpHeaders headers) {
      return ResponseEntity.ok().headers(headers).build();
    }
  }
}
