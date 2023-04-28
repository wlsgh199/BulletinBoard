package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.entity.Post;

public interface PostCategoryService {
    void saveAllPostCategory(Post post, Long categoryId);
}
