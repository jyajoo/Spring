package com.example.jwt_restapi.board.controller;

import com.example.jwt_restapi.base.dto.ResultCode;
import com.example.jwt_restapi.base.dto.RsData;
import com.example.jwt_restapi.board.dto.BoardDto;
import com.example.jwt_restapi.board.service.BoardService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;

  @GetMapping("/boards")
  public ResponseEntity<RsData<List<BoardDto>>> getBoardList() {
    List<BoardDto> boardList = boardService.getBoardList();

    return ResponseEntity.ok()
        .body(RsData.of(ResultCode.SUCCESS, null, boardList));
  }
}
