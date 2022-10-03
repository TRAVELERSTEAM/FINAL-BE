package com.travelers.biz.service;

import com.travelers.biz.domain.Member;
import com.travelers.biz.domain.Product;
import com.travelers.biz.domain.Review;
import com.travelers.biz.repository.MemberRepository;
import com.travelers.biz.repository.ProductRepository;
import com.travelers.biz.repository.TravelPlaceRepository;
import com.travelers.biz.repository.review.ReviewRepository;
import com.travelers.dto.BoardRequest;
import com.travelers.dto.ReviewResponse;
import com.travelers.dto.paging.PagingCorrespondence;
import com.travelers.exception.ErrorCode;
import com.travelers.exception.OptionalHandler;
import com.travelers.exception.TravelersException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.travelers.exception.OptionalHandler.findMember;
import static com.travelers.exception.OptionalHandler.findWithNotfound;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final TravelPlaceRepository travelPlaceRepository;

    @Transactional(readOnly = true)
    public PagingCorrespondence.Response<ReviewResponse.SimpleInfo> showReviews(
            final Long productId,
            final PagingCorrespondence.Request pagingInfo
    ) {
        return reviewRepository.findSimpleList(productId, pagingInfo.toPageable());
    }

    @Transactional(readOnly = true)
    public ReviewResponse.DetailInfo showReview(
            final Long reviewId
    ) {
        return reviewRepository.findDetailInfo(reviewId)
                .orElseThrow(RuntimeException::new);
    }

    @Transactional
    public void write(
            final Long memberId,
            final Long productId,
            final BoardRequest.Write write
    ) {
        validate(memberId, productId);

        final Member member = findMember(() -> memberRepository.findById(memberId));
        final Product product = findProductById(productId);

        reviewRepository.save(
                Review.create(member, product, write.getTitle(), write.getContent())
        );
    }

    private Product findProductById(final Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new TravelersException(ErrorCode.CLIENT_BAD_REQUEST));
    }

    private void validate(final Long memberId, final  Long productId) {
        if(travelPlaceRepository.existsPlace(memberId, productId)) {
            throw new TravelersException(ErrorCode.NO_PERMISSIONS);
        }
    }

    @Transactional
    public void update(
            final Long memberId,
            final Long reviewId,
            final BoardRequest.Write write
    ) {
        final Review review = checkAuthority(memberId, reviewId);

        review.edit(write.getTitle(), write.getContent());
    }

    private Review checkAuthority(final Long memberId, final Long reviewId) {
        final Review review = findById(reviewId);

        review.checkAuthority(memberId);
        return review;
    }

    @Transactional
    public void delete(
            final Long memberId,
            final Long reviewId
    ) {
        final Review review = findById(reviewId);
        review.checkAuthority(memberId);

        reviewRepository.deleteById(reviewId);
    }

    private Review findById(final Long reviewId){
        return findWithNotfound(() -> reviewRepository.findById(reviewId));
    }
}
