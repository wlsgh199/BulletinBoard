package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.category.ReqCreateCategoryDTO;
import io.dkargo.bulletinboard.dto.request.category.ReqUpdateCategoryNameDTO;
import io.dkargo.bulletinboard.dto.response.category.ResFindCategoryDTO;
import io.dkargo.bulletinboard.dto.response.category.ResCreateCategoryDTO;
import io.dkargo.bulletinboard.dto.response.category.ResUpdateCategoryDTO;

import java.util.List;

public interface CategoryService {
    //카테고리 전체 조회
    List<ResFindCategoryDTO> findCategoryByParentIdOrderByCategoryNameAsc(Integer parentId);
    //카테고리 추가
    ResCreateCategoryDTO createCategory(ReqCreateCategoryDTO reqCreateCategoryDTO);

    //카테고리 이름 수정
    ResUpdateCategoryDTO updateCategoryName(Long categoryId, ReqUpdateCategoryNameDTO reqUpdateCategoryNameDTO);
    //카테고리 삭제
    void deleteCategory(long categoryId);

}
