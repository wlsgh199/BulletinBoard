package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.annotation.CurrentMember;
import io.dkargo.bulletinboard.dto.request.reply.ReqCreateReplyDTO;
import io.dkargo.bulletinboard.dto.request.reply.ReqUpdateReplyDTO;
import io.dkargo.bulletinboard.dto.response.reply.ResCreateReplyDTO;
import io.dkargo.bulletinboard.dto.response.reply.ResUpdateReplyDTO;
import io.dkargo.bulletinboard.entity.Member;
import io.dkargo.bulletinboard.jwt.MemberDetailsDTO;
import io.dkargo.bulletinboard.service.ReplyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
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
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResCreateReplyDTO createReply(@PathVariable long commentId,
                                         @RequestBody @Valid ReqCreateReplyDTO reqCreateReplyDTO,
                                         @CurrentMember MemberDetailsDTO memberDetailsDTO) {
        return replyService.createReply(commentId, reqCreateReplyDTO, memberDetailsDTO.getId());
    }

    @Operation(summary = "답글 내용 수정")
    @PatchMapping("/{replyId}")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResUpdateReplyDTO updateReply(@PathVariable long replyId,
                                         @RequestBody @Valid ReqUpdateReplyDTO reqUpdateReplyDTO,
                                         @CurrentMember MemberDetailsDTO memberDetailsDTO) {
        return replyService.updateReply(replyId, reqUpdateReplyDTO, memberDetailsDTO.getId());
    }

    @Operation(summary = "답글 삭제")
    @DeleteMapping("/{replyId}")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public void deleteReply(@PathVariable long replyId,
                            @CurrentMember MemberDetailsDTO memberDetailsDTO) {
        replyService.deleteReply(replyId, memberDetailsDTO.getId());
    }

}
