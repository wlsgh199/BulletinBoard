package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.ReqPostDTO;
import io.dkargo.bulletinboard.entity.Member;
import io.dkargo.bulletinboard.entity.Post;

import java.util.List;

public interface PostService {
    Post findPostById(Long id);
    Post savePost(Member member, ReqPostDTO reqPostDTO);

    List<Post> findPostByMemberId(Long id);
}
