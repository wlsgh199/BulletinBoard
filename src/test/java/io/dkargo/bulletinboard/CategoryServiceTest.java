package io.dkargo.bulletinboard;

import io.dkargo.bulletinboard.dto.request.category.ReqAddCategoryDTO;
import io.dkargo.bulletinboard.dto.request.category.ReqDeleteCategoryDTO;
import io.dkargo.bulletinboard.dto.request.category.ReqPatchCategoryDTO;
import io.dkargo.bulletinboard.dto.request.category.ReqPutCategoryDTO;
import io.dkargo.bulletinboard.dto.response.ResCategoryDTO;
import io.dkargo.bulletinboard.entity.Category;
import io.dkargo.bulletinboard.repository.CategoryRepository;
import io.dkargo.bulletinboard.repository.support.CategoryRepositorySupport;
import io.dkargo.bulletinboard.service.CategoryService;
import io.dkargo.bulletinboard.service.Impl.CategoryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    //TODO : MockMVC 으로 컨트롤러 테스트
    //TODO : 성공테스트, 실패테스트 하기
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryRepositorySupport categoryRepositorySupport;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("카테고리 전체 조회 테스트")
    public void findAllCategoryTest() {
        //given
        List<Category> categoryList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            categoryList.add(new Category(1L, "대"));
        }

        given(categoryRepositorySupport.findAllCategory()).willReturn(categoryList);

        //when
        List<Category> findCategories = categoryRepositorySupport.findAllCategory();

        //then
        assertThat(findCategories.size()).isEqualTo(5);
    }

    @Test
    @DisplayName("카테고리 저장 테스트")
    public void addCategoryTest() {
        //given
        //저장할 카테고리 dto 생성
        ReqAddCategoryDTO reqAddCategoryDTO = new ReqAddCategoryDTO();
        reqAddCategoryDTO.setCategoryName("소");
        reqAddCategoryDTO.setParentId(50L);

        doReturn(new Category(reqAddCategoryDTO.getParentId(), reqAddCategoryDTO.getCategoryName()))
                .when(categoryRepository)
                .save(any(Category.class));

        //when
        //카테고리 저장
        Category category = categoryService.addCategory(reqAddCategoryDTO);

        //then
        //내가 저장한 카테고리가 맞는지 확인
        assertThat(category.getCategoryName()).isEqualTo(reqAddCategoryDTO.getCategoryName());
//        assertThat(category.getDepth()).isEqualTo(reqAddCategoryDTO.getDepth());
        assertThat(category.getParentId()).isEqualTo(reqAddCategoryDTO.getParentId());
    }

    @Test
    @DisplayName("카테고리 삭제 테스트")
    public void deleteCategoryTest() {
        //given
        Category category = Mockito.mock(Category.class);

        //delete dto 생성
        ReqDeleteCategoryDTO reqDeleteCategoryDTO = new ReqDeleteCategoryDTO();
        reqDeleteCategoryDTO.setCategoryId(category.getId());

        given(categoryRepository.findById(0L)).willReturn(Optional.of(category));

        //when
//        when(categoryRepository.findById(0L))
//                .thenReturn(Optional.of(category));
        categoryService.deleteCategory(reqDeleteCategoryDTO);

        //then
        verify(categoryRepository, times(1)).delete(category);
    }

    @Test
    @DisplayName("카테고리 patch 테스트")
    public void patchCategoryTest() {
        //given
        Category category = Category.builder()
                .parentId(1L)
                .categoryName("대")
                .build();

        given(categoryRepository.findById(0L)).willReturn(Optional.of(category));

        ReqPatchCategoryDTO reqPatchCategoryDTO = new ReqPatchCategoryDTO();
        reqPatchCategoryDTO.setCategoryId(0L);
        reqPatchCategoryDTO.setCategoryName("TTT");

        //when
        categoryService.patchCategory(reqPatchCategoryDTO);

        //then
        Category findCategory = categoryRepository.findById(0L).orElseThrow();
        assertThat(category.getCategoryName()).isEqualTo(findCategory.getCategoryName());
//        assertThat(category.getDepth()).isEqualTo(findCategory.getDepth());
        assertThat(category.getParentId()).isEqualTo(findCategory.getParentId());
    }

    @Test
    @DisplayName("카테고리 put 테스트")
    public void putCategoryTest() {
        //given
        Category category = Category.builder()
                .categoryName("소")
//                .depth(40)
                .parentId(50L)
                .build();

        given(categoryRepository.findById(0L)).willReturn(Optional.of(category));

        ReqPutCategoryDTO reqPutCategoryDTO = new ReqPutCategoryDTO();
        reqPutCategoryDTO.setCategoryId(0L);
        reqPutCategoryDTO.setCategoryName("중");
        reqPutCategoryDTO.setParentId(1L);

        //when
        categoryService.putCategory(reqPutCategoryDTO);

        //then
        Category findCategory = categoryRepository.findById(0L).orElseThrow();
        assertThat(category.getCategoryName()).isEqualTo(findCategory.getCategoryName());
//        assertThat(category.getDepth()).isEqualTo(findCategory.getDepth());
        assertThat(category.getParentId()).isEqualTo(findCategory.getParentId());
//        assertThat(findCategory.getDepth()).isEqualTo(0);
    }
}
