package com.travelers.biz.domain.notify;

import com.travelers.biz.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@DiscriminatorValue("ref_library")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefLibrary extends Notify{

    private RefLibrary(final Member writer, final String title, final String content) {
        super(NotifyType.REFERENCE_LIBRARY, writer, title, content);
    }

    public static RefLibrary create(final Member writer, final String title, final String content){
        return new RefLibrary(writer, title, content);
    }

    @Override
    protected void init() {
        super.sequence = ManualIncrement.REF_LIBRARY.incrementAndGet();
    }

}
