package com.travelers.biz.repository.qna;

import com.travelers.dto.QnaResponse;
import com.travelers.dto.paging.PagingCorrespondence;
import org.springframework.data.domain.Pageable;

public interface QnaRepositoryQuery {
    PagingCorrespondence.Response<QnaResponse.SimpleList> findSimpleList(final Pageable pageable);
}
