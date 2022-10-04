package com.travelers.biz.repository;

import com.travelers.biz.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query(" select r from Reply r join fetch r.qna where r.id = :replyId")
    Optional<Reply> findWithQna(@Param("replyId") final Long replyId);
}
