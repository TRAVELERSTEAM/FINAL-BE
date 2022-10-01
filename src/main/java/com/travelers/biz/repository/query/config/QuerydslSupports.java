package com.travelers.biz.repository.query.config;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public abstract class QuerydslSupports {

    private final JPAQueryFactory jpaQueryFactory;
    private final Querydsl querydsl;

    protected QuerydslSupports(final EntityPath<?> entityPath, final EntityManager em) {
        jpaQueryFactory = new JPAQueryFactory(em);
        querydsl = new Querydsl(em, new PathBuilder<>(entityPath.getType(), entityPath.getMetadata()));
    }

    protected JPAQueryFactory getJpaQueryFactory() {
        return jpaQueryFactory;
    }

    protected Querydsl getQuerydsl() {
        return querydsl;
    }

    protected <T> JPAQuery<T> select(final Expression<T> expr) {
        return getJpaQueryFactory().select(expr);
    }

    protected <T> JPAQuery<T> selectFrom(final EntityPath<T> from) {
        return getJpaQueryFactory().selectFrom(from);
    }

    protected <T> JPAQuery<Integer> selectOne() {
        return getJpaQueryFactory().selectOne();
    }

    protected <T> Optional<T> findWhereCondition(final JPAQuery<T> query, final Predicate... o) {
        final BooleanBuilder booleanBuilder = iterationPredicate(o);

        return Optional.ofNullable(
                query.where(booleanBuilder)
                        .fetchFirst()
        );
    }

    private BooleanBuilder iterationPredicate(final Predicate... o) {
        final BooleanBuilder booleanBuilder = new BooleanBuilder();

        for (Predicate predicate : o) {
            booleanBuilder.and(predicate);
        }

        return booleanBuilder;
    }

    protected BooleanBuilder equals(final Supplier<BooleanExpression> supplier) {
        return safeNull(supplier);
    }

    private BooleanBuilder safeNull(final Supplier<BooleanExpression> s) {
        try {
            return new BooleanBuilder(s.get());
        } catch (IllegalArgumentException e) {
            return new BooleanBuilder();
        }
    }

//    public <T> OrderSpecifier[] orderSpecifiers(final EntityPathBase<T> qClass, final Pageable pageable) {
//        return pageable.getSort()
//                .stream()
//                .map(sort -> toOrderSpecifier(qClass, sort))
//                .collect(Collectors.toList()).toArray(OrderSpecifier[]::new);
//    }
//
//    private <T> OrderSpecifier toOrderSpecifier(final EntityPathBase<T> qClass, final Sort.Order sortOrder) {
//        final Order orderMethod = toOrder(sortOrder);
//        final PathBuilder<T> pathBuilder = new PathBuilder<>(qClass.getType(), qClass.getMetadata());
//        return new OrderSpecifier(orderMethod, pathBuilder.get(sortOrder.getProperty()));
//    }
//
//    private Order toOrder(final Sort.Order sortOrder) {
//        if (sortOrder.isAscending()) {
//            return Order.ASC;
//        }
//        return Order.DESC;
//    }

    protected <T> Page<T> applyPagination(final Pageable pageable, final Supplier<JPAQuery<T>> jpaQuery, final Supplier<JPAQuery<Long>> countQuery) {
        final JPAQuery<T> query = jpaQuery.get();

        final List<T> fetch = getQuerydsl().applyPagination(pageable, query).fetch();

        final JPAQuery<Long> longJPAQuery = countQuery.get();

        return PageableExecutionUtils.getPage(fetch, pageable, longJPAQuery::fetchOne);
    }

    protected <T> JPADeleteClause delete(EntityPath<T> entityPath){
        return getJpaQueryFactory().delete(entityPath);
    }

}
