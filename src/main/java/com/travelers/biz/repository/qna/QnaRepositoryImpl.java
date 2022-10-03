package com.travelers.biz.repository.qna;

import com.travelers.biz.domain.QQna;
import com.travelers.biz.repository.notify.config.QuerydslSupports;
import com.travelers.dto.QQnaResponse_SimpleList;
import com.travelers.dto.QnaResponse;
import com.travelers.dto.paging.PagingCorrespondence;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import static com.travelers.biz.domain.QQna.qna;

public class QnaRepositoryImpl extends QuerydslSupports implements QnaRepositoryQuery {

    public QnaRepositoryImpl(EntityManager em) {
        super(em);
    }

    public PagingCorrespondence.Response<QnaResponse.SimpleList> findSimpleList(final Pageable pageable) {
        return PagingCorrespondence.Response.from(
                applyPagination(pageable,
                        () -> select(new QQnaResponse_SimpleList(
                                qna.id,
                                qna.status,
                                qna.title,
                                qna.createdAt
                        ))
                                .from(qna)
                                .offset(pageable.getOffset())
                                .limit(pageable.getPageSize())
                                .orderBy(qna.id.desc()),
                        select(qna.count()).from(qna)
                ));
    }
}
