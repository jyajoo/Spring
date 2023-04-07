package com.example.jwt_restapi.board.service;

import static com.example.jwt_restapi.base.exception.ErrorCode.*;
import com.example.jwt_restapi.base.exception.BoardException;
import com.example.jwt_restapi.board.dto.BoardDto;
import com.example.jwt_restapi.board.dto.BoardUpdateRequest;
import com.example.jwt_restapi.board.entity.Board;
import com.example.jwt_restapi.board.repository.BoardRepository;
import com.example.jwt_restapi.member.dto.MemberContext;
import com.example.jwt_restapi.member.entity.Member;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

  private final BoardRepository boardRepository;

  public Board create(Member member, String subject, String content) {
    Board board = Board.builder()
        .member(member)
        .subject(subject)
        .content(content)
        .build();

    boardRepository.save(board);
    return board;
  }

  public List<BoardDto> getBoardList() {
    return boardRepository.findAll().stream().map(BoardDto::from)
        .collect(Collectors.toList());
  }

  public BoardDto getBoard(Long id) {
    Board board = boardRepository.findById(id)
        .orElseThrow(() -> new BoardException(BOARD_NOT_FOUND));

    return BoardDto.from(board);
  }

  public void deleteBoard(MemberContext memberContext, Long id) {
    Board board = boardRepository.findById(id)
        .orElseThrow(() -> new BoardException(BOARD_NOT_FOUND));
    if (isNotBoardCreatedByMember(memberContext, board)) {
      throw new BoardException(BOARD_AUTHOR_MISMATCH);
    }

    boardRepository.delete(board);
  }

  private boolean isNotBoardCreatedByMember(MemberContext memberContext, Board board) {
    return !memberContext.getId().equals(board.getMember().getId());
  }

  @Transactional
  public BoardDto updateBoard(MemberContext memberContext, Long id, BoardUpdateRequest boardUpdateRequest) {
    Board board = boardRepository.findById(id)
        .orElseThrow(() -> new BoardException(MEMBER_NOT_FOUND));
    if (isNotBoardCreatedByMember(memberContext, board)) {
      throw new BoardException(BOARD_AUTHOR_MISMATCH);
    }

    board.update(boardUpdateRequest);
    return BoardDto.from(board);
  }
}
