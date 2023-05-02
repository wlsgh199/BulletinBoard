package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.ReqCommentDTO;
import io.dkargo.bulletinboard.dto.response.ResCommentReplyDTO;

import java.util.List;

public interface CommentService {
    void addComment(ReqCommentDTO reqCommentDTO);

    List<ResCommentReplyDTO> findCommentReplyByPostId(Long postId);
}
