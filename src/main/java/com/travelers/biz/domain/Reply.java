package com.travelers.biz.domain;

import com.travelers.biz.domain.base.BaseContent;
import com.travelers.biz.domain.base.BaseTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_id")
    private Qna qna;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    private String content;

    public Reply(final Member writer, final String content, final Qna qna){
        this.writer = writer;
        this.content = content;
        relationWith(qna);
    }

    private void relationWith(final Qna qna) {
        this.qna = qna;
        qna.addReply(this);
    }

    public void edit(final String content) {
        this.content = content;
    }
}
