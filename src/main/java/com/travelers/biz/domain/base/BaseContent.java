package com.travelers.biz.domain.base;

import com.travelers.biz.domain.Member;
import com.travelers.exception.ErrorCode;
import com.travelers.exception.TravelersException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseContent extends BaseTime {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member writer;

    protected BaseContent(final Member writer, final String title, final String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    public void edit(final String title, final String content){
        this.title = title;
        this.content = content;
    }

    public void isSameWriter(final Long memberId) {
        if(writer.getId().equals(memberId)) {
            return ;
        }
        throw new TravelersException(ErrorCode.NO_PERMISSIONS);
    }
}
