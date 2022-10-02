package com.travelers.biz.domain.image;

import com.travelers.biz.domain.Review;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@DiscriminatorValue("review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewImage extends Image{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    public ReviewImage(final String url, final Review review) {
        super(url);
        this.review = review;
        review.addImage(this);
    }
}
