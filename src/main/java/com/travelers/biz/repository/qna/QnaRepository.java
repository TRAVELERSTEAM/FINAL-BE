package com.travelers.biz.repository.qna;

import com.travelers.biz.domain.Qna;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnaRepository extends JpaRepository<Qna, Long>, QnaRepositoryQuery{
}
