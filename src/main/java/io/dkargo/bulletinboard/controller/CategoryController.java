package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.request.ReqCategoryDTO;
import io.dkargo.bulletinboard.dto.response.ResCategoryDTO;
import io.dkargo.bulletinboard.entity.Category;
import io.dkargo.bulletinboard.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
//@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("")
    public void saveCategory(@RequestBody ReqCategoryDTO reqCategoryDTO) {
        categoryService.saveCategory(reqCategoryDTO);
    }

    @GetMapping("")
    public List<ResCategoryDTO> findAllCategory() {

        List<Category> categories = categoryService.findAllCategory();

        return categories.stream()
                .map(ResCategoryDTO::new)
                .collect(Collectors.toList());
    }

}
