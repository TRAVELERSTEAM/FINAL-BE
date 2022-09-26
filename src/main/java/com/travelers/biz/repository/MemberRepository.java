package com.travelers.biz.repository;

import com.travelers.biz.domain.Gender;
import com.travelers.biz.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 맴버 찾기(이메일)
    Optional<Member> findByEmail(String email);

    // 맴버 찾기(이름, 생년월일)
    Optional<Member> findByUsernameAndBirthAndGender(String username, String birth, Gender gender);

    // 맴버 중복체크(이메일)
    boolean existsByEmail(String email);

}
