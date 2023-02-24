package com.example.jwt_restapi.member.repository;

import com.example.jwt_restapi.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
