package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.dto.request.category.ReqAddCategoryDTO;
import io.dkargo.bulletinboard.dto.request.category.ReqDeleteCategoryDTO;
import io.dkargo.bulletinboard.dto.request.category.ReqPatchCategoryDTO;
import io.dkargo.bulletinboard.dto.request.category.ReqPutCategoryDTO;
import io.dkargo.bulletinboard.dto.response.ResCategoryDTO;
import io.dkargo.bulletinboard.entity.Category;
import io.dkargo.bulletinboard.repository.CategoryRepository;
import io.dkargo.bulletinboard.repository.support.CategoryRepositorySupport;
import io.dkargo.bulletinboard.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryRepositorySupport categoryRepositorySupport;

    @Override
    public Category addCategory(ReqAddCategoryDTO reqAddCategoryDTO) {
        return categoryRepository.save(Category.builder()
                .categoryName(reqAddCategoryDTO.getCategoryName())
                .parentId(reqAddCategoryDTO.getParentId())
                .build()
        );
    }

    @Override
    public void putCategory(ReqPutCategoryDTO reqPutCategoryDTO) {
        Category category = categoryRepository.findById(reqPutCategoryDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("해당 카테고리는 존재하지 않습니다."));

        category.put(reqPutCategoryDTO);
        categoryRepository.save(category);
    }

    @Override
    public void patchCategory(ReqPatchCategoryDTO reqPatchCategoryDTO) {
        Category category = categoryRepository.findById(reqPatchCategoryDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("해당 카테고리는 존재하지 않습니다."));

        //카테고리 이름 수정
        if (StringUtils.isBlank(reqPatchCategoryDTO.getCategoryName())) {
            reqPatchCategoryDTO.setCategoryName(category.getCategoryName());
        }

        //카테고리 부모 아이디 수정
        if (reqPatchCategoryDTO.getParentId() == null) {
            reqPatchCategoryDTO.setParentId(category.getParentId());
        }

        category.patch(reqPatchCategoryDTO);
        categoryRepository.save(category); //TODO : save 제거
    }

    @Override
    public void deleteCategory(ReqDeleteCategoryDTO reqDeleteCategoryDTO) {
        Category category = categoryRepository.findById(reqDeleteCategoryDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("해당 카테고리는 존재하지 않습니다."));

        categoryRepository.delete(category);
    }

    //TODO : depth로 조회
    @Override
    public List<ResCategoryDTO> findAllCategory() {
        return categoryRepositorySupport.findAllCategory()
                .stream()
                .map(ResCategoryDTO::new)
                .collect(Collectors.toList());
    }


}
