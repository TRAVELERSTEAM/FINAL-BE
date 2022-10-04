package com.travelers.dto.paging;

import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static java.util.Objects.requireNonNullElse;

public class PagingCorrespondence {

    public static class Request {
        private final Integer page;
        private final Integer size;
        private final Sort sort;

        public Request(final Integer page, final Integer size) {
            this.page = requireNonNullElse(page, 1);
            this.size = requireNonNullElse(size, 7);
            sort = Sort.by("id").descending();
        }

        public Pageable toPageable() {
            return PageRequest.of(page - 1, size, sort);
        }
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Response<T> {

        private final List<T> contents;
        private final int currentPage;
        private final int naviSize;
        private final int startPage;
        private final int endPage;
        private final boolean showPrev;
        private final boolean showNext;

        public static <T> Response<T> from(Page<T> page) {
            final int currentPage = page.getNumber() + 1;
            final int naviSize = 10;
            final int beginPage = (currentPage - 1) / naviSize * naviSize + 1;
            final int totalElements = (int) page.getTotalElements();
            final int totalPage = totalElements / page.getSize() + (totalElements % page.getSize() == 0 ? 0 : 1);
            final int endPage = Math.min(beginPage + naviSize - 1, totalPage);
            final boolean showPrev = beginPage != 1;
            final boolean showNext = endPage != totalPage;

            return new Response<>(
                    page.getContent(),
                    currentPage,
                    naviSize,
                    (currentPage - 1) / naviSize * naviSize + 1,
                    endPage,
                    showPrev,
                    showNext
            );
        }
    }
}
