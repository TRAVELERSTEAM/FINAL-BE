package com.travelers.biz.repository.notify;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.travelers.biz.domain.notify.NotifyType;
import com.travelers.biz.domain.notify.QNotify;
import com.travelers.biz.repository.notify.config.QuerydslSupports;
import com.travelers.dto.*;
import com.travelers.dto.paging.PagingCorrespondence;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import static com.travelers.biz.domain.QMember.member;
import static com.travelers.biz.domain.notify.QNotify.notify;

public class NotifyRepositoryImpl extends QuerydslSupports implements NotifyRepositoryQuery {

    private static final QNotify N = notify;

    public NotifyRepositoryImpl(EntityManager em) {
        super(em);
    }

    private <T> JPAQuery<T> selectFromWhere(final NotifyType notifyType, ConstructorExpression<T> dtoResponse) {
        return select(dtoResponse)
                .from(N)
                .where(N.notifyType.eq(notifyType)
                );
    }

    private JPAQuery<Long> countQuery(final NotifyType type) {
        return select(N.count()).from(N)
                .where(N.notifyType.eq(type));
    }

    @Override
    public PagingCorrespondence.Response<NotifyResponse.SimpleInfo> findSimpleList(final NotifyType notifyType, final Pageable pageable) {

        return PagingCorrespondence.Response.from(
                applyPagination(pageable,
                        () -> selectFromWhere(notifyType,
                                new QNotifyResponse_SimpleInfo(
                                        N.id,
                                        N.sequence,
                                        member.username,
                                        N.title,
                                        N.createdAt))
                                .join(N.writer, member)
                                .offset(pageable.getOffset())
                                .limit(pageable.getPageSize())
                                .orderBy(N.id.desc()),
                        countQuery(notifyType)
                )
        );
    }

    @Override
    public NotifyResponse.DetailInfo findDetail(final Long notifyId, final NotifyType notifyType) {
        final NotifyResponse.DetailInfo detailInfo =
                selectFromWhere(
                        notifyType,
                        new QNotifyResponse_DetailInfo(
                                N.id,
                                N.sequence,
                                member.username,
                                N.title,
                                N.content,
                                N.createdAt))
                        .join(N.writer, member)
                        .where(N.id.eq(notifyId))
                        .fetchFirst();

        detailInfo.addAroundTitles(getAround(notifyId));

        return detailInfo;
    }

    private List<NotifyResponse.AroundTitle> getAround(final Long notifyId) {
        return getAround(getPrev(notifyId), getNext(notifyId));
    }

    private List<NotifyResponse.AroundTitle> getAround(final NotifyResponse.AroundTitle... aroundTitles) {
        List<NotifyResponse.AroundTitle> list = new ArrayList<>();
        for (NotifyResponse.AroundTitle aroundTitle : aroundTitles) {
            if (aroundTitle != null)
                list.add(aroundTitle);
        }
        return list;
    }


    private NotifyResponse.AroundTitle getNext(final Long notifyId) {
        return getWhere(N.id.min(), N.id.gt(notifyId)).fetchFirst();
    }

    private NotifyResponse.AroundTitle getPrev(final Long notifyId) {
        return getWhere(N.id.max(), N.id.lt(notifyId)).fetchFirst();
    }

    private JPAQuery<NotifyResponse.AroundTitle> getWhere(final NumberExpression<Long> target, final BooleanExpression condition) {
        return getFrom().where(N.id.eq(JPAExpressions.select(target).from(N).where(condition)));
    }

    private JPAQuery<NotifyResponse.AroundTitle> getFrom() {
        return select(
                new QNotifyResponse_AroundTitle(
                        N.id, N.sequence, N.title
                ))
                .from(N);
    }

}
