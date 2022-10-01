package com.travelers.biz.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travelers.biz.domain.Member;
import com.travelers.biz.domain.notify.*;
import com.travelers.config.DBSliceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import static com.travelers.biz.domain.notify.QNotice.notice;
import static com.travelers.biz.domain.notify.QNotify.notify;
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
                .where(notify.notifyType.eq(NotifyType.NOTICE))
                .fetchOne();

        then(notified).isNotNull();
    }
}