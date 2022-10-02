package com.travelers.biz.repository.review;

import com.travelers.dto.ReviewResponse;
import com.travelers.dto.paging.PagingCorrespondence;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReviewRepositoryQuery {

    PagingCorrespondence.Response<ReviewResponse.SimpleInfo> findSimpleList(final Long productId, final Pageable pageable);

    Optional<ReviewResponse.DetailInfo> findDetailInfo(final Long reviewId);
}
