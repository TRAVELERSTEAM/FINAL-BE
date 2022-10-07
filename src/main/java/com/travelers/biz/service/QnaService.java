package com.travelers.biz.service;

import com.travelers.biz.domain.Member;
import com.travelers.biz.domain.Qna;
import com.travelers.biz.domain.Reply;
import com.travelers.biz.domain.image.Image;
import com.travelers.biz.domain.image.QnaImage;
import com.travelers.biz.repository.ImageRepository;
import com.travelers.biz.repository.MemberRepository;
import com.travelers.biz.repository.ReplyRepository;
import com.travelers.biz.repository.qna.QnaRepository;
import com.travelers.biz.service.handler.FileUploader;
import com.travelers.dto.BoardRequest;
import com.travelers.dto.QnaResponse;
import com.travelers.dto.paging.PagingCorrespondence;
import com.travelers.exception.OptionalHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.travelers.exception.OptionalHandler.*;

@Service
@RequiredArgsConstructor
public class QnaService {

    private final MemberRepository memberRepository;
    private final QnaRepository qnaRepository;
    private final ImageRepository imageRepository;
    private final FileUploader fileUploader;
    private final ReplyRepository replyRepository;

    @Transactional(readOnly = true)
    public PagingCorrespondence.Response<QnaResponse.SimpleInfo> findSimpleList(
            final Long memberId,
            final PagingCorrespondence.Request pagingInfo
    ) {
        return qnaRepository.findSimpleList(memberId, pagingInfo.toPageable());
    }

    @Transactional(readOnly = true)
    public QnaResponse.DetailInfo findDetail(
            final Long qnaId
    ) {
         return findOrResourceNotFound(qnaId, qnaRepository::findDetailInfo);
    }

    @Transactional
    public void write(
            final Long memberId,
            final BoardRequest.Write write
    ) {
        Member writer = findMemberOrThrow(memberId, memberRepository::findById);
        Qna qna = Qna.create(writer, write.getTitle(), write.getContent());

        addImages(qna, write);
        qnaRepository.save(qna);
    }

    private void addImages(
            final Qna qna,
            final BoardRequest.Write write) {
        write.getUrls()
                .forEach(url -> new QnaImage(url, qna));
    }

    @Transactional
    public void update(
            final Long memberId,
            final Long qnaId,
            final BoardRequest.Write write
    ) {
        final Qna qna = findOrResourceNotFound(qnaId, qnaRepository::findById);
        qna.isSameWriter(memberId);

        qna.edit(write.getTitle(), write.getContent());
        deleteImages(qnaId);
        addImages(qna, write);
    }

    @Transactional
    public void delete(
            final Long memberId,
            final Long qnaId
    ) {
        final Qna qna = findOrResourceNotFound(qnaId, qnaRepository::findById);

        qna.isSameWriter(memberId);

        deleteImages(qnaId);

        qnaRepository.delete(qna);
    }

    private void deleteImages(final Long qnaId) {
        final List<Image> images = imageRepository.findAllByQnaId(qnaId);

        final List<String> keyList = images.stream()
                .map(Image::getKey)
                .collect(Collectors.toList());

        fileUploader.delete(keyList);
        imageRepository.deleteAll(images);
    }

    @Transactional
    public void addReply(
            final Long memberId,
            final Long qnaId,
            final BoardRequest.Reply write
    ) {
        final Qna qna = findOrResourceNotFound(qnaId, qnaRepository::findById);
        final Member member = findMemberOrThrow(memberId, memberRepository::findById);
        new Reply(member, write.getContent(), qna);
    }

    @Transactional
    public void updateReply(
            final Long replyId,
            final BoardRequest.Reply write
    ) {
        findOrResourceNotFound(replyId, replyRepository::findById)
                .edit(write.getContent());
    }

    @Transactional
    public void removeReply(
            final Long replyId
    ) {
        final Reply reply = findOrResourceNotFound(replyId, replyRepository::findWithQna);
        reply.requestToDecrementCnt();
        replyRepository.delete(reply);
    }
}
