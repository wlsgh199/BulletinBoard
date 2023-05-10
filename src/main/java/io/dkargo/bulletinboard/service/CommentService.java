package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.comment.ReqCreateCommentDTO;
import io.dkargo.bulletinboard.dto.request.comment.ReqUpdateCommentDTO;
import io.dkargo.bulletinboard.dto.response.ResCommentReplyDTO;

import java.util.List;

public interface CommentService {
    //댓글 추가
    void createComment(long postId, ReqCreateCommentDTO reqCreateCommentDTO);

    //게시물 아이디로 댓글/답글 리스트 조회
    List<ResCommentReplyDTO> findCommentReplyByPostId(long postId);

    //댓글 수정
    void updateComment(long commentId, ReqUpdateCommentDTO reqUpdateCommentDTO);

    //댓글 삭제
    void deleteComment(long commentId);
}
