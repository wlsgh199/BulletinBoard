package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.reply.ReqAddReplyDTO;
import io.dkargo.bulletinboard.dto.request.reply.ReqDeleteReplyDTO;
import io.dkargo.bulletinboard.dto.request.reply.ReqPutReplyDTO;

public interface ReplyService {
    void addReply(ReqAddReplyDTO reqAddReplyDTO);
    void putReply(ReqPutReplyDTO reqPutReplyDTO);
    void deleteReply(ReqDeleteReplyDTO reqDeleteReplyDTO);
}
