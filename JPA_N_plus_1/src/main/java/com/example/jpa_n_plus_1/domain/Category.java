package com.example.jpa_n_plus_1.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

@Entity
@NoArgsConstructor
@Setter
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String content;

//  @Fetch(FetchMode.SUBSELECT)
//  @BatchSize(size = 10)
  @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
  private List<Board> boardList = new ArrayList<>();

  public Category(String content) {
    this.content = content;
  }
}
