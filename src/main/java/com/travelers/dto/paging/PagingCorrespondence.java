package com.travelers.dto.paging;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class PagingCorrespondence {

    public static class Request {
        private final int page;
        private final int size;
        private final Sort sort;

        public Request() {
            page = 1;
            size = 7;
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
        private final int naviSize; // 페이지 내비게이션의 크기
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
