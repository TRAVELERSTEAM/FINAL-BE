package com.travelers.biz.domain.product;

import com.travelers.biz.domain.Banner;
import com.travelers.biz.domain.base.BaseTime;
import com.travelers.biz.domain.image.ProductImage;
import com.travelers.biz.domain.product.embeddable.Price;
import com.travelers.config.converter.PeriodConverter;
import lombok.*;

import javax.persistence.*;
import java.time.Period;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product")
public class Product extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "target")
    private String target;

    @Column(name = "area")
    private String area;

    @Column(name = "theme")
    private String theme;

    @Lob
    @Column(name = "start_date")
    private String startDate;

    @Column(name = "summary")
    private String summary;

    @Convert(converter = PeriodConverter.class)
    @Column(name = "period")
    private Period period;

    @Embedded
    @Column(name = "price")
    private Price price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> images = new LinkedList<>();

    @OneToOne(mappedBy = "product")
    private Banner banner;

    @Builder
    public Product(String title, String target, String area, String theme, String startDate, String summary, Period period, Price price) {
        this.title = title;
        this.target = target;
        this.area = area;
        this.theme = theme;
        this.startDate = startDate;
        this.summary = summary;
        this.period = period;
        this.price = price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPeriod() {
        return period.getDays();
    }

    public void addImage(final ProductImage image) {
        images.add(image);
    }

}
