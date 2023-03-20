package com.example.jwt_restapi.member.repository;

import com.example.jwt_restapi.member.entity.Member;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findByUsername(String username);

  @Query("select m from Member m join fetch m.roleSet where m.id = :memberId")
  @NotNull
  Optional<Member> findById(@NotNull @Param("memberId") Long id);
}
