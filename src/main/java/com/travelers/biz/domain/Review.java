package com.travelers.biz.domain;

import com.travelers.biz.domain.base.BaseContent;
import com.travelers.biz.domain.image.ReviewImage;
import com.travelers.biz.domain.notify.Notice;
import com.travelers.exception.ErrorCode;
import com.travelers.exception.TravelersException;
import io.lettuce.core.dynamic.annotation.CommandNaming;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseContent {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product placeToTravel;

    private Review(final Member writer, final Product placeToTravel, final String title, final String content){
        super(writer, title, content);
        this.placeToTravel = placeToTravel;
    }

    public static Review create(final Member writer, final Product placeToTravel, final String title, final String content){
        return new Review(writer, placeToTravel, title, content);
    }

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private final List<ReviewImage> images = new ArrayList<>();

    public void addImage(final ReviewImage reviewImage){
        images.add(reviewImage);
    }

}
