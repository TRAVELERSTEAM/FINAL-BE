package com.travelers.biz.service;

import com.travelers.biz.domain.Member;
import com.travelers.biz.domain.image.Image;
import com.travelers.biz.domain.image.NotifyImage;
import com.travelers.biz.domain.notify.Notice;
import com.travelers.biz.domain.notify.Notify;
import com.travelers.biz.domain.notify.NotifyType;
import com.travelers.biz.domain.notify.RefLibrary;
import com.travelers.biz.repository.NotifyRepository;
import com.travelers.dto.BoardRequest;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class NotifyServiceTest {

    @Mock
    private NotifyRepository notifyRepository;
    @InjectMocks
    private NotifyService notifyService;

    @Test
    @DisplayName("reflection 을 이용한 공지 생성")
    void dynamic_reflection_create_entity() {
        final Member mockMember = Mockito.mock(Member.class);
        final BoardRequest.Write mockWrite = Mockito.mock(BoardRequest.Write.class);

        final String title = "title";
        final String content = "content";

        given(mockWrite.getTitle()).willReturn(title);
        given(mockWrite.getContent()).willReturn(content);
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

    @Test
    @DisplayName("공지 생성 성공")
    void notice_success() {
        //given

        //when

        //then
    }


}