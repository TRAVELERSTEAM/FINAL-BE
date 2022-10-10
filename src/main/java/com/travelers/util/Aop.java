package com.travelers.util;

import com.travelers.biz.service.handler.S3Uploader;
import com.travelers.dto.Changeable;
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
                .maxSize(15)
                .expirationPolicy(ExpirationPolicy.CREATED)
                .expiration(60, TimeUnit.SECONDS)
                .build();

        cashMap.addExpirationListener(
                (k, v) -> s3Uploader.delete(extractFromFolder(v))
        );
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
            + "|| execution(* com.travelers.biz.service.NotifyService.update(..))"
            + "|| execution(* com.travelers.biz.service.ReviewService.write(..))"
            + "|| execution(* com.travelers.biz.service.ReviewService.update(..))"
            + "|| execution(* com.travelers.biz.service.QnaService.write(..))"
            + "|| execution(* com.travelers.biz.service.QnaService.update(..))")
    public void beforeSave(final JoinPoint joinPoint) {
        final Long memberId = SecurityUtil.getCurrentMemberId();

        if (cashMap.containsKey(memberId)) {
            transferTo(joinPoint, memberId);
            cashMap.remove(memberId);
        }
    }

    private void transferTo(final JoinPoint joinPoint, final Long targetId) {
        for (final Object obj : joinPoint.getArgs()) {
            if(obj instanceof Changeable)
                ((Changeable)obj).changeUrls(cashMap.get(targetId));
        }
    }

}
