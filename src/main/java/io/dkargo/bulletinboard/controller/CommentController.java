package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.request.comment.ReqAddCommentDTO;
import io.dkargo.bulletinboard.dto.request.comment.ReqDeleteCommentDTO;
import io.dkargo.bulletinboard.dto.request.comment.ReqPatchCommentDTO;
import io.dkargo.bulletinboard.dto.response.ResCommentReplyDTO;
import io.dkargo.bulletinboard.service.CommentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;


import javax.transaction.Transactional;
import java.util.List;


@RestController
@RequestMapping("/posts/comments")
@RequiredArgsConstructor
@Transactional
public class CommentController {
    private final CommentService commentService;

    @ApiOperation(value = "게시물 댓글 등록")
    @PostMapping(value = "")
    public void addComment(@RequestBody ReqAddCommentDTO reqAddCommentDTO) {
        commentService.addComment(reqAddCommentDTO);
    }
    @ApiOperation(value = "댓글/답글 조회")
    @GetMapping(value = "")
    public List<ResCommentReplyDTO> findCommentReplyByPostId(@RequestParam Long postId) {
        return commentService.findCommentReplyByPostId(postId);
    }

    @ApiOperation(value = "댓글 수정")
    @PatchMapping
    public void patchComment(@RequestBody ReqPatchCommentDTO reqPatchCommentDTO) {
         commentService.patchComment(reqPatchCommentDTO);
    }

    @ApiOperation(value = "댓글 삭제")
    @DeleteMapping
    public void deleteComment(@RequestBody ReqDeleteCommentDTO reqDeleteCommentDTO) {
        commentService.deleteComment(reqDeleteCommentDTO);
    }

}
