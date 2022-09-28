package com.travelers.biz.repository;

import com.travelers.biz.domain.Authority;
import com.travelers.biz.domain.Gender;
import com.travelers.biz.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 테스트")
    void 회원가입_테스트() {

        Member member = Member.builder()
                .email("test1234@naver.com")
                .username("홍길동")
                .password(passwordEncoder.encode("1234"))
                .tel("01022225555")
                .birth("19980911")
                .groupTrip("남자끼리")
                .area("중남미/북미")
                .theme("문화탐방")
                .gender(Gender.MALE)
                .build();

        memberRepository.save(member);

        Optional<Member> getMember = memberRepository.findByEmail("test1234@naver.com");

        log.info(getMember.get().getCreatedAt() + "");
        log.info(getMember.get().getTheme() + "");
        log.info(getMember.get().getPassword() + "");

        // 회원가입이 되있나 확인!
        assertThat(getMember.get().getGender()).isEqualTo(Gender.MALE);
        assertThat(getMember.get().getAuthority()).isEqualTo(Authority.ROLE_USER);
        assertThat(getMember.get().getEmail()).isEqualTo("test1234@naver.com");
    }


}
