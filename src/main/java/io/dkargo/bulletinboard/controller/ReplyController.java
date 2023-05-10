package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.request.reply.ReqCreateReplyDTO;
import io.dkargo.bulletinboard.dto.request.reply.ReqUpdateReplyDTO;
import io.dkargo.bulletinboard.service.ReplyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;


@RestController
@RequestMapping("/posts/replies")
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;

    @Operation(summary = "게시물 댓글의 답글 등록")
    @PostMapping(value = "/{commentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createReply(@PathVariable long commentId,
                            @RequestBody @Valid ReqCreateReplyDTO reqCreateReplyDTO) {
        replyService.createReply(commentId, reqCreateReplyDTO);
    }

    @Operation(summary = "답글 내용 수정")
    @PatchMapping("/{replyId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateReply(@PathVariable long replyId,
                            @RequestBody @Valid ReqUpdateReplyDTO reqUpdateReplyDTO) {
        replyService.updateReply(replyId, reqUpdateReplyDTO);
    }

    @Operation(summary = "답글 삭제")
    @DeleteMapping("/{replyId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteReply(@PathVariable long replyId) {
        replyService.deleteReply(replyId);
    }

}
