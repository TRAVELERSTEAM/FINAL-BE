package com.travelers.biz.service;

import com.travelers.biz.domain.Member;
import com.travelers.biz.domain.Review;
import com.travelers.biz.domain.image.Image;
import com.travelers.biz.domain.image.ReviewImage;
import com.travelers.biz.domain.product.Product;
import com.travelers.biz.repository.ImageRepository;
import com.travelers.biz.repository.MemberRepository;
import com.travelers.biz.repository.ProductRepository;
import com.travelers.biz.repository.TravelPlaceRepository;
import com.travelers.biz.repository.review.ReviewRepository;
import com.travelers.biz.service.handler.FileUploader;
import com.travelers.dto.BoardRequest;
import com.travelers.dto.ReviewResponse;
import com.travelers.dto.paging.PagingCorrespondence;
import com.travelers.exception.ErrorCode;
import com.travelers.exception.TravelersException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.travelers.exception.OptionalHandler.*;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final TravelPlaceRepository travelPlaceRepository;
    private final ImageRepository imageRepository;
    private final FileUploader fileUploader;

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
                .orElseThrow(() -> new TravelersException(ErrorCode.RESOURCE_NOT_FOUND));
    }

    @Transactional
    public void write(
            final Long memberId,
            final Long productId,
            final BoardRequest.Write write
    ) {
        validate(memberId, productId);

        final Member member = findMemberOrThrow(memberId, memberRepository::findById);
        final Product product = findProductOrThrow(productId, productRepository::findById);

        final Review review = Review.create(member, product, write.getTitle(), write.getContent());

        addImages(review, write);
        reviewRepository.save(review);
    }

    private void validate(final Long memberId, final  Long productId) {
        travelPlaceRepository.existsPlace(memberId, productId)
                .ifPresent(then -> {
                    throw new TravelersException(ErrorCode.NO_PERMISSIONS);
                });
    }

    @Transactional
    public void update(
            final Long memberId,
            final Long reviewId,
            final BoardRequest.Write write
    ) {
        final Review review = findReviewById(reviewId, memberId);

        review.edit(write.getTitle(), write.getContent());
        deleteImages(reviewId);
        addImages(review, write);
    }

    private Review findReviewById(
            final Long reviewId,
            final Long memberId
    ) {
        Review review = findReview(() -> reviewRepository.findById(reviewId));
        review.isSameWriter(memberId);
        return review;
    }

    private void addImages(
            final Review review,
            final BoardRequest.Write write
    ) {
        write.getUrls()
                .forEach(url -> new ReviewImage(url, review));
    }

    private void deleteImages(final Long reviewId) {
        final List<Image> images = imageRepository.findAllByReviewId(reviewId);

        final List<String> keyList = images.stream()
                .map(Image::getKey)
                .collect(Collectors.toList());

        fileUploader.delete(keyList);
        imageRepository.deleteAll(images);
    }

    @Transactional
    public void delete(
            final Long memberId,
            final Long reviewId
    ) {
        Review review = findReview(() -> reviewRepository.findById(reviewId));

        review.isSameWriter(memberId);

        deleteImages(reviewId);

        reviewRepository.delete(review);
    }

}
