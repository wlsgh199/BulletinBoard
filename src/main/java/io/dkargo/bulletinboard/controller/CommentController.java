package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.request.comment.ReqAddCommentDTO;
import io.dkargo.bulletinboard.dto.request.comment.ReqDeleteCommentDTO;
import io.dkargo.bulletinboard.dto.request.comment.ReqPutCommentDTO;
import io.dkargo.bulletinboard.dto.response.ResCommentReplyDTO;
import io.dkargo.bulletinboard.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;


import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
@RequestMapping("/posts/comments")
@RequiredArgsConstructor
@Transactional
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "게시물 댓글 등록")
    @PostMapping(value = "")
    public void addComment(@RequestBody @Valid ReqAddCommentDTO reqAddCommentDTO) {
        commentService.addComment(reqAddCommentDTO);
    }

    @Operation(summary = "댓글/답글 리스트 조회")
    @GetMapping(value = "")
    public List<ResCommentReplyDTO> findCommentReplyByPostId(@RequestParam @NotNull Long postId) {
        return commentService.findCommentReplyByPostId(postId);
    }

    @Operation(summary = "댓글 수정")
    @PutMapping("")
    public void putComment(@RequestBody @Valid ReqPutCommentDTO reqPutCommentDTO) {
        commentService.putComment(reqPutCommentDTO);
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping
    public void deleteComment(@RequestBody @Valid ReqDeleteCommentDTO reqDeleteCommentDTO) {
        commentService.deleteComment(reqDeleteCommentDTO);
    }

}
