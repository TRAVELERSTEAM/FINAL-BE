package com.travelers.biz.repository;

import com.travelers.biz.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByMemberEmail(String email);
    Optional<Token> findById(String tokenId);
}
