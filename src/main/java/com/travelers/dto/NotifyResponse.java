package com.travelers.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

public class NotifyResponse {

    @Getter
    @ToString
    public static class SimpleInfo {
        private final Long sequence;
        private final String writer;
        private final String title;
        private final String createdAt;

        @QueryProjection
        public SimpleInfo(
                final Long sequence,
                final String writer,
                final String title,
                final String createdAt) {
            this.sequence = sequence;
            this.writer = writer;
            this.title = title;
            this.createdAt = createdAt;
        }
    }
}
