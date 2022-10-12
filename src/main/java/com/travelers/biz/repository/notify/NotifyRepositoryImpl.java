package com.travelers.biz.repository.notify;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.travelers.biz.domain.notify.NotifyType;
import com.travelers.biz.repository.notify.config.QuerydslSupports;
import com.travelers.dto.*;
import com.travelers.dto.paging.PagingCorrespondence;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.travelers.biz.domain.QMember.member;
import static com.travelers.biz.domain.notify.QNotify.notify;

public class NotifyRepositoryImpl extends QuerydslSupports implements NotifyRepositoryQuery {

    public NotifyRepositoryImpl(EntityManager em) {
        super(em);
    }

    private <T> JPAQuery<T> selectFromWhere(final NotifyType notifyType, ConstructorExpression<T> dtoResponse) {
        return select(dtoResponse)
                .from(notify)
                .where(notify.notifyType.eq(notifyType)
                );
    }

    private JPAQuery<Long> countQuery(final NotifyType type) {
        return select(notify.count()).from(notify)
                .where(notify.notifyType.eq(type));
    }

    @Override
    public PagingCorrespondence.Response<NotifyResponse.SimpleInfo> findSimpleList(final NotifyType notifyType, final Pageable pageable) {

        return PagingCorrespondence.Response.from(
                applyPagination(pageable,
                        () -> selectFromWhere(notifyType,
                                new QNotifyResponse_SimpleInfo(
                                        notify.id,
                                        notify.sequence,
                                        member.username,
                                        notify.title,
                                        notify.createdAt))
                                .join(notify.writer, member)
                                .offset(pageable.getOffset())
                                .limit(pageable.getPageSize())
                                .orderBy(notify.id.desc()),
                        countQuery(notifyType)
                )
        );
    }

    @Override
    public Optional<NotifyResponse.DetailInfo> findDetail(final Long notifyId, final NotifyType notifyType) {
        Optional<NotifyResponse.DetailInfo> detailInfo = Optional.ofNullable(
                selectFromWhere(
                        notifyType,
                        new QNotifyResponse_DetailInfo(
                                notify.id,
                                notify.sequence,
                                member.username,
                                notify.title,
                                notify.content,
                                notify.createdAt))
                        .join(notify.writer, member)
                        .where(notify.id.eq(notifyId))
                        .fetchFirst()
        );

        detailInfo.ifPresent(e -> e.addAroundTitles(getAround(notifyId, notifyType)));

        return detailInfo;
    }

    private List<NotifyResponse.AroundTitle> getAround(final Long notifyId, final NotifyType notifyType) {
        return getAround(getPrev(notifyId, notifyType), getNext(notifyId, notifyType));
    }

    private List<NotifyResponse.AroundTitle> getAround(final NotifyResponse.AroundTitle... aroundTitles) {
        List<NotifyResponse.AroundTitle> list = new ArrayList<>();
        for (NotifyResponse.AroundTitle aroundTitle : aroundTitles) {
            if (aroundTitle != null)
                list.add(aroundTitle);
        }
        return list;
    }


    private NotifyResponse.AroundTitle getNext(final Long notifyId, final NotifyType notifyType) {
        return getWhere(notify.id.min(), notify.id.gt(notifyId), notifyType)
                .fetchFirst();
    }

    private NotifyResponse.AroundTitle getPrev(final Long notifyId, final NotifyType notifyType) {
        return getWhere(notify.id.max(), notify.id.lt(notifyId), notifyType)
                .fetchFirst();
    }

    private JPAQuery<NotifyResponse.AroundTitle> getWhere(
            final NumberExpression<Long> target,
            final BooleanExpression condition,
            final NotifyType notifyType
    ) {
        return getFrom()
                .where(notify.notifyType.eq(notifyType),
                        notify.id.eq(JPAExpressions.select(target).from(notify).where(condition))
                );
    }

    private JPAQuery<NotifyResponse.AroundTitle> getFrom() {
        return select(
                new QNotifyResponse_AroundTitle(
                        notify.id, notify.sequence, notify.title
                ))
                .from(notify);
    }

}
