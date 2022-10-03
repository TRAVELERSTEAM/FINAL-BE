package com.travelers.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.travelers.biz.domain.Qna;
import com.travelers.biz.domain.Reply;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QnaResponse {

    @Getter
    public static class SimpleInfo {
        private final Long id;
        private final Qna.Status status;
        private final String title;
        private final String createdAt;

        @QueryProjection
        public SimpleInfo(
                final Long id,
                final Qna.Status status,
                final String title,
                final String createdAt) {
            this.id = id;
            this.status = status;
            this.title = title;
            this.createdAt = createdAt;
        }
    }

    @Getter
    public static class DetailInfo {
        private final Long id;
        private final Qna.Status status;
        private final String title;
        private final String content;
        private final String createdAt;
        private List<ReplyResponse> replyList = new ArrayList<>();

        @QueryProjection
        public DetailInfo(
                final Long id,
                final Qna.Status status,
                final String title,
                final String content,
                final String createdAt
        ) {
            this.id = id;
            this.status = status;
            this.title = title;
            this.content = content;
            this.createdAt = createdAt;
        }

        public void addReplyList(final List<ReplyResponse> replyList){
            this.replyList = replyList;
        }
    }

    @Getter
    public static class ReplyResponse {
        private final Long id;
        private final String writer;
        private final String content;
        private final String createdAt;

        @QueryProjection
        public ReplyResponse(
                final Long id,
                final String writer,
                final String content,
                final String createdAt
        ) {
            this.id = id;
            this.writer = writer;
            this.content = content;
            this.createdAt = createdAt;
        }
    }
}
