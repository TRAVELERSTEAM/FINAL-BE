package com.travelers.biz.domain.notify;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotifyType {
    NOTICE(Notice.class),
    REFERENCE_LIBRARY(RefLibrary.class)
    ,;

    private final Class<?> clazz;
}
