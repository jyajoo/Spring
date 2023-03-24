package com.example.jwt_restapi.member.repository;

import com.example.jwt_restapi.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

  @Query("select m from Member m join fetch m.roleSet where m.username = :username")
  Optional<Member> findMemberByUsername(@Param("username") String username);

  @Query("select m from Member m join fetch m.roleSet where m.id = :memberId")
  Optional<Member> findMemberById(@Param("memberId") Long id);
}
