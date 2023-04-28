package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.dto.request.ReqPostDTO;
import io.dkargo.bulletinboard.entity.Member;
import io.dkargo.bulletinboard.entity.Post;
import io.dkargo.bulletinboard.repository.PostRepository;
import io.dkargo.bulletinboard.service.PostService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;


@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post savePost(Member member, ReqPostDTO reqPostDTO) {
        //게시글 저장
        Post post = new Post(member, reqPostDTO);
        post.setCreateTime(LocalDateTime.now());
        postRepository.save(post);

        return post;
    }
}
