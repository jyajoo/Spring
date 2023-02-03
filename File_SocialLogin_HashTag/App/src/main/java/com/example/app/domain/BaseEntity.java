package com.example.app.domain;

import static lombok.AccessLevel.*;

import java.time.LocalDateTime;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@SuperBuilder // 상속 관계에서의 Builder (부모 객체 필드값도 지정 가능, 부모 자식 모두 붙여주어야 함)
@MappedSuperclass // 공통 매핑 정보가 필요한 경우 사용
@NoArgsConstructor(access = PROTECTED) // 파라미터가 없는 기본 생성자 자동 생성
@EntityListeners(AuditingEntityListener.class) //  Auditing 기능 추가
@ToString
public class BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @CreatedDate
  private LocalDateTime createDate;

  @LastModifiedDate
  private LocalDateTime updateDate;
}
