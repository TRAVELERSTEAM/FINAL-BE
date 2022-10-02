package com.travelers.api.controller;

import com.travelers.biz.service.ReviewService;
import com.travelers.dto.ReviewResponse;
import com.travelers.dto.paging.PagingCorrespondence;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping()
    public ResponseEntity<PagingCorrespondence.Response<ReviewResponse.SimpleInfo>> showAll(
            final Long productId,
            final PagingCorrespondence.Request pagingInfo
    ) {
        return ResponseEntity.ok(
                reviewService.showReviews(productId, pagingInfo)
        );
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse.DetailInfo> showOne(
            final Long reviewId
    ) {
        return ResponseEntity.ok(
                reviewService.showReview(reviewId)
        );
    }
}
