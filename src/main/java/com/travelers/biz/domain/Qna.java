package com.travelers.biz.domain;

import com.travelers.biz.domain.base.BaseContent;
import com.travelers.biz.domain.image.QnaImage;
import com.travelers.biz.domain.image.ReviewImage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Qna extends BaseContent {

    @RequiredArgsConstructor
    public enum Status{
        UN_RESPONSE, RESPONSE,;

        public Status change(final Member member){
            return member.getAuthority() == Authority.ROLE_ADMIN ? RESPONSE : UN_RESPONSE;
        }
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    private int replyCnt;

    @OneToMany(mappedBy = "qna", cascade = CascadeType.ALL)
    private final List<QnaImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "qna", cascade = CascadeType.ALL)
    private final List<Reply> replyList = new ArrayList<>();

    private Qna(final Member writer, final String title, final String content) {
        super(writer, title, content);
        this.status = Status.UN_RESPONSE;
    }

    public static Qna create(final Member writer, final String title, final String content) {
        return new Qna(writer, title, content);
    }

    public void addImage(final QnaImage qnaImage){
        images.add(qnaImage);
    }

    public void addReply(final Reply reply){
        this.replyList.add(reply);
        incrementReplyCnt();
        canChangeStatus(reply);
    }

    private void canChangeStatus(final Reply reply) {
        if(status != Status.RESPONSE) {
            status = status.change(reply.getWriter());
        }
    }

    public void incrementReplyCnt() {
        replyCnt++;
    }

    public void decrementReplyCnt() {
        replyCnt--;
    }

}
