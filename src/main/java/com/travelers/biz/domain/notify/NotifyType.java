package com.travelers.biz.domain.notify;

import com.travelers.biz.domain.Member;
import com.travelers.dto.BoardRequest;
import com.travelers.exception.SupplierWithThrowable;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;

@RequiredArgsConstructor
public enum NotifyType {
    NOTICE(Notice.class),
    REFERENCE_LIBRARY(RefLibrary.class)
    ,;

    private final Class<?> clazz;

    public Notify toNotify(
            final NotifyType notifyType,
            final Member member,
            final BoardRequest.Write write
    ){
        return createNotify(() -> {
            final Constructor<?> constructor = ReflectionUtils.accessibleConstructor(notifyType.clazz, Member.class, String.class, String.class);
            ReflectionUtils.makeAccessible(constructor);
            return (Notify) constructor.newInstance(member, write.getTitle(), write.getContent());
        });
    }

    public Notify createNotify(final SupplierWithThrowable<Notify> supplier) {
        try {
            return supplier.get();
        } catch (final ReflectiveOperationException e){
            return null;
        }
    }
}
