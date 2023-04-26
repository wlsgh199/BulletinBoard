package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.entity.PostCategory;

import java.util.List;

public interface PostCategoryService {
    void saveAllPostCategory(List<PostCategory> postCategoryList);
}
