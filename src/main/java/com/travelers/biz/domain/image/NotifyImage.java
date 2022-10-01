package com.travelers.biz.domain.image;

import com.travelers.biz.domain.notify.Notify;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@DiscriminatorValue("notify")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotifyImage extends Image{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notify_id", nullable = false)
    private Notify notify;

    public NotifyImage(final String url, final Notify notify) {
        super(url);
        this.notify = notify;
        notify.addImage(this);
    }
}
