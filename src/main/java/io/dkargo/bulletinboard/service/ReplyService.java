package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.ReqReplyDTO;

public interface ReplyService {
    void addReply(ReqReplyDTO reqReplyDTO);
}
