package com.travelers.biz.domain.image;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@DiscriminatorColumn(name = "image_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Image {

    private static final String PATH_DELIMITER = "/";

    @Column(name = "image_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url")
    private String url;
    @Column(name = "image_key")
    private String key;

    public Image(final String url){
        this.url = url;
        this.key = extractKey();
    }

    private String extractKey() {
        int indexOfImageName = url.lastIndexOf(PATH_DELIMITER);
        String urlWithoutImageName = url.substring(0, indexOfImageName);
        int indexOfDirectoryName = urlWithoutImageName.lastIndexOf(PATH_DELIMITER);

        return url.substring(indexOfDirectoryName + 1);
    }
}
