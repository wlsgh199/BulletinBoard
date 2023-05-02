package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.dto.request.ReqReplyDTO;
import io.dkargo.bulletinboard.entity.Comment;
import io.dkargo.bulletinboard.entity.User;
import io.dkargo.bulletinboard.entity.Reply;
import io.dkargo.bulletinboard.repository.CommentRepository;
import io.dkargo.bulletinboard.repository.UserRepository;
import io.dkargo.bulletinboard.repository.ReplyRepository;
import io.dkargo.bulletinboard.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    @Override
    public void addReply(ReqReplyDTO reqReplyDTO) {
        Comment comment = commentRepository.findById(reqReplyDTO.getCommentId()).orElseThrow(IllegalAccessError::new);
        User user = userRepository.findById(reqReplyDTO.getUserId()).orElseThrow(IllegalAccessError::new);

        Reply reply = new Reply(comment, user, reqReplyDTO.getContent(), reqReplyDTO.getDepth());
        replyRepository.save(reply);
    }
}
