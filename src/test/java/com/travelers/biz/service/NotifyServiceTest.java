package com.travelers.biz.service;

import com.travelers.biz.domain.Member;
import com.travelers.biz.domain.image.NotifyImage;
import com.travelers.biz.domain.notify.Notice;
import com.travelers.biz.domain.notify.Notify;
import com.travelers.biz.domain.notify.NotifyType;
import com.travelers.biz.domain.notify.RefLibrary;
import com.travelers.biz.repository.ImageRepository;
import com.travelers.biz.repository.MemberRepository;
import com.travelers.biz.repository.notify.NotifyRepository;
import com.travelers.biz.service.handler.FileUploader;
import com.travelers.dto.BoardRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotifyServiceTest {

    private NotifyService notifyService;
    private NotifyRepository notifyRepository;
    private MemberRepository memberRepository;
    private ImageRepository imageRepository;
    private FileUploader fileUploader;
    private BoardRequest.Write mockWrite;
    private Member mockMember;
    final String title = "title";
    final String content = "content";


    @BeforeEach
    void initFixture() {
        notifyRepository = mock(NotifyRepository.class);
        memberRepository = mock(MemberRepository.class);
        imageRepository = mock(ImageRepository.class);
        fileUploader = mock(FileUploader.class);
        notifyService = new NotifyService(
                notifyRepository,
                memberRepository,
                imageRepository,
                fileUploader
        );

        mockWrite = Mockito.mock(BoardRequest.Write.class);
        given(mockWrite.getTitle()).willReturn(title);
        given(mockWrite.getContent()).willReturn(content);

        mockMember = Mockito.mock(Member.class);
    }

    @Test
    @DisplayName("reflection 을 이용한 공지 생성")
    void dynamic_reflection_create_entity() {

        given(mockWrite.getUrls()).willReturn(List.of(
                "http://localhost",
                "http://pll0123"
        ));

        final Notify notify = NotifyType.NOTICE.toNotify(NotifyType.NOTICE, mockMember, mockWrite);

        mockWrite.getUrls()
                .forEach(url -> new NotifyImage(url, notify));

        then(notify.getTitle()).isEqualTo(title);
        then(notify.getContent()).isEqualTo(content);
        then(notify.getImages().size()).isEqualTo(2);
        then(Notice.class.isAssignableFrom(notify.getClass())).isTrue();
        then(RefLibrary.class.isAssignableFrom(notify.getClass())).isFalse();
    }

}