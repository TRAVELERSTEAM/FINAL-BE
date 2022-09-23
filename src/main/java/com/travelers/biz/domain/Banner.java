package com.travelers.biz.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author kei
 * @since 2022-09-23
 */
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String image;

    @Builder
    public Banner(Long id, String image) {
        this.id = id;
        this.image = image;
    }
}
