package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.comment.ReqAddCommentDTO;
import io.dkargo.bulletinboard.dto.request.comment.ReqDeleteCommentDTO;
import io.dkargo.bulletinboard.dto.request.comment.ReqPatchCommentDTO;
import io.dkargo.bulletinboard.dto.response.ResCommentReplyDTO;

import java.util.List;

public interface CommentService {
    void addComment(ReqAddCommentDTO reqAddCommentDTO);
    List<ResCommentReplyDTO> findCommentReplyByPostId(Long postId);
    void patchComment(ReqPatchCommentDTO reqPatchCommentDTO);
    void deleteComment(ReqDeleteCommentDTO reqDeleteCommentDTO);
}
