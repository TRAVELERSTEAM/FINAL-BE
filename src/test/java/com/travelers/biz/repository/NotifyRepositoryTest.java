package com.travelers.biz.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travelers.biz.domain.Member;
import com.travelers.biz.domain.notify.*;
import com.travelers.config.DBSliceTest;
import com.travelers.dto.NotifyResponse;
import com.travelers.dto.PagingResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static com.travelers.biz.domain.notify.QNotify.notify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;

@DBSliceTest
class NotifyRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private NotifyRepository notifyRepository;
    private Member member;
    @Autowired
    private JPAQueryFactory qf;

    @BeforeEach
    void initFixture(){
        member = memberRepository.save(Member.builder().build());
    }

    @Test
    @DisplayName("공지사항 생성")
    void create_notice() {
        final Notice notice = Notice.create(member, "title", "content");
        final Notify savedNotify = notifyRepository.save(notice);

        then(savedNotify.getCreatedAt()).isNotNull();
        then(savedNotify.getModifiedAt()).isNotNull();
        then(savedNotify.getId()).isNotNull();
        then(savedNotify.getSequence()).isNotNull();
        then(savedNotify.getTitle()).isEqualTo("title");
        then(savedNotify.getContent()).isEqualTo("content");
        then(savedNotify.getWriter()).isNotNull();
        then(savedNotify.getNotifyType()).isSameAs(NotifyType.NOTICE);

        final Notify notified = qf.selectFrom(notify)
                .where(notify.notifyType.eq(NotifyType.NOTICE),
                        notify.id.eq(savedNotify.getId()))
                .fetchOne();

        then(notified).isNotNull();
    }

    @Test
    @DisplayName("공지사항 목록 출력")
    void select_notice_list() {
        PageRequest pagingCondition = PageRequest.of(0, 7, Sort.by("createdAt").descending());

        PagingResponse<NotifyResponse.SimpleInfo> noticeList = notifyRepository.findSimpleList(NotifyType.NOTICE, pagingCondition);
        List<NotifyResponse.SimpleInfo> contents = noticeList.getContents();

        then(contents.size()).isSameAs(7);
        then(noticeList.getCurrentPage()).isSameAs(1);
        then(noticeList.isShowPrev()).isFalse();
        then(noticeList.isShowNext()).isFalse();
        then(noticeList.getStartPage()).isSameAs(1);
        then(noticeList.getEndPage()).isSameAs(2);
        then(contents).extracting("title")
                .containsExactly(
                        "취업하고 싶다",
                        "러시아 여행 출국 금지 관련 안내",
                        "찾아오시는 길",
                        "여행 예약 안내",
                        "입금 계좌 안내",
                        "백신 및 안내 규정",
                        "배고프다");
    }
}