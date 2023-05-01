package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.request.ReqCommentDTO;
import io.dkargo.bulletinboard.dto.request.ReqPostDTO;
import io.dkargo.bulletinboard.entity.Member;
import io.dkargo.bulletinboard.entity.Post;
import io.dkargo.bulletinboard.service.CommentService;
import io.dkargo.bulletinboard.service.MemberService;
import io.dkargo.bulletinboard.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;


import javax.transaction.Transactional;


@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Transactional
public class CommentController {
    private final CommentService commentService;

    @ApiOperation(value = "댓글 등록")
    @PostMapping(value = "")
    public void addComment(@RequestBody ReqCommentDTO reqCommentDTO) {
        System.out.println("reqCommentDTO.getContent() = " + reqCommentDTO.getContent());
        commentService.addComment(reqCommentDTO);
    }
}
