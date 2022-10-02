package com.travelers.biz.service.handler;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AmazonS3Config {

    private final String region;
    private final String accessKey;
    private final String secretKey;

    public AmazonS3Config(
            @Value("${cloud.aws.region.static}") final String region,
            @Value("${cloud.aws.credentials.access-key}") final String accessKey,
            @Value("${cloud.aws.credentials.secret-key}") final String secretKey){
        this.region = region;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    //접근 권한
    @Bean
    @Primary
    public BasicAWSCredentials awsCredentialsProvider() {
        return new BasicAWSCredentials(accessKey, secretKey);
    }

    //AmazonS3Client -> deprecated
    //ClientBuilder로 AmazonS3 사용하는 것을 권장
    @Bean
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentialsProvider()))
                .build();
    }


}
