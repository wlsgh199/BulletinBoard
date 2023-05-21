package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.request.category.ReqCreateCategoryDTO;
import io.dkargo.bulletinboard.dto.request.category.ReqUpdateCategoryNameDTO;
import io.dkargo.bulletinboard.dto.response.category.ResCreateCategoryDTO;
import io.dkargo.bulletinboard.dto.response.category.ResFindCategoryDTO;
import io.dkargo.bulletinboard.dto.response.category.ResUpdateCategoryDTO;
import io.dkargo.bulletinboard.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
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
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public List<ResFindCategoryDTO> findCategoryByParentId(@RequestParam(required = false) Long parentId) {
        return categoryService.findCategoryByParentIdOrderByCategoryNameAsc(parentId);
    }

    @Operation(summary = "카테고리 추가")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @Secured("ROLE_ADMIN")
    public ResCreateCategoryDTO createCategory(@RequestBody @Valid ReqCreateCategoryDTO reqCreateCategoryDTO) {
        return categoryService.createCategory(reqCreateCategoryDTO);
    }

    @Operation(summary = "카테고리 이름 수정")
    @PatchMapping(value = "/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_ADMIN")
    public ResUpdateCategoryDTO updateCategoryName(@PathVariable long categoryId,
                                                   @RequestBody @Valid ReqUpdateCategoryNameDTO reqUpdateCategoryNameDTO) {
        return categoryService.updateCategoryName(categoryId, reqUpdateCategoryNameDTO);
    }

    @Operation(summary = "카테고리 삭제")
    @DeleteMapping(value = "/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_ADMIN")
    public void deletePost(@PathVariable long categoryId) {
        categoryService.deleteCategory(categoryId);
    }


}
