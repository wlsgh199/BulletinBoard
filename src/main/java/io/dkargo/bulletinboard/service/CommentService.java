package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.comment.ReqAddCommentDTO;
import io.dkargo.bulletinboard.dto.request.comment.ReqDeleteCommentDTO;
import io.dkargo.bulletinboard.dto.request.comment.ReqPutCommentDTO;
import io.dkargo.bulletinboard.dto.response.ResCommentReplyDTO;

import java.util.List;

public interface CommentService {
    //댓글 추가
    void addComment(ReqAddCommentDTO reqAddCommentDTO);
    //게시물 아이디로 댓글/답글 리스트 조회
    List<ResCommentReplyDTO> findCommentReplyByPostId(long postId);
    //댓글 수정
    void putComment(ReqPutCommentDTO reqPutCommentDTO);
    //댓글 삭제
    void deleteComment(ReqDeleteCommentDTO reqDeleteCommentDTO);
}
