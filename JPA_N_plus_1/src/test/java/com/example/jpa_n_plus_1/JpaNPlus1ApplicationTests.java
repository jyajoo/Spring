package com.example.jpa_n_plus_1;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.jpa_n_plus_1.domain.Board;
import com.example.jpa_n_plus_1.domain.Category;
import com.example.jpa_n_plus_1.repository.BoardRepository;
import com.example.jpa_n_plus_1.repository.CategoryRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class JpaNPlus1ApplicationTests {

  @Autowired
  private BoardRepository boardRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Test
  void contextLoads() {
  }

  @Test
  @DisplayName("initData")
  @Rollback(value = false)
  void test() {
    List<Category> categoryList = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      Category category = new Category("Category" + i );
      Category savedCategory = categoryRepository.save(category);
      List<Board> boardList = new ArrayList<>();
      for (int j = 0; j < 10; j++) {
        boardList.add(new Board("Board" + j, "Board", savedCategory));
      }
      boardRepository.saveAll(boardList);
      savedCategory.setBoardList(boardList);
      categoryList.add(savedCategory);
    }
    categoryRepository.saveAll(categoryList);
  }

  @Test
  @DisplayName("N + 1 문제")
  void test2() {
    List<Category> categoryList = categoryRepository.findAll();
    assertThat(categoryList.size()).isEqualTo(10);
  }

  @Test
  @DisplayName("Fetch Join")
  void test3() {
    List<Category> categoryList = categoryRepository.findAllJoinFetch();
    assertThat(categoryList.size()).isEqualTo(10);
  }

  @Test
  @DisplayName("일반 join")
  void test4() {
    List<Category> categoryList = categoryRepository.findAllInnerJoin();
    assertThat(categoryList.size()).isEqualTo(10);
  }

  @Test
  @DisplayName("Entity Graph")
  void test5() {
    List<Category> categoryList = categoryRepository.findAllEntityGraph();
    assertThat(categoryList.size()).isEqualTo(10);
  }

}
