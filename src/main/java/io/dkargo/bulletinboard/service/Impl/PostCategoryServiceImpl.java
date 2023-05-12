package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.entity.Category;
import io.dkargo.bulletinboard.entity.Post;
import io.dkargo.bulletinboard.entity.PostCategory;
import io.dkargo.bulletinboard.exception.CustomException;
import io.dkargo.bulletinboard.exception.ErrorCode;
import io.dkargo.bulletinboard.repository.CategoryRepository;
import io.dkargo.bulletinboard.repository.PostCategoryRepository;
import io.dkargo.bulletinboard.service.PostCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class PostCategoryServiceImpl implements PostCategoryService {

    private final CategoryRepository categoryRepository;
    private final PostCategoryRepository postCategoryRepository;

    @Override
    public void saveAllPostCategory(Post post, long categoryId) {
        //postCategory 테이블에 게시글 * 카테고리 만큼 저장
        List<PostCategory> postCategoryList = new ArrayList<>();

        Category category = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        //첫번째 postCategory 생성
        PostCategory postCategory = PostCategory.builder()
                .post(post)
                .category(category)
                .build();

        postCategoryList.add(postCategory);

        //연관 카테고리 전체 검색
        // 부모 id 가 Null 일때까지 조회
        do {
            category = categoryRepository
                    .findById(Long.valueOf(category.getParentId()))
                    .orElseThrow(NoSuchElementException::new);

            postCategory = PostCategory.builder()
                    .post(post)
                    .category(category)
                    .build();
            postCategoryList.add(postCategory);

        } while (category.getParentId() != null);
        //게시물 * 카테고리만큼 저장
        postCategoryRepository.saveAll(postCategoryList);
    }
}
