package com.example.jpa_n_plus_1.repository;

import com.example.jpa_n_plus_1.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
