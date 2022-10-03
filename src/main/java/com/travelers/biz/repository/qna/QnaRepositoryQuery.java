package com.travelers.biz.repository.qna;

import com.travelers.dto.QnaResponse;
import com.travelers.dto.paging.PagingCorrespondence;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface QnaRepositoryQuery {
    PagingCorrespondence.Response<QnaResponse.SimpleInfo> findSimpleList(final Long memberId, final Pageable pageable);
    Optional<QnaResponse.DetailInfo> findDetailInfo(final Long qnaId);
}
