package com.travelers.biz.repository;

import com.travelers.biz.domain.Gender;
import com.travelers.biz.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 전체 맴버 목록
    List<Member> findAll();

    // 맴버 찾기(이메일)
    Optional<Member> findByEmail(String email);

    // 맴버 찾기(이름, 성별, 생년월일)
    Optional<Member> findByUsernameAndBirthAndGender(String username, String birth, Gender gender);

    // 맴버 찾기(이름, 성별, 생년월일, 전화번호, 이메일)
    Optional<Member> findByUsernameAndBirthAndGenderAndTelAndEmail(String username, String birth, Gender gender, String tel, String email);

    // 맴버 중복체크(이메일)
    boolean existsByEmail(String email);

}
