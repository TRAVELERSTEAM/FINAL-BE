package com.travelers.biz.domain.image;

import com.travelers.biz.domain.product.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@DiscriminatorValue("product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductImage extends Image {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public ProductImage(final String url, final Product product) {
        super(url);
        this.product = product;
        product.addImage(this);
    }

}