package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.request.ReqCommentDTO;
import io.dkargo.bulletinboard.dto.response.ResCommentReplyDTO;
import io.dkargo.bulletinboard.dto.response.ResReplyDTO;
import io.dkargo.bulletinboard.entity.Comment;
import io.dkargo.bulletinboard.repository.support.CommentRepositorySupport;
import io.dkargo.bulletinboard.service.CommentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/posts/comments")
@RequiredArgsConstructor
@Transactional
public class CommentController {
    private final CommentService commentService;

    @ApiOperation(value = "게미물 댓글 등록")
    @PostMapping(value = "")
    public void addComment(@RequestBody ReqCommentDTO reqCommentDTO) {
        commentService.addComment(reqCommentDTO);
    }

    @ApiOperation(value = "댓글/답글 조회")
    @GetMapping(value = "")
    public List<ResCommentReplyDTO> findCommentReplyByPostId(@RequestParam Long postId) {
        return commentService.findCommentReplyByPostId(postId);
    }
}
