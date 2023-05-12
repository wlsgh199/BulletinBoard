package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.dto.request.reply.ReqCreateReplyDTO;
import io.dkargo.bulletinboard.dto.request.reply.ReqUpdateReplyDTO;
import io.dkargo.bulletinboard.entity.Comment;
import io.dkargo.bulletinboard.entity.Reply;
import io.dkargo.bulletinboard.exception.CustomException;
import io.dkargo.bulletinboard.exception.ErrorCode;
import io.dkargo.bulletinboard.repository.CommentRepository;
import io.dkargo.bulletinboard.repository.ReplyRepository;
import io.dkargo.bulletinboard.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Override
    public void createReply(long commentId, ReqCreateReplyDTO reqCreateReplyDTO) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

//        User user = userRepository.findById(reqCreateReplyDTO.getUserId())
//                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Reply reply = Reply.builder()
                .comment(comment)
//                .user(user)
                .content(reqCreateReplyDTO.getContent())
                .build();

        //처음 댓글이면 true 로 업데이트
        if(!comment.getReplyExistFlag()) {
            comment.replyExistFlagUpdate(true);
        }

        replyRepository.save(reply);
    }

    @Override
    public void updateReply(long replyId, ReqUpdateReplyDTO reqUpdateReplyDTO) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        if (!reply.getUser().userIdValidCheck(replyId)) {
            throw new CustomException(ErrorCode.UPDATE_ONLY_WRITER);
        }

        reply.update(reqUpdateReplyDTO);
    }

    @Override
    public void deleteReply(long replyId) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

//        if (!reply.getUser().userIdValidCheck(replyId)) {
//        throw new CustomException(ErrorCode.UPDATE_ONLY_WRITER);
//        }
        replyRepository.delete(reply);

        Comment comment = reply.getComment();
        int replyCount = replyRepository.countReplyByComment(comment);

        if(replyCount == 0) {
            comment.replyExistFlagUpdate(false);
        }
    }
}
