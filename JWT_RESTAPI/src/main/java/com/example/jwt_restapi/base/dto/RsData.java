package com.example.jwt_restapi.base.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 생성
public class RsData<T> {

  private String resultCode;
  private String msg;
  private T data;

  public static <T> RsData<T> of(String resultCode, String msg, T data) {
    return new RsData<>(resultCode, msg, data);
  }
}
