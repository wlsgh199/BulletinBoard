package io.dkargo.bulletinboard.service.Impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.dkargo.bulletinboard.dto.request.ReqPostDTO;
import io.dkargo.bulletinboard.dto.response.ResPostDTO;
import io.dkargo.bulletinboard.entity.Member;
import io.dkargo.bulletinboard.entity.Post;
import io.dkargo.bulletinboard.entity.QPost;
import io.dkargo.bulletinboard.entity.QPostFile;
import io.dkargo.bulletinboard.repository.PostRepository;
import io.dkargo.bulletinboard.repository.support.PostRepositorySupport;
import io.dkargo.bulletinboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostRepositorySupport postRepositorySupport;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<ResPostDTO> findAllPost(Pageable pageable) {
        return postRepositorySupport.findAllPost(pageable)
                .stream()
                .map(ResPostDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResPostDTO> findPostByMemberId(Long memberId, Pageable pageable) {
        return postRepositorySupport.findPostByMemberId(memberId, pageable)
                .stream()
                .map(ResPostDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResPostDTO> findPostByTitle(String title, Pageable pageable) {
        return postRepositorySupport.findPostByTitle(title, pageable)
                .stream()
                .map(ResPostDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResPostDTO> findPostByContent(String title, Pageable pageable) {
        return postRepositorySupport.findPostByContent(title, pageable)
                .stream()
                .map(ResPostDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResPostDTO> findPostByCategory(Long categoryId, Pageable pageable) {
        return postRepositorySupport.findPostByCategory(categoryId, pageable)
                .stream()
                .map(ResPostDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public Post savePost(Member member, ReqPostDTO reqPostDTO) {
        //게시글 저장
        Post post = new Post(member, reqPostDTO);
        postRepository.save(post);

        return post;
    }
}
