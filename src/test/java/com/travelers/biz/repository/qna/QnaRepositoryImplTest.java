package com.travelers.biz.repository.qna;

import com.travelers.biz.domain.*;
import com.travelers.biz.repository.MemberRepository;
import com.travelers.biz.repository.ReplyRepository;
import com.travelers.config.DBSliceTest;
import com.travelers.dto.QnaResponse;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DBSliceTest
class QnaRepositoryImplTest {

    @Autowired
    private QnaRepository qnaRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private EntityManagerFactory emf;
    @Autowired
    private EntityManager em;
    private Qna qna;

    private Member member;
    private Member mockMember;
    @BeforeEach
    void initFixture() {
        member = memberRepository.save(Member.builder()
                .username("정성훈")
                .gender(Gender.MALE)
                .build());
        mockMember = Mockito.mock(Member.class);
        BDDMockito.given(mockMember.getAuthority()).willReturn(Authority.ROLE_ADMIN);
        qna = Qna.create(member, "test", "test");
        new Reply(mockMember, "관리자", qna);
        qnaRepository.save(qna);
    }

    @Test
    @DisplayName("Qna 생성 테스트")
    void aVoid() {
        Qna qna = qnaRepository.save(Qna.create(member, "테스트 중입니다.", "제곧내"));

        assertThat(qna.getStatus()).isSameAs(Qna.Status.UN_RESPONSE);
        assertThat(qna.getReplyCnt()).isSameAs(0);
        assertThat(qna.getReplyList().size()).isSameAs(0);

        Reply reply = new Reply(mockMember, "안녕하세요 관리자닙니다.", qna);
        PersistenceUnitUtil persistenceUtil = emf.getPersistenceUnitUtil();

        then(qna.getStatus()).isSameAs(Qna.Status.RESPONSE);
        then(qna.getReplyCnt()).isSameAs(1);
        then(qna.getReplyList().size()).isSameAs(1);
        then(qna.getReplyList().get(0)).isSameAs(reply);

        qna.getReplyList().forEach(e ->
                then(persistenceUtil.isLoaded(e)).isTrue());
    }
}