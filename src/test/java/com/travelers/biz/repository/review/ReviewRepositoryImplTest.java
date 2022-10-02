package com.travelers.biz.repository.review;

import com.travelers.config.DBSliceTest;
import com.travelers.dto.ReviewResponse;
import com.travelers.dto.paging.PagingCorrespondence;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

@DBSliceTest
class ReviewRepositoryImplTest {

    @Autowired
    private ReviewRepository reviewRepository;

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
}