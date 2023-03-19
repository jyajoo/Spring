package com.example.jwt_restapi.member.entity;

import com.example.jwt_restapi.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import java.util.Set;
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

  /*
  @ElementCollection : 컬렉션의 각 요소를 저장할 수 있다. 부모 Entity와 독립적으로 사용 X
  @CollectionTable : @ElementCollection과 함께 사용될 때, 생성될 테이블의 이름 지정
   */
  @ElementCollection(targetClass = MemberRole.class)
  @CollectionTable(name = "member_roles",
      joinColumns = @JoinColumn(name = "member_id"))
  @Enumerated(EnumType.STRING)
  private Set<MemberRole> roleSet;
}
