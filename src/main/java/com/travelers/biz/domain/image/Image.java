package com.travelers.biz.domain.image;

import com.travelers.biz.domain.notify.Notify;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@DiscriminatorColumn(name = "image_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Image {
    private static final String DELIMITER = "/";

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
        int indexOfImageName = url.lastIndexOf(DELIMITER);
        String urlWithoutImageName = url.substring(0, indexOfImageName);
        int indexOfDirectoryName = urlWithoutImageName.lastIndexOf(DELIMITER);

        return url.substring(indexOfDirectoryName + 1);
    }


}
