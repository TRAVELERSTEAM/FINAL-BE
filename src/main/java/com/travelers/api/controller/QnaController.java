package com.travelers.api.controller;

import com.travelers.biz.service.QnaService;
import com.travelers.dto.BoardRequest;
import com.travelers.dto.QnaResponse;
import com.travelers.dto.paging.PagingCorrespondence;
import com.travelers.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QnaController {

    private final QnaService qnaService;

    private Long currentMemberId() {
        return getCurrentMemberId();
    }

    @GetMapping
    public ResponseEntity<PagingCorrespondence.Response<QnaResponse.SimpleInfo>> showAll(
            final PagingCorrespondence.Request request
    ) {
        return ResponseEntity.ok(qnaService.findSimpleList(currentMemberId(), request));
    }

    @GetMapping("/{qnaId}")
    public ResponseEntity<QnaResponse.DetailInfo> showOne(
            @PathVariable final Long qnaId
    ) {
        return ResponseEntity.ok(qnaService.findDetail(qnaId));
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @RequestBody BoardRequest.Write write
    ) {
        qnaService.write(currentMemberId(), write);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{qnaId}")
    public ResponseEntity<Void> update(
            @PathVariable final Long qnaId,
            @RequestBody final BoardRequest.Write write
    ) {
        qnaService.update(currentMemberId(), qnaId, write);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{qnaId}")
    public ResponseEntity<Void> updated(
            @PathVariable final Long qnaId
    ) {
        qnaService.delete(currentMemberId(), qnaId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{qnaId}/reply")
    public ResponseEntity<Void> createReply(
            @PathVariable final Long qnaId,
            @RequestBody final BoardRequest.Reply write
    ) {
        qnaService.addReply(currentMemberId(), qnaId, write);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/reply/{replyId}")
    public ResponseEntity<Void> updateReply(
            @PathVariable final Long replyId,
            @RequestBody final BoardRequest.Reply write
    ) {
        qnaService.updateReply(replyId, write);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/reply/{replyId}")
    public ResponseEntity<Void> deleteReply(
            @PathVariable final Long replyId
    ) {
        qnaService.removeReply(replyId);
        return ResponseEntity.noContent().build();
    }

    private static Long getCurrentMemberId() {
        return SecurityUtil.getCurrentMemberId();
    }

}
