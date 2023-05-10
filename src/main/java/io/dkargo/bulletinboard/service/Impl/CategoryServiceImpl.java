package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.dto.request.category.ReqCreateCategoryDTO;
import io.dkargo.bulletinboard.dto.request.category.ReqUpdateCategoryNameDTO;
import io.dkargo.bulletinboard.dto.response.ResCategoryDTO;
import io.dkargo.bulletinboard.entity.Category;
import io.dkargo.bulletinboard.repository.CategoryRepository;
import io.dkargo.bulletinboard.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category createCategory(ReqCreateCategoryDTO reqCreateCategoryDTO) {
        return categoryRepository.save(Category.builder()
                .categoryName(reqCreateCategoryDTO.getCategoryName())
                .parentId(reqCreateCategoryDTO.getParentId())
                .build()
        );
    }

    @Override
    public List<ResCategoryDTO> findCategoryByParentIdOrderByCategoryNameAsc(Integer parentId) {
        return categoryRepository.findCategoriesByParentIdOrderByCategoryNameAsc(parentId)
                .stream()
                .map(ResCategoryDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public void updateCategoryName(Long categoryId, ReqUpdateCategoryNameDTO reqUpdateCategoryNameDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("해당 카테고리는 존재하지 않습니다."));

        category.update(reqUpdateCategoryNameDTO);
    }

    @Override
    public void deleteCategory(long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("해당 카테고리는 존재하지 않습니다."));
        //TODO : 관련 하위 카테고리 전부 삭제
        categoryRepository.delete(category);
    }


}
