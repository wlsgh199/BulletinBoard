package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.entity.Post;

public interface PostCategoryService {
    //게시물 * 카테고리 수 (1:N) 저장
    void saveAllPostCategory(Post post, Long categoryId);
}
