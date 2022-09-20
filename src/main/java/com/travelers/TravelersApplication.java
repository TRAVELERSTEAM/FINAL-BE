package com.travelers;

import com.travelers.biz.domain.Authority;
import com.travelers.biz.domain.Gender;
import com.travelers.biz.domain.Member;
import com.travelers.biz.repository.MemberRepository;
import com.travelers.dto.MemberRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
public class TravelersApplication {

    @Value("${email}") String email;
    @Value("${password}") String password;

    public static void main(String[] args) {
        SpringApplication.run(TravelersApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(MemberRepository memberRepository, PasswordEncoder passwordEncoder){

        return (arg) -> {
            Member member = Member.builder()
                    .email(email)
                    .username("관리자")
                    .password(passwordEncoder.encode(password))
                    .birth("1998-09-16")
                    .gender(Gender.MALE)
                    .tel("01055222222")
                    .hobby("코딩")
                    .prefer("상관없음")
                    .build();

            member.changeAuthority(Authority.ROLE_ADMIN);
            memberRepository.save(member);
        };
    }
}
