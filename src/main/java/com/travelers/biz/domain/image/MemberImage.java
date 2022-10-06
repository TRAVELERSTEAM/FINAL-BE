package com.travelers.biz.domain.image;

import com.travelers.biz.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@DiscriminatorValue("member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberImage extends Image {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public MemberImage(final String url, final Member member) {
        super(url);
        this.member = member;
        member.addImage(this);
    }

}
