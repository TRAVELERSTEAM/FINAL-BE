package com.travelers.biz.repository.query;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.travelers.biz.domain.notify.NotifyType;
import com.travelers.biz.domain.notify.QNotify;
import com.travelers.biz.repository.query.config.QuerydslSupports;
import com.travelers.dto.NotifyResponse;
import com.travelers.dto.PagingResponse;
import com.travelers.dto.QNotifyResponse_SimpleInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import static com.travelers.biz.domain.QMember.member;
import static com.travelers.biz.domain.notify.QNotify.notify;

public class NotifyRepositoryImpl extends QuerydslSupports implements NotifyRepositoryQuery {

    private static final QNotify N = notify;

    public NotifyRepositoryImpl(EntityManager em) {
        super(notify, em);
    }

    private <T> JPAQuery<T> selectFromWhere(final NotifyType notifyType, ConstructorExpression<T> dtoResponse) {
        return select(dtoResponse)
                .from(N)
                .where(N.notifyType.eq(notifyType));
    }

    private JPAQuery<Long> countQuery(final NotifyType type) {
        return select(N.count())
                .from(N)
                .where(N.notifyType.eq(type));
    }

    @Override
    public PagingResponse<NotifyResponse.SimpleInfo> findSimpleList(final NotifyType notifyType, final Pageable pageable) {
        return PagingResponse.from(
                applyPagination(pageable,
                        () -> selectFromWhere(notifyType,
                                new QNotifyResponse_SimpleInfo(
                                        N.sequence,
                                        member.username,
                                        N.content,
                                        N.createdAt)
                        )
                                .join(N.writer, member)
                                .offset(pageable.getOffset())
                                .limit(pageable.getPageSize())
                                .orderBy(N.createdAt.desc()),
                        countQuery(notifyType)
                )
        );
    }

}
