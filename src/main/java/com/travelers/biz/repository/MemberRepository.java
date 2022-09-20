package com.travelers.biz.repository;

import com.travelers.biz.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 맴버 찾기(이메일)
    Optional<Member> findByEmail(String email);

    // 맴버 중복체크(이메일)
    boolean existsByEmail(String email);

}
