package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.dto.request.ReqPostDTO;
import io.dkargo.bulletinboard.entity.Member;
import io.dkargo.bulletinboard.entity.Post;
import io.dkargo.bulletinboard.repository.PostRepository;
import io.dkargo.bulletinboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public Post savePost(Member member, ReqPostDTO reqPostDTO) {
        //게시글 저장
        Post post = new Post(member, reqPostDTO);
        postRepository.save(post);

        return post;
    }
}
