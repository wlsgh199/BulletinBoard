package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.reply.ReqCreateReplyDTO;
import io.dkargo.bulletinboard.dto.request.reply.ReqUpdateReplyDTO;
import io.dkargo.bulletinboard.dto.response.reply.ResCreateReplyDTO;
import io.dkargo.bulletinboard.dto.response.reply.ResUpdateReplyDTO;

public interface ReplyService {
    //답글 달기
    ResCreateReplyDTO createReply(long commentId, ReqCreateReplyDTO reqCreateReplyDTO);
    //답글 수정
    ResUpdateReplyDTO updateReply(long replyId, ReqUpdateReplyDTO reqUpdateReplyDTO);
    //답글 삭제
    void deleteReply(long replyId);
}
