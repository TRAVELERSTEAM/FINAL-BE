package com.travelers.biz.domain.notify;

import java.util.concurrent.atomic.AtomicLong;

public interface ManualIncrement {
    AtomicLong NOTICE_ID = new AtomicLong();
    AtomicLong REF_LIBRARY = new AtomicLong();
}
