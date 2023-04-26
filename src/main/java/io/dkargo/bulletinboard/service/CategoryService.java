package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.ReqCategoryDTO;
import io.dkargo.bulletinboard.entity.Category;

import java.util.List;

public interface CategoryService {
    void saveCategory(ReqCategoryDTO reqCategoryDTO);

    List<Category> findAllCategory();
}
