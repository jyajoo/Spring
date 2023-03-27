package com.example.jwt_restapi.board.dto;

import com.example.jwt_restapi.board.entity.Board;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class BoardDto {

  private Long id;
  private Long memberId;
  private String subject;
  private String content;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;

  public static BoardDto from(Board board) {
    BoardDto boardDto = new BoardDto();
    boardDto.id = board.getId();
    boardDto.memberId = board.getMember().getId();
    boardDto.subject = board.getSubject();
    boardDto.content = board.getContent();
    boardDto.createdDate = board.getCreatedDate();
    boardDto.updatedDate = board.getUpdatedDate();
    return boardDto;
  }
}
