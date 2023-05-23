package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.reply.ReqCreateReplyDTO;
import io.dkargo.bulletinboard.dto.request.reply.ReqUpdateReplyDTO;
import io.dkargo.bulletinboard.dto.response.reply.ResCreateReplyDTO;
import io.dkargo.bulletinboard.dto.response.reply.ResUpdateReplyDTO;
import io.dkargo.bulletinboard.entity.Member;

public interface ReplyService {
    //답글 달기
    ResCreateReplyDTO createReply(long commentId, ReqCreateReplyDTO reqCreateReplyDTO, String email);
    //답글 수정
    ResUpdateReplyDTO updateReply(long replyId, ReqUpdateReplyDTO reqUpdateReplyDTO, String email);
    //답글 삭제
    void deleteReply(long replyId, String email);
}
