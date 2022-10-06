package com.travelers.biz.service.handler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class S3Uploader implements FileUploader {

    private final AmazonS3 amazonS3;
    private final FileHandler fileHandler;
    private final String bucket;
    private final String s3Directory;

    public S3Uploader(final AmazonS3 amazonS3,
                      final FileHandler fileHandler,
                      @Value("${cloud.aws.s3.image.bucket}") final String bucket,
                      @Value("${cloud.aws.s3.image.directory}") final String directory) {
        this.amazonS3 = amazonS3;
        this.fileHandler = fileHandler;
        this.bucket = bucket;
        this.s3Directory = directory;
    }

    @Override
    public List<String> upload(final List<MultipartFile> multipartFiles) {
        return multipartFiles.stream()
                .map(fileHandler::convert)
                .map(this::upload)
                .collect(Collectors.toList());
    }

    private String upload(final File file) {
        final String fileName = getFileName(file);

        putS3(file, () -> amazonS3.putObject(new PutObjectRequest(bucket, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead)));

        return getS3Url(fileName);
    }

    public String upload(final File file, String fileName) {
        putS3(file, () -> amazonS3.putObject(new PutObjectRequest(bucket, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead)));

        return getS3Url(fileName);
    }

    private String getFileName(final File file) {
        return s3Directory + file.getName();
    }

    private void putS3(final File file, final Runnable runnable) {
        try {
            runnable.run();
        } catch (AmazonClientException e) {
            throw new IllegalArgumentException();
        } finally {
            fileHandler.remove(file);
        }
    }

    private String getS3Url(final String fileName) {
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    public void delete(final List<String> keys) {
        final List<KeyVersion> keyVersions = convertKeyVersionsFrom(keys);
        final DeleteObjectsRequest deleteObjectsRequest = getDeleteObjectsRequest(keyVersions);
        run(() -> amazonS3.deleteObjects(deleteObjectsRequest));
    }

    private DeleteObjectsRequest getDeleteObjectsRequest(final List<KeyVersion> keyVersions) {
        final DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucket);
        deleteObjectsRequest.setKeys(keyVersions);
        return deleteObjectsRequest;
    }

    private List<KeyVersion> convertKeyVersionsFrom(final List<String> keys) {
        return keys.stream()
                .map(KeyVersion::new)
                .collect(Collectors.toList());
    }

    void run(Runnable run) {
        try {
            run.run();
        } catch (AmazonS3Exception ignored) {

        }
    }
}