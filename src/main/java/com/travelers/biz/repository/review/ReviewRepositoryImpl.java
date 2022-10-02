package com.travelers.biz.repository.review;

import com.travelers.biz.domain.QReview;
import com.travelers.biz.repository.notify.config.QuerydslSupports;
import com.travelers.dto.QReviewResponse_SimpleInfo;
import com.travelers.dto.ReviewResponse;
import com.travelers.dto.paging.PagingCorrespondence;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import static com.travelers.biz.domain.QMember.member;
import static com.travelers.biz.domain.QReview.review;

public class ReviewRepositoryImpl extends QuerydslSupports implements ReviewRepositoryQuery {

    private static final QReview R = review;

    public ReviewRepositoryImpl(EntityManager em) {
        super(em);
    }

    public PagingCorrespondence.Response<ReviewResponse.SimpleInfo> findSimpleList(final Long productId, final Pageable pageable) {
        return PagingCorrespondence.Response.from(
                applyPagination(pageable,
                        () -> select(
                                new QReviewResponse_SimpleInfo(
                                        R.id,
                                        member.username,
                                        R.content,
                                        R.createdAt
                                ))
                                .from(R)
                                .join(R.writer, member)
                                .where(R.placeToTravel.id.eq(productId))
                                .offset(pageable.getOffset())
                                .limit(pageable.getPageSize())
                                .orderBy(R.id.desc()),
                        select(R.count())
                                .from(R)
                                .where(R.placeToTravel.id.eq(productId))
                )
        );
    }
}
