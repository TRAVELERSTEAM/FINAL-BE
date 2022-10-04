package com.travelers.biz.repository.review;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travelers.config.DBSliceTest;
import com.travelers.dto.ReviewResponse;
import com.travelers.dto.paging.PagingCorrespondence;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

@DBSliceTest
class ReviewRepositoryImplTest {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private JPAQueryFactory qf;

    @Test
    @DisplayName("후기 출력")
    void findSimpleList() {
        final PageRequest of = PageRequest.of(0, 5);

        final PagingCorrespondence.Response<ReviewResponse.SimpleInfo> simpleList = reviewRepository.findSimpleList(1L, of);
        List<ReviewResponse.SimpleInfo> contents = simpleList.getContents();

        then(contents.size()).isSameAs(5);
        then(simpleList.getStartPage()).isSameAs(1);
        then(simpleList.getEndPage()).isSameAs(2);
        then(contents).extracting("content")
                .containsExactly(
                        "이중권의 괌 여행 후기",
                        "김윤겸의 괌 여행 후기",
                        "정성훈의 괌 여행 후기",
                        "이남대의 괌 여행 후기",
                        "이민형의 괌 여행 후기"
                );
    }

    @Test
    @DisplayName("존재하지 않는 review를 조회하면 예외가 터진다.")
    void none_review_then_throw() {
        thenThrownBy(() -> reviewRepository.findDetailInfo(25L)
                .orElseThrow());
    }

    @ParameterizedTest
    @MethodSource("repeatableTest")
    void parameterized(final Long reviewId, final String title, final String[] aroundTitle) {
        ReviewResponse.DetailInfo detailInfo = reviewRepository.findDetailInfo(reviewId)
                .orElseThrow(NullPointerException::new);

        then(detailInfo.getReviewId()).isSameAs(reviewId);
        then(detailInfo.getContent()).isEqualTo(title);
        then(detailInfo.getAroundTitles())
                .extracting("title", String.class)
                .containsExactly(aroundTitle);
    }

    private static Stream<Arguments> repeatableTest() {
        return Stream.of(
                Arguments.of(1L, "김현준의 괌 여행 후기", new String[]{"5번 후기"}),
                Arguments.of(24L, "이중권의 유럽 여행 후기", new String[]{"20번 후기"}),
                Arguments.of(14L, "정성훈의 하와이 여행 후기", new String[]{"10번 후기", "18번 후기"}),
                Arguments.of(4L, "김현준의 유럽 여행 후기", new String[]{"8번 후기"}),
                Arguments.of(21L, "이중권의 괌 여행 후기", new String[]{"17번 후기"})
        );
    }
}