package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.dto.request.comment.ReqAddCommentDTO;
import io.dkargo.bulletinboard.dto.request.comment.ReqDeleteCommentDTO;
import io.dkargo.bulletinboard.dto.request.comment.ReqPutCommentDTO;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentRepositorySupport commentRepositorySupport;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public void addComment(ReqAddCommentDTO reqAddCommentDTO) {
        Post post = postRepository.findById(reqAddCommentDTO.getPostId())
                .orElseThrow(() -> new RuntimeException("해당 게시물이 존재하지 않습니다."));

        if (post.getReplyCommentUseFlag().equals("N")) {
            throw new RuntimeException("해당 게시물은 댓글을 작성할수 없습니다.");
        }

        User user = userRepository.findById(reqAddCommentDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("해당 유저는 존재하지 않습니다."));


        Comment comment = Comment.builder()
                .post(post)
                .user(user)
                .content(reqAddCommentDTO.getContent())
                .build();

        commentRepository.save(comment);
    }

    @Override
    public List<ResCommentReplyDTO> findCommentReplyByPostId(Long postId) {
        List<Comment> commentList = commentRepositorySupport.findCommentByPostId(postId);

        return commentList
                .stream()
                .map(ResCommentReplyDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public void putComment(ReqPutCommentDTO reqPutCommentDTO) {
        Comment comment = commentRepository.findById(reqPutCommentDTO.getCommentId())
                .orElseThrow(() -> new RuntimeException("해당 댓글이 존재하지 않습니다."));

        if (!comment.getUser().userIdValidCheck(reqPutCommentDTO.getUserId())) {
            throw new RuntimeException("댓글 작성자만 수정할수 있습니다.");
        }

        comment.patch(reqPutCommentDTO);
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(ReqDeleteCommentDTO reqDeleteCommentDTO) {
        Comment comment = commentRepository.findById(reqDeleteCommentDTO.getCommentId())
                .orElseThrow(() -> new RuntimeException("해당 댓글이 존재하지 않습니다."));

        if (!comment.getUser().userIdValidCheck(reqDeleteCommentDTO.getUserId())) {
            throw new RuntimeException("댓글 작성자만 삭제할수 있습니다.");
        }

        if (comment.getReplyList().size() > 0) {
            throw new RuntimeException("답글 달린글은 삭제할수 없습니다.");
        }

        commentRepository.delete(comment);
    }
}
