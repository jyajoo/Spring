package com.example.jwt_restapi.board.service;

import com.example.jwt_restapi.board.entity.Board;
import com.example.jwt_restapi.board.repository.BoardRepository;
import com.example.jwt_restapi.member.entity.Member;
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
}
