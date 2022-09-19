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
                .gender(Gender.MAIL)
                .birth("1998-09-11")
                .build();

        memberRepository.save(member);

        Optional<Member> getMember = memberRepository.findById(1L);

        // 회원가입이 되있나 확인!
        assertThat(getMember.get().getGender()).isEqualTo(Gender.MAIL);
        assertThat(getMember.get().getAuthority()).isEqualTo(Authority.ROLE_USER);
        assertThat(getMember.get().getEmail()).isEqualTo("test@naver.com");

    }
}
