package com.travelers.biz.service;

import com.travelers.biz.service.handler.FileUploader;
import com.travelers.util.Aop;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final FileUploader fileUploader;
    private final Aop aop;

    public List<String> uploadImage(
            final Long currentMemberId,
            final List<MultipartFile> files
    ) {
        return getS3Url(currentMemberId, files);
    }

    private List<String> getS3Url(
            final Long currentMemberId,
            final List<MultipartFile> files
    ) {
        final List<String> s3Url = fileUploader.upload(files);
        aop.temporaryStorage(currentMemberId, s3Url);
        return s3Url;
    }
}
