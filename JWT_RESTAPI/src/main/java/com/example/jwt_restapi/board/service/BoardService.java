package com.example.jwt_restapi.board.service;

import com.example.jwt_restapi.base.exception.BoardException;
import com.example.jwt_restapi.board.dto.BoardDto;
import com.example.jwt_restapi.board.entity.Board;
import com.example.jwt_restapi.board.repository.BoardRepository;
import com.example.jwt_restapi.member.entity.Member;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        .orElseThrow(() -> new BoardException("해당 게시물이 존재하지 않습니다."));

    return BoardDto.from(board);
  }
}
