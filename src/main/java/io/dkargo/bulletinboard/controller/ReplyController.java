package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.request.reply.ReqAddReplyDTO;
import io.dkargo.bulletinboard.dto.request.comment.ReqDeleteCommentDTO;
import io.dkargo.bulletinboard.dto.request.comment.ReqPatchCommentDTO;
import io.dkargo.bulletinboard.dto.request.reply.ReqDeleteReplyDTO;
import io.dkargo.bulletinboard.dto.request.reply.ReqPatchReplyDTO;
import io.dkargo.bulletinboard.service.ReplyService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;


import javax.transaction.Transactional;


@RestController
@RequestMapping("/posts/replies")
@RequiredArgsConstructor
@Transactional
public class ReplyController {
    private final ReplyService replyService;

    @ApiOperation(value = "게시물 댓글의 답글 등록")
    @PostMapping(value = "")
    public void addReply(@RequestBody ReqAddReplyDTO reqAddReplyDTO) {
        System.out.println("reqReplyDTO.getContent() = " + reqAddReplyDTO.getContent());
        replyService.addReply(reqAddReplyDTO);
    }

    @ApiOperation(value = "답글 수정")
    @PatchMapping
    public void patchReply(@RequestBody ReqPatchReplyDTO reqPatchReplyDTO) {
        replyService.patchReply(reqPatchReplyDTO);
    }

    @ApiOperation(value = "답글 삭제")
    @DeleteMapping
    public void deleteReply(@RequestBody ReqDeleteReplyDTO reqDeleteReplyDTO) {
        replyService.deleteReply(reqDeleteReplyDTO);
    }

}
