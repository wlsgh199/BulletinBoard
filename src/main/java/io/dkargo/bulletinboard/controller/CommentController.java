package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.annotation.CurrentMember;
import io.dkargo.bulletinboard.dto.request.comment.ReqCreateCommentDTO;
import io.dkargo.bulletinboard.dto.request.comment.ReqUpdateCommentDTO;
import io.dkargo.bulletinboard.dto.response.comment.ResFindCommentReplyDTO;
import io.dkargo.bulletinboard.dto.response.comment.ResCreateCommentDTO;
import io.dkargo.bulletinboard.dto.response.comment.ResUpdateCommentDTO;
import io.dkargo.bulletinboard.jwt.MemberDetailsDTO;
import io.dkargo.bulletinboard.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
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
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public List<ResFindCommentReplyDTO> findCommentReplyByPostId(@PathVariable long postId) {
        return commentService.findCommentReplyByPostId(postId);
    }

    @Operation(summary = "게시물 댓글 등록")
    @PostMapping(value = "/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResCreateCommentDTO createComment(@PathVariable long postId,
                                             @RequestBody @Valid ReqCreateCommentDTO reqCreateCommentDTO,
                                             @CurrentMember MemberDetailsDTO memberDetailsDTO) {
        return commentService.createComment(postId, reqCreateCommentDTO, memberDetailsDTO.getUsername());
    }

    @Operation(summary = "댓글 수정")
    @PutMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResUpdateCommentDTO updateComment(@PathVariable long commentId,
                                             @RequestBody @Valid ReqUpdateCommentDTO reqUpdateCommentDTO,
                                             @CurrentMember MemberDetailsDTO memberDetailsDTO) {
        return commentService.updateComment(commentId, reqUpdateCommentDTO, memberDetailsDTO.getUsername());
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public void deleteComment(@PathVariable long commentId,
                              @CurrentMember MemberDetailsDTO memberDetailsDTO) {
        commentService.deleteComment(commentId, memberDetailsDTO.getUsername());
    }

}
