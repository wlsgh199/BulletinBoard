package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.comment.ReqCreateCommentDTO;
import io.dkargo.bulletinboard.dto.request.comment.ReqUpdateCommentDTO;
import io.dkargo.bulletinboard.dto.response.comment.ResFindCommentReplyDTO;
import io.dkargo.bulletinboard.dto.response.comment.ResCreateCommentDTO;
import io.dkargo.bulletinboard.dto.response.comment.ResUpdateCommentDTO;
import io.dkargo.bulletinboard.entity.Member;

import java.util.List;

public interface CommentService {
    //댓글 추가
    ResCreateCommentDTO createComment(long postId, ReqCreateCommentDTO reqCreateCommentDTO, long memberId);

    //게시물 아이디로 댓글/답글 리스트 조회
    List<ResFindCommentReplyDTO> findCommentReplyByPostId(long postId);

    //댓글 수정
    ResUpdateCommentDTO updateComment(long commentId, ReqUpdateCommentDTO reqUpdateCommentDTO, long memberId);

    //댓글 삭제
    void deleteComment(long commentId, long memberId);
}
