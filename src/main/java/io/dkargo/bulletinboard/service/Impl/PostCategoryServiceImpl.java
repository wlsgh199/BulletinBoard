package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.entity.Category;
import io.dkargo.bulletinboard.entity.Post;
import io.dkargo.bulletinboard.entity.PostCategory;
import io.dkargo.bulletinboard.repository.CategoryRepository;
import io.dkargo.bulletinboard.repository.PostCategoryRepository;
import io.dkargo.bulletinboard.service.PostCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostCategoryServiceImpl implements PostCategoryService {

    private final CategoryRepository categoryRepository;
    private final PostCategoryRepository postCategoryRepository;

    @Override
    public void saveAllPostCategory(Post post, Long categoryId) {
        //postCategory 테이블에 게시글 * 카테고리 만큼 저장
        List<PostCategory> postCategoryList = new ArrayList<>();

        Category category = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new RuntimeException("해당 카테고리가 존재하지 않습니다."));

        PostCategory postCategory = PostCategory.builder()
                .post(post)
                .category(category)
                .build();

        postCategoryList.add(postCategory);

        //연관 카테고리 전체 검색
        do {
            category = categoryRepository
                    .findById(category.getParentId())
                    .orElseThrow(NoSuchElementException::new);

            postCategory = PostCategory.builder()
                    .post(post)
                    .category(category)
                    .build();
            postCategoryList.add(postCategory);
        } while (category.getParentId() != null);

        postCategoryRepository.saveAll(postCategoryList);
    }
}
