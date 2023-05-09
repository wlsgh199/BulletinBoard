package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.request.comment.ReqAddCommentDTO;
import io.dkargo.bulletinboard.dto.request.comment.ReqDeleteCommentDTO;
import io.dkargo.bulletinboard.dto.request.comment.ReqPutCommentDTO;
import io.dkargo.bulletinboard.dto.response.ResCommentReplyDTO;
import io.dkargo.bulletinboard.service.CommentService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
@RequestMapping("api/v1/posts/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "게시물 댓글 등록")
    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public void addComment(@RequestBody @Valid ReqAddCommentDTO reqAddCommentDTO) {
        commentService.addComment(reqAddCommentDTO);
    }

    @Operation(summary = "댓글/답글 리스트 조회")
    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public List<ResCommentReplyDTO> findCommentReplyByPostId(@RequestParam @NotNull Long postId) {
        return commentService.findCommentReplyByPostId(postId);
    }

    @Operation(summary = "댓글 수정")
    @PutMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void putComment(@RequestBody @Valid ReqPutCommentDTO reqPutCommentDTO) {
        commentService.putComment(reqPutCommentDTO);
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@RequestBody @Valid ReqDeleteCommentDTO reqDeleteCommentDTO) {
        commentService.deleteComment(reqDeleteCommentDTO);
    }

}
