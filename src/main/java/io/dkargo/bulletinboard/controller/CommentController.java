package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.request.comment.ReqCreateCommentDTO;
import io.dkargo.bulletinboard.dto.request.comment.ReqUpdateCommentDTO;
import io.dkargo.bulletinboard.dto.response.ResCommentReplyDTO;
import io.dkargo.bulletinboard.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/posts/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "댓글/답글 리스트 조회")
    @GetMapping(value = "/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ResCommentReplyDTO> findCommentReplyByPostId(@PathVariable long postId) {
        return commentService.findCommentReplyByPostId(postId);
    }

    @Operation(summary = "게시물 댓글 등록")
    @PostMapping(value = "/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createComment(@PathVariable long postId,
                              @RequestBody @Valid ReqCreateCommentDTO reqCreateCommentDTO) {
        commentService.createComment(postId, reqCreateCommentDTO);
    }

    @Operation(summary = "댓글 수정")
    @PutMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateComment(@PathVariable long commentId,
                              @RequestBody @Valid ReqUpdateCommentDTO reqUpdateCommentDTO) {
        commentService.updateComment(commentId, reqUpdateCommentDTO);
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("{/{commentId}}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable long commentId) {
        commentService.deleteComment(commentId);
    }

}
