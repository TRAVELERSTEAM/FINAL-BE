package com.travelers.biz.repository;

import com.travelers.biz.domain.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query(" select i from NotifyImage i where i.notify.id = :notifyId")
    List<Image> findAllByNotifyId(@Param("notifyId") final Long notifyId);
}
