package com.example.jpa_n_plus_1.repository;

import com.example.jpa_n_plus_1.domain.Category;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  @Query("select DISTINCT c from Category c join fetch c.boardList")
  List<Category> findAllJoinFetch();

  @Query("select DISTINCT c from Category c join c.boardList")
  List<Category> findAllInnerJoin();

  @EntityGraph(attributePaths = {"boardList"})
  @Query("select DISTINCT c from Category c")
  List<Category> findAllEntityGraph();
}
