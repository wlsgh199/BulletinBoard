package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.dto.request.reply.ReqAddReplyDTO;
import io.dkargo.bulletinboard.dto.request.reply.ReqDeleteReplyDTO;
import io.dkargo.bulletinboard.dto.request.reply.ReqPatchReplyDTO;
import io.dkargo.bulletinboard.entity.Comment;
import io.dkargo.bulletinboard.entity.Reply;
import io.dkargo.bulletinboard.entity.User;
import io.dkargo.bulletinboard.repository.CommentRepository;
import io.dkargo.bulletinboard.repository.ReplyRepository;
import io.dkargo.bulletinboard.repository.UserRepository;
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
    public void addReply(ReqAddReplyDTO reqAddReplyDTO) {
        Comment comment = commentRepository.findById(reqAddReplyDTO.getCommentId())
                .orElseThrow(() -> new RuntimeException("해당 댓글이 존재하지 않습니다."));

        User user = userRepository.findById(reqAddReplyDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("해당 유저는 존재하지 않습니다."));

        Reply reply = Reply.builder()
                .comment(comment)
                .user(user)
                .content(reqAddReplyDTO.getContent())
                .build();

        replyRepository.save(reply);
    }

    @Override
    public void patchReply(ReqPatchReplyDTO reqPatchReplyDTO) {
        Reply reply = replyRepository.findById(reqPatchReplyDTO.getReplyId())
                .orElseThrow(() -> new RuntimeException("해당 댓글이 존재하지 않습니다."));

        if (!reply.getUser().userIdValidCheck(reqPatchReplyDTO.getUserId())) {
            throw new RuntimeException("답글 작성자만 수정할수 있습니다.");
        }

        reply.patch(reqPatchReplyDTO);
        replyRepository.save(reply);
    }

    @Override
    public void deleteReply(ReqDeleteReplyDTO reqDeleteReplyDTO) {
        Reply reply = replyRepository.findById(reqDeleteReplyDTO.getReplyId())
                .orElseThrow(() -> new RuntimeException("해당 댓글이 존재하지 않습니다."));

        if (!reply.getUser().userIdValidCheck(reqDeleteReplyDTO.getUserId())) {
            throw new RuntimeException("답글 작성자만 삭제할수 있습니다.");
        }

        replyRepository.delete(reply);
    }
}
