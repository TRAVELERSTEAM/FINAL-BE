package com.travelers.biz.service;

import com.travelers.biz.repository.review.ReviewRepository;
import com.travelers.dto.ReviewResponse;
import com.travelers.dto.paging.PagingCorrespondence;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public PagingCorrespondence.Response<ReviewResponse.SimpleInfo> showReviews(
            final Long productId,
            final PagingCorrespondence.Request pagingInfo
    ) {
        return reviewRepository.findSimpleList(productId, pagingInfo.toPageable());
    }
}
