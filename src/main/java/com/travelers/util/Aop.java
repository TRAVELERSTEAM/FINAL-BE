package com.travelers.util;

import com.travelers.biz.service.handler.S3Uploader;
import com.travelers.dto.BoardRequest;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Aspect
@Component
public class Aop {

    private final ExpiringMap<Long, List<String>> cashMap;

    public Aop(final S3Uploader s3Uploader) {
        cashMap = ExpiringMap.builder()
                .maxSize(1000)
                .expirationPolicy(ExpirationPolicy.CREATED)
                .expiration(60, TimeUnit.SECONDS)
                .build();

        cashMap.addExpirationListener((k, v) -> {
            s3Uploader.delete(extractFromFolder(v));
        });
    }

    public void temporaryStorage(final Long memberId, final List<String> s3Url) {
        cashMap.put(memberId, s3Url);
    }

    private List<String> extractFromFolder(final List<String> urls) {
        return urls.stream()
                .map(e -> {
                    final int startedFileNamePos = e.lastIndexOf("/");
                    final String untilFolder = e.substring(0, startedFileNamePos);
                    final int startedFolderNamePos = untilFolder.lastIndexOf("/");
                    return e.substring(startedFolderNamePos + 1);
                })
                .collect(Collectors.toList());
    }

    @Before(value = "execution(* com.travelers.biz.service.NotifyService.write(..))"
            + "|| execution(* com.travelers.biz.service.NotifyService.update(..))")
    public void beforeReviewSave(final JoinPoint joinPoint) {
        Long targetId = SecurityUtil.getCurrentMemberId();

        if (cashMap.containsKey(targetId)) {
            transferTo(joinPoint, targetId);
            cashMap.remove(targetId);
        }
    }

    private void transferTo(JoinPoint joinPoint, Long targetId) {
        List.of(joinPoint.getArgs())
                .forEach(e -> {
                    if (BoardRequest.Write.class.isAssignableFrom(e.getClass())) {
                        ((BoardRequest.Write) e).getUrls().addAll(cashMap.get(targetId));
                    }
                });
    }

}