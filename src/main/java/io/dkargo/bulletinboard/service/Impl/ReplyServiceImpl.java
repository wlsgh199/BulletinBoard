package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.dto.request.reply.ReqCreateReplyDTO;
import io.dkargo.bulletinboard.dto.request.reply.ReqUpdateReplyDTO;
import io.dkargo.bulletinboard.entity.Comment;
import io.dkargo.bulletinboard.entity.Reply;
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
                .orElseThrow(() -> new RuntimeException("해당 댓글이 존재하지 않습니다."));

//        User user = userRepository.findById(reqCreateReplyDTO.getUserId())
//                .orElseThrow(() -> new RuntimeException("해당 유저는 존재하지 않습니다."));

        Reply reply = Reply.builder()
                .comment(comment)
//                .user(user)
                .content(reqCreateReplyDTO.getContent())
                .build();

        replyRepository.save(reply);
    }

    @Override
    public void updateReply(long replyId, ReqUpdateReplyDTO reqUpdateReplyDTO) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new RuntimeException("해당 댓글이 존재하지 않습니다."));

        if (!reply.getUser().userIdValidCheck(replyId)) {
            throw new RuntimeException("답글 작성자만 수정할수 있습니다.");
        }

        reply.update(reqUpdateReplyDTO);
    }

    @Override
    public void deleteReply(long replyId) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new RuntimeException("해당 댓글이 존재하지 않습니다."));

//        if (!reply.getUser().userIdValidCheck(replyId)) {
//            throw new RuntimeException("답글 작성자만 삭제할수 있습니다.");
//        }

        replyRepository.delete(reply);
    }
}
