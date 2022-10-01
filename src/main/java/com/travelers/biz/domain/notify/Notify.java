package com.travelers.biz.domain.notify;

import com.travelers.biz.domain.base.BaseContent;
import com.travelers.biz.domain.image.Image;
import com.travelers.biz.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DiscriminatorColumn(name = "d_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Notify extends BaseContent {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notify_id")
    private Long id;

    protected Long sequence;

    @OneToMany(mappedBy = "notify", cascade = CascadeType.ALL)
    private final List<Image> images = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private NotifyType notifyType;

    protected Notify(final NotifyType notifyType, final Member writer, final String title, final String content) {
        super(writer, title, content);
        this.notifyType = notifyType;
    }

    abstract public void addImage(final Image image);
}
