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
    List<ResCategoryDTO> findAllCategory();

    void addCategory(ReqAddCategoryDTO reqAddCategoryDTO);
    void putCategory(ReqPutCategoryDTO reqPutCategoryDTO);
    void patchCategory(ReqPatchCategoryDTO reqPatchCategoryDTO);
    void deleteCategory(ReqDeleteCategoryDTO reqDeleteCategoryDTO);

}
