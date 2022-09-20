package com.travelers.biz.repository;

import com.travelers.biz.domain.Authority;
import com.travelers.biz.domain.Gender;
import com.travelers.biz.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
@Slf4j
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입 테스트")
    void 회원가입_테스트() {

        Member member = Member.builder()
                .email("test@naver.com")
                .username("홍길동")
                .password("1234")
                .tel("01022225555")
                .birth("1998-09-11")
                .hobby("등산")
                .prefer("남자끼리, 누구든지")
                .gender(Gender.MALE)
                .build();

        memberRepository.save(member);

        Optional<Member> getMember = memberRepository.findById(1L);

        log.info(getMember.get().getCreatedAt() + "");
        log.info(getMember.get().getPrefer() + "");

        // 회원가입이 되있나 확인!
        assertThat(getMember.get().getGender()).isEqualTo(Gender.MALE);
        assertThat(getMember.get().getAuthority()).isEqualTo(Authority.ROLE_USER);
        assertThat(getMember.get().getEmail()).isEqualTo("test@naver.com");

    }
}
