package com.example.app.domain;

import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true) // callSuper = ture : 부모 클래스 정보까지 출력
public class GenFile extends BaseEntity{
  private String relTypeCode;
  private int relId;
  private String typeCode;
  private String type2Code;
  private String fileExtTypeCode;
  private String fileExtType2Code;
  private int fileSize;
  private int fileNo;
  private String fileExt;
  private String fileDir;
  private String originFileName;
}
