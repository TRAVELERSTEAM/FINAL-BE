package com.travelers.biz.domain.notify;

import com.travelers.biz.domain.Member;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("notice")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends Notify{

    private Notice(final Member writer, final String title, final String content) {
        super(NotifyType.NOTICE, writer, title, content);
    }

    public static Notice create(final Member writer, final String title, final String content){
        return new Notice(writer, title, content);
    }

    @Override
    protected void init() {
        super.sequence = ManualIncrement.NOTICE.incrementAndGet();
    }
}
