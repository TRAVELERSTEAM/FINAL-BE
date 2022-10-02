package com.travelers.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

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
                final String createdAt) {
            this.id = id;
            this.name = name;
            this.content = content;
            this.createdAt = createdAt;
        }
    }
}
