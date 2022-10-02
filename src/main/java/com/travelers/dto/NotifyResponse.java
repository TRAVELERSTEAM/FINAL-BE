package com.travelers.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.util.List;

public class NotifyResponse {

    @Getter
    public static class SimpleInfo {
        private final Long id;
        private final Long sequence;
        private final String writer;
        private final String title;
        private final String createdAt;

        @QueryProjection
        public SimpleInfo(
                final Long id,
                final Long sequence,
                final String writer,
                final String title,
                final String createdAt) {
            this.id = id;
            this.sequence = sequence;
            this.writer = writer;
            this.title = title;
            this.createdAt = createdAt;
        }
    }

    @Getter
    public static class DetailInfo {
        private final Long id;
        private final Long sequence;
        private final String writer;
        private final String title;
        private final String content;
        private final String createdAt;
        private List<AroundTitle> aroundTitles;

        @QueryProjection
        public DetailInfo(
                final Long id,
                final Long sequence,
                final String writer,
                final String title,
                final String content,
                final String createdAt
        ) {
            this.id = id;
            this.sequence = sequence;
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
        private final Long sequence;
        private final String title;

        @QueryProjection
        public AroundTitle(
                final Long id,
                final Long sequence,
                final String title
        ) {
            this.id = id;
            this.sequence = sequence;
            this.title = title;
        }
    }
}
