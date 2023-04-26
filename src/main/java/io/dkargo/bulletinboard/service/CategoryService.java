package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.req.ReqCategoryDTO;
import io.dkargo.bulletinboard.entity.Category;

import java.util.List;

public interface CategoryService {
    void addCategory(ReqCategoryDTO reqCategoryDTO);

    List<Category> getCategories();
}
