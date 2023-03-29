package com.example.jwt_restapi.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BoardUpdateRequest {

  @NotBlank(message = "제목을 입력해주세요")
  private String subject;

  @NotBlank(message = "내용을 입력해주세요")
  private String content;
}
