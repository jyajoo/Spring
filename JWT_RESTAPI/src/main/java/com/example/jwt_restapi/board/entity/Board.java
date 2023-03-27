package com.example.jwt_restapi.board.entity;

import com.example.jwt_restapi.base.entity.BaseEntity;
import com.example.jwt_restapi.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Board extends BaseEntity {

  @ManyToOne
  private Member member;

  private String subject;

  @JsonIgnore
  private String content;
}
