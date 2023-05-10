package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.request.category.ReqCreateCategoryDTO;
import io.dkargo.bulletinboard.dto.request.category.ReqUpdateCategoryNameDTO;
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

    @Operation(summary = "부모 카테고리 아이디로 카테고리 조회 ")
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<ResCategoryDTO> findCategoryByParentId(@RequestParam(required = false) Integer parentId) {
        return categoryService.findCategoryByParentIdOrderByCategoryNameAsc(parentId);
    }

    @Operation(summary = "카테고리 추가")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Category createCategory(@RequestBody @Valid ReqCreateCategoryDTO reqCreateCategoryDTO) {
        return categoryService.createCategory(reqCreateCategoryDTO);
    }

    @Operation(summary = "카테고리 이름 수정")
    @PatchMapping(value = "/{categoryId}")
    @ResponseStatus(HttpStatus.OK)  //TODO : 리턴 추가
    public void updateCategoryName(@PathVariable long categoryId,
                                   @RequestBody @Valid ReqUpdateCategoryNameDTO reqUpdateCategoryNameDTO) {
        categoryService.updateCategoryName(categoryId, reqUpdateCategoryNameDTO);
    }

    @Operation(summary = "카테고리 삭제")
    @DeleteMapping(value = "/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePost(@PathVariable long categoryId) {
        categoryService.deleteCategory(categoryId);
    }


}
