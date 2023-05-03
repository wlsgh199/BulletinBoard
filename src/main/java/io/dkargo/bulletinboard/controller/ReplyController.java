package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.request.reply.ReqAddReplyDTO;
import io.dkargo.bulletinboard.dto.request.reply.ReqDeleteReplyDTO;
import io.dkargo.bulletinboard.dto.request.reply.ReqPutReplyDTO;
import io.dkargo.bulletinboard.service.ReplyService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;


import javax.transaction.Transactional;
import javax.validation.Valid;


@RestController
@RequestMapping("/posts/replies")
@RequiredArgsConstructor
@Transactional
public class ReplyController {
    private final ReplyService replyService;

    @ApiOperation(value = "게시물 댓글의 답글 등록")
    @PostMapping(value = "")
    public void addReply(@RequestBody @Valid ReqAddReplyDTO reqAddReplyDTO) {
        replyService.addReply(reqAddReplyDTO);
    }

    @ApiOperation(value = "답글 수정")
    @PatchMapping
    public void putReply(@RequestBody @Valid ReqPutReplyDTO reqPutReplyDTO) {
        replyService.putReply(reqPutReplyDTO);
    }

    @ApiOperation(value = "답글 삭제")
    @DeleteMapping
    public void deleteReply(@RequestBody @Valid ReqDeleteReplyDTO reqDeleteReplyDTO) {
        replyService.deleteReply(reqDeleteReplyDTO);
    }

}
