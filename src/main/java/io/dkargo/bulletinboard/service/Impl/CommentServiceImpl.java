package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.dto.request.ReqCommentDTO;
import io.dkargo.bulletinboard.dto.request.ReqPostDTO;
import io.dkargo.bulletinboard.entity.Comment;
import io.dkargo.bulletinboard.entity.Member;
import io.dkargo.bulletinboard.entity.Post;
import io.dkargo.bulletinboard.repository.CommentRepository;
import io.dkargo.bulletinboard.repository.MemberRepository;
import io.dkargo.bulletinboard.repository.PostRepository;
import io.dkargo.bulletinboard.repository.support.CommentRepositorySupport;
import io.dkargo.bulletinboard.service.CommentService;
import io.dkargo.bulletinboard.service.MemberService;
import io.dkargo.bulletinboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentRepositorySupport commentRepositorySupport;

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Override
    public void addComment(ReqCommentDTO reqCommentDTO) {
        Post post = postRepository.findById(reqCommentDTO.getPostId()).orElseThrow(IllegalAccessError::new);
        System.out.println("reqCommentDTO = " + reqCommentDTO.getMemberId());

        Member member = memberRepository.findById(reqCommentDTO.getMemberId()).orElseThrow(IllegalAccessError::new);
        Comment comment = new Comment(post, member, reqCommentDTO.getContent(), reqCommentDTO.getDepth());
        commentRepository.save(comment);
    }
}
