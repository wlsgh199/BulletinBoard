package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.dto.request.ReqCommentDTO;
import io.dkargo.bulletinboard.dto.request.ReqReplyDTO;
import io.dkargo.bulletinboard.entity.Comment;
import io.dkargo.bulletinboard.entity.Member;
import io.dkargo.bulletinboard.entity.Reply;
import io.dkargo.bulletinboard.repository.CommentRepository;
import io.dkargo.bulletinboard.repository.MemberRepository;
import io.dkargo.bulletinboard.repository.ReplyRepository;
import io.dkargo.bulletinboard.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    @Override
    public void addReply(ReqReplyDTO reqReplyDTO) {
        Comment comment = commentRepository.findById(reqReplyDTO.getCommentId()).orElseThrow(IllegalAccessError::new);
        Member member = memberRepository.findById(reqReplyDTO.getMemberId()).orElseThrow(IllegalAccessError::new);

        Reply reply = new Reply(comment, member, reqReplyDTO.getContent(), reqReplyDTO.getDepth());
        replyRepository.save(reply);
    }
}
