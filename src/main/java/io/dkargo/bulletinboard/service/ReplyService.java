package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.reply.ReqAddReplyDTO;
import io.dkargo.bulletinboard.dto.request.reply.ReqDeleteReplyDTO;
import io.dkargo.bulletinboard.dto.request.reply.ReqPatchReplyDTO;

public interface ReplyService {
    void addReply(ReqAddReplyDTO reqAddReplyDTO);
    void patchReply(ReqPatchReplyDTO reqPatchReplyDTO);
    void deleteReply(ReqDeleteReplyDTO reqDeleteReplyDTO);
}
