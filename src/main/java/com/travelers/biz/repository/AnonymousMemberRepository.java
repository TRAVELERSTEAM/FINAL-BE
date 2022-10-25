package com.travelers.biz.repository;

import com.travelers.biz.domain.AnonymousMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnonymousMemberRepository extends JpaRepository<AnonymousMember, Long> {
}
