package com.travelers.dto.paging;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PagingRequest {

    public static class Info {
        private final int page;
        private final int size;
        private final Sort sort;

        public Info() {
            page = 1;
            size = 7;
            sort = Sort.by("id").descending();
        }

        public Pageable toPageable() {
            return PageRequest.of(page - 1, size, sort);
        }
    }
}
