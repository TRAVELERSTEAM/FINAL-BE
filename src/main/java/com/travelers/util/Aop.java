package com.travelers.util;

import com.travelers.admin.NoticeRequest;
import com.travelers.biz.service.handler.S3Uploader;
import com.travelers.dto.ReviewRequest;
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

    private final ExpiringMap<Long, List<String>> cashing;

    public Aop(final S3Uploader s3Uploader) {
        cashing = ExpiringMap.builder()
                .maxSize(1000)
                .expirationPolicy(ExpirationPolicy.CREATED)
                .expiration(60, TimeUnit.SECONDS)
                .build();

        cashing.addExpirationListener((k, v) -> {
            s3Uploader.delete(extractFromFolder(v));
        });
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

    private void cashing(Long targetId, List<String> target) {
        if (cashing.containsKey(targetId)){
            final List<String> from = cashing.get(targetId);
            final List<String> to = target;

            to.addAll(from);
            cashing.remove(targetId);
        }
    }

    private Long getTargetId(JoinPoint joinPoint, int index) {
        return (Long) getTarget(joinPoint, index);
    }

    private static Object getTarget(JoinPoint joinPoint, int index) {
        return joinPoint.getArgs()[index];
    }

    @Before("execution(public void com.travelers.biz.service.ReviewService.write(..))")
    public void beforeReviewSave(final JoinPoint joinPoint) {
        final Long targetId = getTargetId(joinPoint, 0);
        final ReviewRequest.Create target = (ReviewRequest.Create) getTarget(joinPoint, 2);

        cashing(targetId, target.getUrls());
    }

    @Before("execution(public void com.travelers.biz.service.ReviewService.update(..))")
    public void beforeReviewUpdate(final JoinPoint joinPoint) {
        final Long targetId = getTargetId(joinPoint, 1);
        final ReviewRequest.Update target = (ReviewRequest.Update) getTarget(joinPoint, 2);

        cashing(targetId, target.getUrls());
    }

    @Before("execution(public void com.travelers.admin.service.AdminNoticeService.write(..))")
    public void beforeNoticeSave(final JoinPoint joinPoint){
        final Long targetId = getTargetId(joinPoint, 0);
        final NoticeRequest.Create target = (NoticeRequest.Create) getTarget(joinPoint, 1);

        cashing(targetId, target.getUrls());
    }

    @Before("execution(public void com.travelers.admin.service.AdminNoticeService.update(..))")
    public void beforeNoticeUpdate(final JoinPoint joinPoint){
        final Long targetId = getTargetId(joinPoint, 0);
        final NoticeRequest.Update target = (NoticeRequest.Update) getTarget(joinPoint, 2);

        cashing(targetId, target.getUrls());
    }

    public void temporaryStorage(final Long memberId, final List<String> s3Url) {
        cashing.put(memberId, s3Url);
    }
}
