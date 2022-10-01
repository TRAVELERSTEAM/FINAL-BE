package com.travelers.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PagingResponse<T> {

    private final List<T> contents;
    private final int currentPage;
    private final int naviSize; // 페이지 내비게이션의 크기
    private final int startPage;
    private final int endPage;
    private final boolean showNext;
    private final boolean showPrev;

    public static <T> PagingResponse<T> from(Page<T> page) {
        final int currentPage = page.getNumber() + 1;
        final int naviSize = 10;
        final int beginPage = (currentPage - 1) / naviSize * naviSize + 1;
        int totalElements = (int) page.getTotalElements();
        int totalPage = totalElements / page.getSize() + (totalElements % page.getSize() == 0 ? 0 : 1);
        final int endPage = Math.min(beginPage + naviSize - 1, totalPage);
        final boolean showPrev = beginPage != 1;
        final boolean showNext = endPage != totalElements;

        return new PagingResponse<>(
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
