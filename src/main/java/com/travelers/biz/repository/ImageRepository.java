package com.travelers.biz.repository;

import com.travelers.biz.domain.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query(" select i from NotifyImage i where i.notify.id = :notifyId")
    List<Image> findAllByNotifyId(@Param("notifyId") final Long notifyId);

    @Query(" select i from ReviewImage i where i.review.id = :reviewId")
    List<Image> findAllByReviewId(@Param("reviewId") final Long reviewId);

    @Query(" select i from QnaImage i where i.qna.id = :qnaId")
    List<Image> findAllByQnaId(@Param("qnaId") final Long qnaId);
}
