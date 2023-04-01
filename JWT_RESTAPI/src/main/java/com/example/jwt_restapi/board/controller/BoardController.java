package com.example.jwt_restapi.board.controller;

import com.example.jwt_restapi.base.dto.ResultCode;
import com.example.jwt_restapi.base.dto.RsData;
import com.example.jwt_restapi.board.dto.BoardDto;
import com.example.jwt_restapi.board.dto.BoardUpdateRequest;
import com.example.jwt_restapi.board.service.BoardService;
import com.example.jwt_restapi.member.dto.MemberContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "BoardController", description = "게시물 조회 및 CRUD 기능을 제공하는 컨트롤러")
public class BoardController {

  private final BoardService boardService;

  @GetMapping("/boards")
  @Operation(summary = "게시물 전체 조회", description = "전체 게시물 목록을 제공합니다.")
  public ResponseEntity<RsData<List<BoardDto>>> getBoardList() {
    List<BoardDto> boardList = boardService.getBoardList();

    return ResponseEntity.ok()
        .body(RsData.of(ResultCode.SUCCESS, "게시물 전체 조회", boardList));
  }

  @GetMapping("/board/{id}")
  @Operation(summary = "게시물 단건 조회", description = "하나의 게시물에 대한 정보를 제공합니다.")
  public ResponseEntity<RsData<BoardDto>> getBoard(
      @Parameter(description = "게시물 ID", required = true, example = "1") @PathVariable Long id) {
    BoardDto board = boardService.getBoard(id);
    return ResponseEntity.ok()
        .body(RsData.of(ResultCode.SUCCESS, "게시물 단건 조회", board));
  }

  @DeleteMapping("/board/{id}")
  @Operation(summary = "게시물 삭제", description = "게시물을 삭제 처리합니다.")
  public ResponseEntity<RsData<Object>> deleteBoard(
      @AuthenticationPrincipal MemberContext memberContext,
      @Parameter(description = "게시물 ID", required = true, example = "1") @PathVariable Long id) {
    boardService.deleteBoard(memberContext, id);
    return ResponseEntity.ok()
        .body(RsData.of(ResultCode.SUCCESS, "게시물 삭제", null));
  }

  @PatchMapping("/board/{id}")
  @Operation(summary = "게시물 수정", description = "게시물을 수정합니다.")
  public ResponseEntity<RsData<BoardDto>> updateBoard(
      @AuthenticationPrincipal MemberContext memberContext,
      @Parameter(description = "게시물 ID", required = true, example = "1") @PathVariable Long id,
      @Valid @RequestBody BoardUpdateRequest boardUpdateRequest) {

    BoardDto boardDto = boardService.updateBoard(memberContext, id, boardUpdateRequest);
    return ResponseEntity.ok()
        .body(RsData.of(ResultCode.SUCCESS, "게시물 수정", boardDto));
  }
}
