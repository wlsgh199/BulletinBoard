package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.request.ReqReplyDTO;
import io.dkargo.bulletinboard.service.ReplyService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;


import javax.transaction.Transactional;


@RestController
@RequestMapping("/replies")
@RequiredArgsConstructor
@Transactional
public class ReplyController {
    private final ReplyService replyService;

    @ApiOperation(value = "답글 등록")
    @PostMapping(value = "")
    public void addComment(@RequestBody ReqReplyDTO reqReplyDTO) {
        System.out.println("reqReplyDTO.getContent() = " + reqReplyDTO.getContent());
        replyService.addReply(reqReplyDTO);
    }
}
