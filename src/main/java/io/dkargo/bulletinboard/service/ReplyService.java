package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.reply.ReqAddReplyDTO;
import io.dkargo.bulletinboard.dto.request.reply.ReqDeleteReplyDTO;
import io.dkargo.bulletinboard.dto.request.reply.ReqPutReplyDTO;

public interface ReplyService {
    //답글 달기
    void addReply(ReqAddReplyDTO reqAddReplyDTO);
    //답글 수정
    void putReply(ReqPutReplyDTO reqPutReplyDTO);
    //답글 삭제
    void deleteReply(ReqDeleteReplyDTO reqDeleteReplyDTO);
}
