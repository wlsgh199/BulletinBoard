package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.dto.request.category.ReqAddCategoryDTO;
import io.dkargo.bulletinboard.dto.request.category.ReqDeleteCategoryDTO;
import io.dkargo.bulletinboard.dto.request.category.ReqPatchCategoryDTO;
import io.dkargo.bulletinboard.dto.request.category.ReqPutCategoryDTO;
import io.dkargo.bulletinboard.dto.response.ResCategoryDTO;
import io.dkargo.bulletinboard.entity.Category;
import io.dkargo.bulletinboard.entity.Post;
import io.dkargo.bulletinboard.repository.CategoryRepository;
import io.dkargo.bulletinboard.repository.support.CategoryRepositorySupport;
import io.dkargo.bulletinboard.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryRepositorySupport categoryRepositorySupport;

    @Override
    public Category addCategory(ReqAddCategoryDTO reqAddCategoryDTO) {
        return categoryRepository.save(Category.builder()
                .categoryName(reqAddCategoryDTO.getCategoryName())
                .depth(reqAddCategoryDTO.getDepth())
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
        if (reqPatchCategoryDTO.getCategoryName() == null) {
            reqPatchCategoryDTO.setCategoryName(category.getCategoryName());
        }

        //카테고리 부모 아이디 수정
        if (reqPatchCategoryDTO.getParentId() == null) {
            reqPatchCategoryDTO.setParentId(category.getParentId());
        }

        //카테고리 뎁스 수정
        if (reqPatchCategoryDTO.getDepth() == null) {
            reqPatchCategoryDTO.setDepth(category.getDepth());
        }

        category.patch(reqPatchCategoryDTO);
        categoryRepository.save(category);

    }

    @Override
    public void deleteCategory(ReqDeleteCategoryDTO reqDeleteCategoryDTO) {
        Category category = categoryRepository.findById(reqDeleteCategoryDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("해당 카테고리는 존재하지 않습니다."));

        categoryRepository.delete(category);
    }

    @Override
    public List<ResCategoryDTO> findAllCategory() {
        return categoryRepositorySupport.findAllCategory()
                .stream()
                .map(ResCategoryDTO::new)
                .collect(Collectors.toList());
    }


}
