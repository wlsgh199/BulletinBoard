package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.req.ReqCategoryDTO;
import io.dkargo.bulletinboard.dto.res.ResCategoryDTO;
import io.dkargo.bulletinboard.entity.Category;
import io.dkargo.bulletinboard.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/categories")
    public void saveCategory(@RequestBody ReqCategoryDTO reqCategoryDTO) {
        categoryService.saveCategory(reqCategoryDTO);
    }

    @GetMapping("/categories")
    public List<ResCategoryDTO> findAllCategory() {

        List<Category> categories = categoryService.findAllCategory();

        return categories.stream()
                .map(ResCategoryDTO::new)
                .collect(Collectors.toList());
    }

}
