package com.travelers.biz.service.handler;

import com.travelers.biz.domain.image.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.function.Function;

public interface FileUploader {

    List<String> upload(final List<MultipartFile> multipartFiles);
    void delete(final List<String> lists);
    void deleteImages(final Long id, final Function<Long, List<Image>> function);
}
