package com.travelers.biz.repository.qna;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPAExpressions;
import com.travelers.biz.domain.QMember;
import com.travelers.biz.domain.QQna;
import com.travelers.biz.domain.QReply;
import com.travelers.biz.repository.notify.config.QuerydslSupports;
import com.travelers.dto.QQnaResponse_DetailInfo;
import com.travelers.dto.QQnaResponse_ReplyResponse;
import com.travelers.dto.QQnaResponse_SimpleInfo;
import com.travelers.dto.QnaResponse;
import com.travelers.dto.paging.PagingCorrespondence;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static com.travelers.biz.domain.QMember.member;
import static com.travelers.biz.domain.QQna.qna;
import static com.travelers.biz.domain.QReply.reply;

public class QnaRepositoryImpl extends QuerydslSupports implements QnaRepositoryQuery {

    public QnaRepositoryImpl(EntityManager em) {
        super(em);
    }

    @Override
    public PagingCorrespondence.Response<QnaResponse.SimpleInfo> findSimpleList(
            final Long memberId,
            final Pageable pageable
    ) {
        return PagingCorrespondence.Response.from(
                applyPagination(pageable,
                        () -> select(new QQnaResponse_SimpleInfo(
                                qna.id,
                                qna.status,
                                qna.title,
                                qna.createdAt
                        ))
                                .from(qna)
                                .where(qna.writer.id.eq(memberId))
                                .offset(pageable.getOffset())
                                .limit(pageable.getPageSize())
                                .orderBy(qna.id.desc()),
                        select(qna.count()).from(qna)
                ));
    }

    @Override
    public Optional<QnaResponse.DetailInfo> findDetailInfo(final Long qnaId) {
        Optional<QnaResponse.DetailInfo> detailInfo = Optional.ofNullable(
                select(new QQnaResponse_DetailInfo(
                        qna.id,
                        qna.status,
                        qna.title,
                        qna.content,
                        qna.createdAt
                ))
                        .from(qna)
                        .fetchOne()
        );
        detailInfo.ifPresent(setReplyList(qnaId));

        return detailInfo;
    }

    private Consumer<QnaResponse.DetailInfo> setReplyList(final Long qnaId) {
        return e -> e.addReplyList(
                select(new QQnaResponse_ReplyResponse(
                        reply.id,
                        member.username,
                        reply.content,
                        reply.createdAt
                ))
                        .from(reply)
                        .where(reply.qna.id.eq(qnaId))
                        .join(reply.writer, member)
                        .fetch()
        );
    }

}
