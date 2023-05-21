package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.dto.request.category.ReqCreateCategoryDTO;
import io.dkargo.bulletinboard.dto.request.category.ReqUpdateCategoryNameDTO;
import io.dkargo.bulletinboard.dto.response.category.ResFindCategoryDTO;
import io.dkargo.bulletinboard.dto.response.category.ResCreateCategoryDTO;
import io.dkargo.bulletinboard.dto.response.category.ResUpdateCategoryDTO;
import io.dkargo.bulletinboard.entity.Category;
import io.dkargo.bulletinboard.entity.PostCategory;
import io.dkargo.bulletinboard.exception.CustomException;
import io.dkargo.bulletinboard.exception.ErrorCodeEnum;
import io.dkargo.bulletinboard.repository.CategoryRepository;
import io.dkargo.bulletinboard.repository.PostCategoryRepository;
import io.dkargo.bulletinboard.repository.support.CategoryRepositorySupport;
import io.dkargo.bulletinboard.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final PostCategoryRepository postCategoryRepository;
    private final CategoryRepositorySupport categoryRepositorySupport;

    @Override
    public ResCreateCategoryDTO createCategory(ReqCreateCategoryDTO reqCreateCategoryDTO) {
        //카테고리 존재여부 체크
        if (categoryRepositorySupport.existCategoryCheck(reqCreateCategoryDTO.getParentId(), reqCreateCategoryDTO.getCategoryName())){
            throw new CustomException(ErrorCodeEnum.DUPLICATE_CATEGORY);
        }

        Category category = categoryRepository.save(
                Category.builder()
                        .categoryName(reqCreateCategoryDTO.getCategoryName())
                        .parentId(reqCreateCategoryDTO.getParentId())
                        .build());

        return new ResCreateCategoryDTO(category);
    }

    @Override
    public List<ResFindCategoryDTO> findCategoryByParentIdOrderByCategoryNameAsc(Long parentId) {
        return categoryRepository.findCategoriesByParentIdOrderByCategoryNameAsc(parentId)
                .stream()
                .map(ResFindCategoryDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public ResUpdateCategoryDTO updateCategoryName(Long categoryId, ReqUpdateCategoryNameDTO reqUpdateCategoryNameDTO) {
        //해당 카테고리가 존재하지 않으면 에러
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCodeEnum.CATEGORY_NOT_FOUND));

        category.updateCategoryName(reqUpdateCategoryNameDTO);
        return new ResUpdateCategoryDTO(category);
    }

    @Override
    public void deleteCategory(long categoryId) {
        //해당 카테고리가 존재하지 않으면 에러
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCodeEnum.CATEGORY_NOT_FOUND));

        PostCategory postCategory = postCategoryRepository.findTopByCategory_Id(category.getId());

        //카테고리가 사용중이면 삭제 불가
        if (postCategory != null) {
            throw new CustomException(ErrorCodeEnum.CATEGORY_IS_USED);
        }

        categoryRepository.deleteById(categoryId);
    }

}
