package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.dto.request.comment.ReqCreateCommentDTO;
import io.dkargo.bulletinboard.dto.request.comment.ReqUpdateCommentDTO;
import io.dkargo.bulletinboard.dto.response.comment.ResCreateCommentDTO;
import io.dkargo.bulletinboard.dto.response.comment.ResFindCommentReplyDTO;
import io.dkargo.bulletinboard.dto.response.comment.ResUpdateCommentDTO;
import io.dkargo.bulletinboard.entity.Comment;
import io.dkargo.bulletinboard.entity.Member;
import io.dkargo.bulletinboard.entity.Post;
import io.dkargo.bulletinboard.exception.CustomException;
import io.dkargo.bulletinboard.exception.ErrorCodeEnum;
import io.dkargo.bulletinboard.repository.CommentRepository;
import io.dkargo.bulletinboard.repository.MemberRepository;
import io.dkargo.bulletinboard.repository.PostRepository;
import io.dkargo.bulletinboard.repository.support.CommentRepositorySupport;
import io.dkargo.bulletinboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentRepositorySupport commentRepositorySupport;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Override
    public ResCreateCommentDTO createComment(long postId, ReqCreateCommentDTO reqCreateCommentDTO, long memberId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCodeEnum.POST_NOT_FOUND));

        //댓글/답글 플래그가 N인경우 불가
        if (!post.getReplyCommentUseFlag()) {
            throw new CustomException(ErrorCodeEnum.POST_NOT_CREATE_COMMENT);
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCodeEnum.MEMBER_NOT_FOUND));

        Comment comment = Comment.builder()
                .post(post)
                .member(member)
                .content(reqCreateCommentDTO.getContent())
                .build();

        commentRepository.save(comment);
        return new ResCreateCommentDTO(comment);
    }

    @Override
    public List<ResFindCommentReplyDTO> findCommentReplyByPostId(long postId) {
        List<Comment> commentList = commentRepositorySupport.findCommentByPostId(postId);

        return commentList
                .stream()
                .map(ResFindCommentReplyDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public ResUpdateCommentDTO updateComment(long commentId, ReqUpdateCommentDTO reqUpdateCommentDTO, long memberId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCodeEnum.COMMENT_NOT_FOUND));

        //작성자 인지 체크
        comment.getMember().userIdValidCheck(memberId);

        comment.update(reqUpdateCommentDTO);
        return new ResUpdateCommentDTO(comment);
    }

    @Override
    public void deleteComment(long commentId, long memberId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCodeEnum.COMMENT_NOT_FOUND));

        //작성자 인지 체크
        comment.getMember().userIdValidCheck(memberId);

        //답글 존재유무 체크
        comment.replyExistCheck();

        commentRepository.delete(comment);
    }
}
