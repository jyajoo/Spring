package com.example.jwt_restapi.member.dto;

import com.example.jwt_restapi.member.entity.Member;
import com.example.jwt_restapi.member.entity.MemberRole;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

/*
equals :  두 객체의 내용이 같은지, 동등성(equality) 비교
hashCode : 두 객체가 같은 객체인지, 동일성(identity) 비교
 */
@Getter
@EqualsAndHashCode(callSuper=false) // callSuper=false : 부모 클래스 고려 X
public class MemberContext extends User {

  private final Long id;
  private final String username;
  private final String email;
  private final LocalDateTime createdDate;
  private final LocalDateTime updatedDate;
  private final Set<MemberRole> roleSet;

  public MemberContext(Member member) {
    super(member.getUsername(), member.getPassword(), getAuthorities(member.getRoleSet()));
    id = member.getId();
    username = member.getUsername();
    email = member.getEmail();
    createdDate = member.getCreatedDate();
    updatedDate = member.getUpdatedDate();
    roleSet = member.getRoleSet();
  }
  private static Collection<? extends GrantedAuthority> getAuthorities(Set<MemberRole> roleSet) {
    return roleSet.stream().map(role -> new SimpleGrantedAuthority(role.name()))
        .collect(Collectors.toSet());
  }
}
