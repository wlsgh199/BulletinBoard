package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.dto.request.ReqCategoryDTO;
import io.dkargo.bulletinboard.entity.Category;
import io.dkargo.bulletinboard.repository.CategoryRepository;
import io.dkargo.bulletinboard.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * 카테고리 추가
     *
     * @param reqCategoryDTO 카테고리 정보
     */
    @Override
    public void saveCategory(ReqCategoryDTO reqCategoryDTO) {
        categoryRepository.save(new Category(reqCategoryDTO));
    }


    /**
     * 카테고리 리스트 조회
     *
     * @return 카테고리 리스트
     */
    @Override
    public List<Category> findAllCategory() {
        return categoryRepository.findAll();
    }


}
