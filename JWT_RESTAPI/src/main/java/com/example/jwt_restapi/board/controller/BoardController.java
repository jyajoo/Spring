package com.example.jwt_restapi.board.controller;

import com.example.jwt_restapi.base.dto.ResultCode;
import com.example.jwt_restapi.base.dto.RsData;
import com.example.jwt_restapi.board.dto.BoardDto;
import com.example.jwt_restapi.board.service.BoardService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;

  @GetMapping("/boards")
  public ResponseEntity<RsData<List<BoardDto>>> getBoardList() {
    List<BoardDto> boardList = boardService.getBoardList();

    return ResponseEntity.ok()
        .body(RsData.of(ResultCode.SUCCESS, "게시물 전체 조회", boardList));
  }

  @GetMapping("/board/{id}")
  public ResponseEntity<RsData<BoardDto>> getBoard(@PathVariable Long id) {
    BoardDto board = boardService.getBoard(id);
    return ResponseEntity.ok()
        .body(RsData.of(ResultCode.SUCCESS, "게시물 단건 조회", board));
  }
}
