package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.request.category.ReqAddCategoryDTO;
import io.dkargo.bulletinboard.dto.request.category.ReqDeleteCategoryDTO;
import io.dkargo.bulletinboard.dto.request.category.ReqPatchCategoryDTO;
import io.dkargo.bulletinboard.dto.request.category.ReqPutCategoryDTO;
import io.dkargo.bulletinboard.dto.response.ResCategoryDTO;
import io.dkargo.bulletinboard.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
@Transactional
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "카테고리 추가")
    @PostMapping("")
    public void addCategory(@RequestBody @Valid ReqAddCategoryDTO reqAddCategoryDTO) {
        categoryService.addCategory(reqAddCategoryDTO);
    }

    @Operation(summary = "전체 카테고리 조회 ")
    @GetMapping("")
    public List<ResCategoryDTO> findAllCategory() {
        return categoryService.findAllCategory();
    }

    @Operation(summary = "카테고리 부분 수정")
    @PatchMapping(value = "")
    public void patchPost(@RequestBody @Valid ReqPatchCategoryDTO reqPatchCategoryDTO) {
        categoryService.patchCategory(reqPatchCategoryDTO);
    }

    @Operation(summary = "카테고리 수정")
    @PutMapping(value = "")
    public void putPost(@RequestBody @Valid ReqPutCategoryDTO reqPutCategoryDTO) {
        categoryService.putCategory(reqPutCategoryDTO);
    }

    @Operation(summary = "카테고리 삭제")
    @DeleteMapping(value = "")
    public void deletePost(@RequestBody @Valid ReqDeleteCategoryDTO reqDeleteCategoryDTO) {
        categoryService.deleteCategory(reqDeleteCategoryDTO);
    }


}
