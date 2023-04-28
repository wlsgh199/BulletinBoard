package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.ReqPostDTO;
import io.dkargo.bulletinboard.entity.Post;
import org.springframework.stereotype.Service;

@Service
public interface PostCategoryService {
    void saveAllPostCategory(Post post, Long categoryId);
}
