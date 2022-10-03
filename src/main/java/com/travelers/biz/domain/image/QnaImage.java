package com.travelers.biz.domain.image;

import com.travelers.biz.domain.Qna;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@DiscriminatorValue("qna")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QnaImage extends Image{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_id")
    private Qna qna;

    public QnaImage(final String url, final Qna qna) {
        super(url);
        this.qna = qna;
        qna.addImage(this);
    }

}
