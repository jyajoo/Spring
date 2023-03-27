package com.example.jwt_restapi.board.repository;

import com.example.jwt_restapi.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
