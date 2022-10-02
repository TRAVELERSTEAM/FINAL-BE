package com.travelers.biz.domain;

import com.travelers.biz.domain.base.BaseContent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends BaseContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_id")
    private Qna qna;

    public Reply(final Member writer, final String title, final String content, final Qna qna){
        super(writer, title, content);
        relationWith(qna);
    }

    private void relationWith(final Qna qna) {
        this.qna = qna;
        qna.addReply(this);
    }
}
