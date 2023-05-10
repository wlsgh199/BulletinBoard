package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.dto.request.comment.ReqCreateCommentDTO;
import io.dkargo.bulletinboard.dto.request.comment.ReqUpdateCommentDTO;
import io.dkargo.bulletinboard.dto.response.ResCommentReplyDTO;
import io.dkargo.bulletinboard.entity.Comment;
import io.dkargo.bulletinboard.entity.Post;
import io.dkargo.bulletinboard.entity.User;
import io.dkargo.bulletinboard.repository.CommentRepository;
import io.dkargo.bulletinboard.repository.PostRepository;
import io.dkargo.bulletinboard.repository.UserRepository;
import io.dkargo.bulletinboard.repository.support.CommentRepositorySupport;
import io.dkargo.bulletinboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentRepositorySupport commentRepositorySupport;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public void createComment(long postId, ReqCreateCommentDTO reqCreateCommentDTO) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("해당 게시물이 존재하지 않습니다."));

        if (!post.getReplyCommentUseFlag()) {
            throw new RuntimeException("해당 게시물은 댓글을 작성할수 없습니다.");
        }

        User user = userRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("해당 유저는 존재하지 않습니다."));


        Comment comment = Comment.builder()
                .post(post)
                .user(user)
                .content(reqCreateCommentDTO.getContent())
                .build();

        commentRepository.save(comment);
    }

    @Override
    public List<ResCommentReplyDTO> findCommentReplyByPostId(long postId) {
        List<Comment> commentList = commentRepositorySupport.findCommentByPostId(postId);

        return commentList
                .stream()
                .map(ResCommentReplyDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public void updateComment(long commentId, ReqUpdateCommentDTO reqUpdateCommentDTO) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("해당 댓글이 존재하지 않습니다."));

        if (!comment.getUser().userIdValidCheck(commentId)) {
            throw new RuntimeException("댓글 작성자만 수정할수 있습니다.");
        }

        comment.update(reqUpdateCommentDTO);
    }

    @Override
    public void deleteComment(long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("해당 댓글이 존재하지 않습니다."));

        //TODO : 시큐리티로 작성자 확인
//        if (!comment.getUser().userIdValidCheck(reqDeleteCommentDTO.getUserId())) {
//            throw new RuntimeException("댓글 작성자만 삭제할수 있습니다.");
//        }

        if (comment.getReplyExistFlag()) {
            throw new RuntimeException("답글 달린글은 삭제할수 없습니다.");
        }
        //TODO : 삭제할때 reply 0개일 경우 comment replyExsitFlag false 으로 바꾸기 추가

        commentRepository.delete(comment);
    }
}
