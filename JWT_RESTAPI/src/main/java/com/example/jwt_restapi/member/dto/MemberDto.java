package com.example.jwt_restapi.member.dto;

import com.example.jwt_restapi.member.entity.MemberRole;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MemberDto {

  private final Long id;
  private final String username;
  private final String email;
  private final LocalDateTime createdDate;
  private final LocalDateTime updatedDate;
  private final Set<MemberRole> roleSet;
}
