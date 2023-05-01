package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.ReqCommentDTO;

public interface CommentService {
    void addComment(ReqCommentDTO reqCommentDTO);
}
