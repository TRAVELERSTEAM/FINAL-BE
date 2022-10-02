package com.travelers.biz.repository.review;

import com.querydsl.jpa.JPAExpressions;
import com.travelers.biz.domain.QReview;
import com.travelers.biz.repository.notify.config.QuerydslSupports;
import com.travelers.dto.QReviewResponse_AroundTitle;
import com.travelers.dto.QReviewResponse_DetailInfo;
import com.travelers.dto.QReviewResponse_SimpleInfo;
import com.travelers.dto.ReviewResponse;
import com.travelers.dto.paging.PagingCorrespondence;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    @Override
    public Optional<ReviewResponse.DetailInfo> findDetailInfo(final Long reviewId) {
        final Optional<ReviewResponse.DetailInfo> detailInfo =
                Optional.ofNullable(select(
                        new QReviewResponse_DetailInfo(
                                R.id,
                                R.placeToTravel.id,
                                member.username,
                                R.title,
                                R.content,
                                R.createdAt
                        ))
                        .from(R)
                        .join(R.writer, member)
                        .where(R.id.eq(reviewId))
                        .fetchFirst());


        List<ReviewResponse.AroundTitle> aroundTitles = new ArrayList<>();
        addPrev(aroundTitles, reviewId);
        addNext(aroundTitles, reviewId);

        detailInfo.ifPresent(
                e -> e.addAroundTitles(aroundTitles)
        );

        return detailInfo;
    }

    private void addPrev(List<ReviewResponse.AroundTitle> aroundTitles, final Long reviewId) {

        ReviewResponse.AroundTitle prev = getPrev(reviewId);
        if (prev != null) {
            aroundTitles.add(prev);
        }
    }

    private void addNext(List<ReviewResponse.AroundTitle> aroundTitles, final Long reviewId) {

        ReviewResponse.AroundTitle next = getNext(reviewId);
        if (next != null) {
            aroundTitles.add(next);
        }
    }

    private ReviewResponse.AroundTitle getPrev(final Long reviewId) {
        QReview sub = new QReview("reviewSub");
        Long productId = select(review.placeToTravel.id)
                .from(review)
                .where(review.id.eq(reviewId))
                .fetchOne();

        return select(new QReviewResponse_AroundTitle(
                review.id,
                review.title
        ))
                .from(review)
                .where(review.id.eq(
                        JPAExpressions.select(sub.id.max())
                                .from(sub)
                                .where(sub.id.lt(reviewId),
                                        sub.placeToTravel.id.eq(productId)
                                )))
                .fetchFirst();
    }

    private ReviewResponse.AroundTitle getNext(final Long reviewId) {
        QReview sub = new QReview("reviewSub");
        Long productId = select(review.placeToTravel.id)
                .from(review)
                .where(review.id.eq(reviewId))
                .fetchOne();

        return select(new QReviewResponse_AroundTitle(
                review.id,
                review.title
        ))
                .from(review)
                .where(review.id.eq(
                        JPAExpressions.select(sub.id.min())
                                .from(sub)
                                .where(sub.id.gt(reviewId),
                                        sub.placeToTravel.id.eq(productId)
                                )))
                .fetchFirst();

    }


}
