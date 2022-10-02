package com.travelers.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ReviewResponse {

    @Getter
    public static class SimpleInfo {
        private final Long id;
        private final String name;
        private final String content;
        private final String createdAt;

        @QueryProjection
        public SimpleInfo(
                final Long id,
                final String name,
                final String content,
                final String createdAt
        ) {
            this.id = id;
            this.name = name;
            this.content = content;
            this.createdAt = createdAt;
        }
    }

    @Getter
    public static class DetailInfo {
        private final Long reviewId;
        private final Long productId;
        private final String writer;
        private final String title;
        private final String content;
        private final String createdAt;
        private List<AroundTitle> aroundTitles = new ArrayList<>();

        @QueryProjection
        public DetailInfo(
                final Long reviewId,
                final Long productId,
                final String writer,
                final String title,
                final String content,
                final String createdAt
        ) {
            this.reviewId = reviewId;
            this.productId = productId;
            this.writer = writer;
            this.title = title;
            this.content = content;
            this.createdAt = createdAt;
        }

        public void addAroundTitles(final List<AroundTitle> aroundTitles){
            this.aroundTitles = aroundTitles;
        }
    }

    @Getter
    static public class AroundTitle {
        private final Long id;
        private final String title;

        @QueryProjection
        public AroundTitle(
                final Long id,
                final String title
        ) {
            this.id = id;
            this.title = title;
        }
    }
}
