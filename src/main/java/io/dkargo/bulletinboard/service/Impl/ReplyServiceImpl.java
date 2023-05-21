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

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    @Override
    public ResCreateReplyDTO createReply(long commentId, ReqCreateReplyDTO reqCreateReplyDTO, long memberId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCodeEnum.COMMENT_NOT_FOUND));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCodeEnum.MEMBER_NOT_FOUND));

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
    public ResUpdateReplyDTO updateReply(long replyId, ReqUpdateReplyDTO reqUpdateReplyDTO, long memberId) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new CustomException(ErrorCodeEnum.COMMENT_NOT_FOUND));

        //작성자 인지 체크
        if (!reply.getMember().userIdValidCheck(memberId)) {
            throw new CustomException(ErrorCodeEnum.UPDATE_ONLY_WRITER);
        }

        reply.update(reqUpdateReplyDTO);
        return new ResUpdateReplyDTO(reply);
    }

    @Override
    public void deleteReply(long replyId, long memberId) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new CustomException(ErrorCodeEnum.COMMENT_NOT_FOUND));

        //작성자 인지 체크
        if (!reply.getMember().userIdValidCheck(memberId)) {
            throw new CustomException(ErrorCodeEnum.UPDATE_ONLY_WRITER);
        }

        replyRepository.delete(reply);

        Comment comment = reply.getComment();
        int replyCount = replyRepository.countReplyByComment(comment);

        if (replyCount == 0) {
            comment.replyExistFlagUpdate(false);
        }
    }
}
