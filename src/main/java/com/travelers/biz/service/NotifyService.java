package com.travelers.biz.service;

import com.travelers.biz.domain.Member;
import com.travelers.biz.domain.image.Image;
import com.travelers.biz.domain.image.NotifyImage;
import com.travelers.biz.domain.notify.Notify;
import com.travelers.biz.domain.notify.NotifyType;
import com.travelers.biz.repository.ImageRepository;
import com.travelers.biz.repository.MemberRepository;
import com.travelers.biz.repository.notify.NotifyRepository;
import com.travelers.biz.service.handler.FileUploader;
import com.travelers.dto.BoardRequest;
import com.travelers.dto.NotifyResponse;
import com.travelers.dto.paging.PagingCorrespondence;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotifyService {

    private final NotifyRepository notifyRepository;
    private final MemberRepository memberRepository;
    private final ImageRepository imageRepository;
    private final FileUploader fileUploader;

    @Transactional(readOnly = true)
    public PagingCorrespondence.Response<NotifyResponse.SimpleInfo> showNotifies(
            final NotifyType notifyType,
            final PagingCorrespondence.Request pagingInfo
    ) {
        return notifyRepository.findSimpleList(notifyType, pagingInfo.toPageable());
    }

    @Transactional(readOnly = true)
    public NotifyResponse.DetailInfo showOne(
            final Long id,
            final NotifyType notifyType
    ) {
        return notifyRepository.findDetail(id, notifyType);
    }

    @Transactional
    public void write(
            final Long memberId,
            final NotifyType notifyType,
            final BoardRequest.Write write
    ) {
        final Notify notify = notifyType.toNotify(notifyType, findMemberById(memberId), write);
        addImages(notify, write);
        notifyRepository.save(notify);
    }

    private void addImages(
            final Notify notify,
            final BoardRequest.Write write
    ) {
        write.getUrls()
                .forEach(url -> new NotifyImage(url, notify));
    }

    private Member findMemberById(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(RuntimeException::new);
    }

    @Transactional
    public void update(
            final Long notifyId,
            final BoardRequest.Write write
    ) {
        final Notify notify = notifyRepository.findById(notifyId)
                .orElseThrow(RuntimeException::new);

        notify.edit(write.getTitle(), write.getContent());
        deleteImages(notifyId);
        addImages(notify, write);
    }

    @Transactional
    public void delete(
            final Long notifyId
    ) {
        deleteImages(notifyId);
        notifyRepository.deleteById(notifyId);
    }

    private void deleteImages(final Long notifyId) {
        final List<Image> images = imageRepository.findAllByNotifyId(notifyId);

        final List<String> keyList = images.stream()
                .map(Image::getKey)
                .collect(Collectors.toList());

        fileUploader.delete(keyList);
        imageRepository.deleteAll(images);
    }
}
