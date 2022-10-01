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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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
        final PageRequest pagingCondition = PageRequest.of(0, 7, Sort.by("createdAt").descending());

        final PagingResponse<NotifyResponse.SimpleInfo> noticeList = notifyRepository.findSimpleList(NotifyType.NOTICE, pagingCondition);
        final List<NotifyResponse.SimpleInfo> contents = noticeList.getContents();

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

    @ParameterizedTest
    @MethodSource("aroundTest")
    @DisplayName("공지사항 한 건 출력 시 이전과 다음 게시글이 있으면 제목과 번호를 가져와야 한다.")
    void select_one(final Long id, final String title, final String[] aroundTitle) {
        NotifyResponse.DetailInfo detail = notifyRepository.findDetail(id, NotifyType.NOTICE);

        then(detail.getId()).isSameAs(id);
        then(detail.getTitle()).isEqualTo(title);

        then(detail.getAroundTitles())
                .extracting("title", String.class)
                .containsExactly(aroundTitle);
    }

    private static Stream<Arguments> aroundTest(){
        return Stream.of(
                Arguments.of(1L, "여행 시 규칙", new String[]{"환불 규정"}),
                Arguments.of(2L, "환불 규정", new String[]{"여행 시 규칙", "안전 수칙"}),
                Arguments.of( 3L, "안전 수칙", new String[]{"환불 규정", "전화 상담에 대한 안내문"})
        );
    }
}