package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.category.ReqAddCategoryDTO;
import io.dkargo.bulletinboard.dto.request.category.ReqDeleteCategoryDTO;
import io.dkargo.bulletinboard.dto.request.category.ReqPatchCategoryDTO;
import io.dkargo.bulletinboard.dto.request.category.ReqPutCategoryDTO;
import io.dkargo.bulletinboard.dto.request.post.ReqDeletePostDTO;
import io.dkargo.bulletinboard.dto.request.post.ReqPatchPostDTO;
import io.dkargo.bulletinboard.dto.request.post.ReqPutPostDTO;
import io.dkargo.bulletinboard.dto.response.ResCategoryDTO;
import io.dkargo.bulletinboard.entity.Category;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    //카테고리 전체 조회
    List<ResCategoryDTO> findAllCategory();
    //카테고리 추가
    void addCategory(ReqAddCategoryDTO reqAddCategoryDTO);
    //카테고리 수정
    void putCategory(ReqPutCategoryDTO reqPutCategoryDTO);
    //카테고리 부분 수정
    void patchCategory(ReqPatchCategoryDTO reqPatchCategoryDTO);
    //카테고리 삭제
    void deleteCategory(ReqDeleteCategoryDTO reqDeleteCategoryDTO);

}
