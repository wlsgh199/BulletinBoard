package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.request.ReqReplyDTO;
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
    public void addReply(@RequestBody ReqReplyDTO reqReplyDTO) {
        System.out.println("reqReplyDTO.getContent() = " + reqReplyDTO.getContent());
        replyService.addReply(reqReplyDTO);
    }
}
