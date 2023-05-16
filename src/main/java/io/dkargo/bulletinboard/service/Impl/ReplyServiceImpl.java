package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.dto.request.reply.ReqCreateReplyDTO;
import io.dkargo.bulletinboard.dto.request.reply.ReqUpdateReplyDTO;
import io.dkargo.bulletinboard.dto.response.reply.ResCreateReplyDTO;
import io.dkargo.bulletinboard.dto.response.reply.ResUpdateReplyDTO;
import io.dkargo.bulletinboard.entity.Comment;
import io.dkargo.bulletinboard.entity.Member;
import io.dkargo.bulletinboard.entity.Reply;
import io.dkargo.bulletinboard.exception.CustomException;
import io.dkargo.bulletinboard.exception.ErrorCodeEnum;
import io.dkargo.bulletinboard.repository.CommentRepository;
import io.dkargo.bulletinboard.repository.MemberRepository;
import io.dkargo.bulletinboard.repository.ReplyRepository;
import io.dkargo.bulletinboard.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final CommentRepository commentRepository;

    @Override
    public ResCreateReplyDTO createReply(long commentId, ReqCreateReplyDTO reqCreateReplyDTO, Member member) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCodeEnum.COMMENT_NOT_FOUND));

        Reply reply = Reply.builder()
                .comment(comment)
                .member(member)
                .content(reqCreateReplyDTO.getContent())
                .build();

        //처음 댓글이면 true 로 업데이트
        if (!comment.getReplyExistFlag()) {
            comment.replyExistFlagUpdate(true);
        }

        replyRepository.save(reply);
        return new ResCreateReplyDTO(reply);
    }

    @Override
    public ResUpdateReplyDTO updateReply(long replyId, ReqUpdateReplyDTO reqUpdateReplyDTO, Member member) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new CustomException(ErrorCodeEnum.COMMENT_NOT_FOUND));

        //작성자 인지 체크
        reply.getMember().userIdValidCheck(member.getId());

        reply.update(reqUpdateReplyDTO);
        return new ResUpdateReplyDTO(reply);
    }

    @Override
    public void deleteReply(long replyId, Member member) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new CustomException(ErrorCodeEnum.COMMENT_NOT_FOUND));

        //작성자 인지 체크
        reply.getMember().userIdValidCheck(member.getId());

        replyRepository.delete(reply);

        Comment comment = reply.getComment();
        int replyCount = replyRepository.countReplyByComment(comment);

        if (replyCount == 0) {
            comment.replyExistFlagUpdate(false);
        }
    }
}
