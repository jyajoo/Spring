package com.example.jwt_restapi.base.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass // 부모 클래스를 상속받는 자식 클래스에 매핑 정보 제공
@SuperBuilder // 부모 클래스를 상속받는 자식 클래스를 만들 때, 부모 클래스의 필드값도 초기화
@NoArgsConstructor // 기본 생성자 생성(접근 제한을 protected로 설정 : 누락 방지)
@EntityListeners(AuditingEntityListener.class) // 생성일 / 수정일 /생성자 자동화
@ToString // 문자열 리턴
public abstract class BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @CreatedDate
  @Column(updatable = false)  // 생성일은 update되지 않도록 설정
  private LocalDateTime createdDate;

  @LastModifiedDate
  private LocalDateTime updatedDate;
}
