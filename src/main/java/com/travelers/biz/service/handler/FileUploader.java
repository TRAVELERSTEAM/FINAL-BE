package com.travelers.biz.service.handler;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploader {

    List<String> upload(final List<MultipartFile> multipartFiles);
    void delete(final List<String> lists);
}
