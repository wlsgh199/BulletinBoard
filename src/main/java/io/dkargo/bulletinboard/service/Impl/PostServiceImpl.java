package io.dkargo.bulletinboard.service.Impl;


import io.dkargo.bulletinboard.dto.request.ReqPostDTO;
import io.dkargo.bulletinboard.entity.Post;
import io.dkargo.bulletinboard.repository.PostCategoryRepository;
import io.dkargo.bulletinboard.repository.PostRepository;
import io.dkargo.bulletinboard.service.PostService;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository, PostCategoryRepository postCategoryRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public void savePost(ReqPostDTO reqPostDTO) {
        Post post = new Post(reqPostDTO);
        post.setCreateTime(LocalDateTime.now());
        postRepository.save(post);
    }
}
