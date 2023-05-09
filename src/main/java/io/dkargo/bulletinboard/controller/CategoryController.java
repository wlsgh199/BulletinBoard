package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.request.category.ReqAddCategoryDTO;
import io.dkargo.bulletinboard.dto.request.category.ReqDeleteCategoryDTO;
import io.dkargo.bulletinboard.dto.request.category.ReqPatchCategoryDTO;
import io.dkargo.bulletinboard.dto.request.category.ReqPutCategoryDTO;
import io.dkargo.bulletinboard.dto.response.ResCategoryDTO;
import io.dkargo.bulletinboard.entity.Category;
import io.dkargo.bulletinboard.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "카테고리 추가")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Category addCategory(@RequestBody @Valid ReqAddCategoryDTO reqAddCategoryDTO) {
        return categoryService.addCategory(reqAddCategoryDTO);
    }

    @Operation(summary = "전체 카테고리 조회 ")
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<ResCategoryDTO> findAllCategory() {
        return categoryService.findAllCategory();
    }

    @Operation(summary = "카테고리 부분 수정")
    @PatchMapping(value = "")
    @ResponseStatus(HttpStatus.OK)  //TODO : 리턴 추가
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
    @ResponseStatus(HttpStatus.OK)
    public void deletePost(@RequestBody @Valid ReqDeleteCategoryDTO reqDeleteCategoryDTO) {
        categoryService.deleteCategory(reqDeleteCategoryDTO);
    }


}
