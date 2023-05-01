package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.ReqPostDTO;
import io.dkargo.bulletinboard.dto.response.ResPostDTO;
import io.dkargo.bulletinboard.entity.Member;
import io.dkargo.bulletinboard.entity.Post;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    List<ResPostDTO> findAllPost(Pageable pageable);
    List<ResPostDTO> findPostByMemberId(Long memberId, Pageable pageable);
    List<ResPostDTO> findPostByTitle(String title, Pageable pageable);
    List<ResPostDTO> findPostByContent(String title, Pageable pageable);
    List<ResPostDTO> findPostByCategory(Long categoryId, Pageable pageable);

    Post savePost(Member member, ReqPostDTO reqPostDTO);

}
