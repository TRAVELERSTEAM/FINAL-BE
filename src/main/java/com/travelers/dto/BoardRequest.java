package com.travelers.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BoardRequest {

    @Getter
    public static class Write implements Changeable {
        private final String title;
        private final String content;
        private List<String> urls;

        public Write() {
            this.title = null;
            this.content = null;
            this.urls = new ArrayList<>();
        }

        public List<String> getUrls() {
            return Collections.unmodifiableList(urls);
        }

        @Override
        public void changeUrls(final List<String> urls) {
            this.urls = urls;
        }
    }

    @NoArgsConstructor(force = true)
    public static class Reply{
        private final String content;

        public String getContent() {
            return content;
        }
    }
}
