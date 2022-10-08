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
import com.travelers.exception.ErrorCode;
import com.travelers.exception.TravelersException;
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
        return notifyRepository.findDetail(id, notifyType)
                .orElseThrow(() -> new TravelersException(ErrorCode.RESOURCE_NOT_FOUND));
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
                .orElseThrow(() -> new TravelersException(ErrorCode.MEMBER_NOT_FOUND));
    }

    @Transactional
    public void update(
            final Long notifyId,
            final BoardRequest.Write write
    ) {
        final Notify notify = findById(notifyId);

        notify.edit(write.getTitle(), write.getContent());
        fileUploader.deleteImages(notifyId, imageRepository::findAllByNotifyId);
        addImages(notify, write);
    }

    @Transactional
    public void delete(
            final Long notifyId
    ) {
        fileUploader.deleteImages(notifyId, imageRepository::findAllByNotifyId);

        notifyRepository.delete(findById(notifyId));
    }

    private Notify findById(Long notifyId) {
        return notifyRepository.findById(notifyId)
                .orElseThrow(() -> new TravelersException(ErrorCode.RESOURCE_NOT_FOUND));
    }

}
