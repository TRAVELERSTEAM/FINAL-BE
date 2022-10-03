package com.travelers.biz.repository;

import com.travelers.biz.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query(" select r from Reply r where r.qna.id = :qnaId")
    List<Reply> findByQnaId(@Param("qnaId") final Long qnaId);
}
