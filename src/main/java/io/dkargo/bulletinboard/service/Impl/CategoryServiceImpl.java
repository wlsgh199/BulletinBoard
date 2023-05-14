package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.dto.request.category.ReqCreateCategoryDTO;
import io.dkargo.bulletinboard.dto.request.category.ReqUpdateCategoryNameDTO;
import io.dkargo.bulletinboard.dto.response.ResFindCategoryDTO;
import io.dkargo.bulletinboard.dto.response.category.ResCreateCategoryDTO;
import io.dkargo.bulletinboard.dto.response.category.ResUpdateCategoryDTO;
import io.dkargo.bulletinboard.entity.Category;
import io.dkargo.bulletinboard.exception.CustomException;
import io.dkargo.bulletinboard.exception.ErrorCodeEnum;
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
    public ResCreateCategoryDTO createCategory(ReqCreateCategoryDTO reqCreateCategoryDTO) {
        Category category = categoryRepository.save(
                Category.builder()
                        .categoryName(reqCreateCategoryDTO.getCategoryName())
                        .parentId(reqCreateCategoryDTO.getParentId())
                        .build());

        return new ResCreateCategoryDTO(category);
    }

    @Override
    public List<ResFindCategoryDTO> findCategoryByParentIdOrderByCategoryNameAsc(Integer parentId) {
        return categoryRepository.findCategoriesByParentIdOrderByCategoryNameAsc(parentId)
                .stream()
                .map(ResFindCategoryDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public ResUpdateCategoryDTO updateCategoryName(Long categoryId, ReqUpdateCategoryNameDTO reqUpdateCategoryNameDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCodeEnum.CATEGORY_NOT_FOUND));

        category.update(reqUpdateCategoryNameDTO);
        return new ResUpdateCategoryDTO(category);
    }

    @Override
    public void deleteCategory(long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCodeEnum.CATEGORY_NOT_FOUND));
        //TODO : 관련 하위 카테고리 전부 삭제
        categoryRepository.delete(category);
    }


}
