package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.reply.ReqCreateReplyDTO;
import io.dkargo.bulletinboard.dto.request.reply.ReqUpdateReplyDTO;

public interface ReplyService {
    //답글 달기
    void createReply(long commentId, ReqCreateReplyDTO reqCreateReplyDTO);
    //답글 수정
    void updateReply(long replyId, ReqUpdateReplyDTO reqUpdateReplyDTO);
    //답글 삭제
    void deleteReply(long replyId);
}
