package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.request.category.ReqAddCategoryDTO;
import io.dkargo.bulletinboard.dto.request.category.ReqDeleteCategoryDTO;
import io.dkargo.bulletinboard.dto.request.category.ReqPatchCategoryDTO;
import io.dkargo.bulletinboard.dto.request.category.ReqPutCategoryDTO;
import io.dkargo.bulletinboard.dto.request.post.ReqDeletePostDTO;
import io.dkargo.bulletinboard.dto.request.post.ReqPatchPostDTO;
import io.dkargo.bulletinboard.dto.request.post.ReqPutPostDTO;
import io.dkargo.bulletinboard.dto.response.ResCategoryDTO;
import io.dkargo.bulletinboard.entity.Category;
import io.dkargo.bulletinboard.service.CategoryService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
@Transactional
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @ApiOperation(value = "카테고리 추가")
    @PostMapping("")
    public void addCategory(@RequestBody @Valid ReqAddCategoryDTO reqAddCategoryDTO) {
        categoryService.addCategory(reqAddCategoryDTO);
    }

    @ApiOperation(value = "전체 카테고리 조회 ")
    @GetMapping("")
    public List<ResCategoryDTO> findAllCategory() {
        return categoryService.findAllCategory();
    }

    @ApiOperation(value = "카테고리 부분 수정")
    @PatchMapping(value = "")
    public void patchPost(@RequestBody @Valid ReqPatchCategoryDTO reqPatchCategoryDTO) {
        categoryService.patchCategory(reqPatchCategoryDTO);
    }

    @ApiOperation(value = "카테고리 수정")
    @PutMapping(value = "")
    public void putPost(@RequestBody @Valid ReqPutCategoryDTO reqPutCategoryDTO) {
        categoryService.putCategory(reqPutCategoryDTO);
    }

    @ApiOperation(value = "카테고리 삭제")
    @DeleteMapping(value = "")
    public void deletePost(@RequestBody @Valid ReqDeleteCategoryDTO reqDeleteCategoryDTO) {
        categoryService.deleteCategory(reqDeleteCategoryDTO);
    }


}
