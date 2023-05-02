package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.dto.request.ReqCommentDTO;
import io.dkargo.bulletinboard.dto.response.ResCommentReplyDTO;
import io.dkargo.bulletinboard.entity.Comment;
import io.dkargo.bulletinboard.entity.User;
import io.dkargo.bulletinboard.entity.Post;
import io.dkargo.bulletinboard.repository.CommentRepository;
import io.dkargo.bulletinboard.repository.UserRepository;
import io.dkargo.bulletinboard.repository.PostRepository;
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

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public void addComment(ReqCommentDTO reqCommentDTO) {
        Post post = postRepository.findById(reqCommentDTO.getPostId()).orElseThrow(IllegalAccessError::new);
        System.out.println("reqCommentDTO = " + reqCommentDTO.getUserId());

        User user = userRepository.findById(reqCommentDTO.getUserId()).orElseThrow(IllegalAccessError::new);
        Comment comment = new Comment(post, user, reqCommentDTO.getContent(), reqCommentDTO.getDepth());
        commentRepository.save(comment);
    }

    @Override
    public List<ResCommentReplyDTO> findCommentReplyByPostId(Long postId) {
        List<Comment> commentList = commentRepositorySupport.findCommentByPostId(postId);

        return  commentList
                .stream()
                .map(ResCommentReplyDTO::new)
                .collect(Collectors.toList());
    }
}
