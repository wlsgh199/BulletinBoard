package io.dkargo.bulletinboard;

import io.dkargo.bulletinboard.dto.request.category.ReqAddCategoryDTO;
import io.dkargo.bulletinboard.dto.request.category.ReqDeleteCategoryDTO;
import io.dkargo.bulletinboard.dto.request.category.ReqPatchCategoryDTO;
import io.dkargo.bulletinboard.dto.request.category.ReqPutCategoryDTO;
import io.dkargo.bulletinboard.entity.Category;
import io.dkargo.bulletinboard.repository.CategoryRepository;
import io.dkargo.bulletinboard.service.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class CategoryServiceTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Test
    @DisplayName("카테고리 전체 조회 테스트")
    public void findAllCategoryTest() {
        categoryService.findAllCategory();
    }

    @Test
    @DisplayName("카테고리 저장 테스트")
    public void addCategoryTest() {
        //저장할 카테고리 dto 생성
        ReqAddCategoryDTO reqAddCategoryDTO = new ReqAddCategoryDTO();
        reqAddCategoryDTO.setCategoryName("소");
        reqAddCategoryDTO.setDepth(40);
        reqAddCategoryDTO.setParentId(50L);
        //카테고리 저장
        categoryService.addCategory(reqAddCategoryDTO);

        //addCategory함수의 리턴이 없으니 카테고리 전체 조회해서 가장 마지막에있는것을 가져온다.
        List<Category> categoryList = categoryRepository.findAll();
        Category category = categoryList.get(categoryList.size() - 1);

        //내가 저장한 카테고리가 맞는지 확인
        Assertions.assertEquals(reqAddCategoryDTO.getCategoryName(), category.getCategoryName());
        Assertions.assertEquals(reqAddCategoryDTO.getDepth(), category.getDepth());
        Assertions.assertEquals(reqAddCategoryDTO.getParentId(), category.getParentId());
    }

    @Test
    @DisplayName("카테고리 삭제 테스트")
    public void deleteCategoryTest() {
        Category category = Category.builder()
                .categoryName("소")
                .depth(40)
                .parentId(50L)
                .build();

        Category resultCategory = categoryRepository.save(category);

        //delete dto 생성
        ReqDeleteCategoryDTO reqDeleteCategoryDTO = new ReqDeleteCategoryDTO();
        reqDeleteCategoryDTO.setCategoryId(resultCategory.getId());

        //delete 테스트
        categoryService.deleteCategory(reqDeleteCategoryDTO);

        //해당 아이디는 삭제했으니 null
        Assertions.assertNull(categoryRepository.findById(resultCategory.getId()).orElse(null));
    }

    @Test
    @DisplayName("카테고리 patch 테스트")
    public void patchCategoryTest() {
        Category category = Category.builder()
                .categoryName("소")
                .depth(40)
                .parentId(50L)
                .build();

        Category resultCategory = categoryRepository.save(category);

        ReqPatchCategoryDTO reqPatchCategoryDTO = new ReqPatchCategoryDTO();
        reqPatchCategoryDTO.setCategoryId(resultCategory.getId());
        reqPatchCategoryDTO.setCategoryName("소소소");

        categoryService.patchCategory(reqPatchCategoryDTO);

        Category findCategory = categoryRepository.findById(category.getId()).orElse(null);

        //카테고리 null 확인
        Assertions.assertNotNull(findCategory);
        // 소 -> 소소소 로 변경한것 확인
        Assertions.assertEquals(findCategory.getCategoryName(), reqPatchCategoryDTO.getCategoryName());

        // 나머지 데이터는 null 으로 안바뀌고 그대로 있는지 확인
        Assertions.assertEquals(category.getDepth(), findCategory.getDepth());
        Assertions.assertEquals(category.getParentId(), findCategory.getParentId());
    }

    @Test
    @DisplayName("카테고리 put 테스트")
    public void putCategoryTest() {
        Category category = Category.builder()
                .categoryName("소")
                .depth(40)
                .parentId(50L)
                .build();

        Category resultCategory = categoryRepository.save(category);

        //put 실행
        ReqPutCategoryDTO reqPutCategoryDTO = new ReqPutCategoryDTO();
        reqPutCategoryDTO.setCategoryId(resultCategory.getId());
        reqPutCategoryDTO.setCategoryName("중");
        reqPutCategoryDTO.setParentId(1L);
        categoryService.putCategory(reqPutCategoryDTO);

        Category findCategory = categoryRepository.findById(category.getId()).orElse(null);

        // 카테고리 null 확인
        Assertions.assertNotNull(findCategory);

        // 제대로 변경되었는지 확인
        Assertions.assertEquals(findCategory.getCategoryName(), reqPutCategoryDTO.getCategoryName());
        Assertions.assertEquals(findCategory.getParentId(), reqPutCategoryDTO.getParentId());

        //depth 데이터는 입력안했으니 null 으로 바뀌는지 확인
        Assertions.assertNull(findCategory.getDepth());
    }
}
