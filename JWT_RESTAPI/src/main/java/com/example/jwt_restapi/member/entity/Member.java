package com.example.jwt_restapi.member.entity;

import com.example.jwt_restapi.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Setter
@Getter
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 생성
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Member extends BaseEntity {

  @Column(unique = true)
  private String username;

  @JsonIgnore
  private String password;

  private String email;

  public Member(long id) {
    super(id);
  }
}
