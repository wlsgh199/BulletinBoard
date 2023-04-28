package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.dto.request.ReqPostDTO;
import io.dkargo.bulletinboard.entity.Category;
import io.dkargo.bulletinboard.entity.Post;
import io.dkargo.bulletinboard.entity.PostCategory;
import io.dkargo.bulletinboard.repository.CategoryRepository;
import io.dkargo.bulletinboard.repository.PostCategoryRepository;
import io.dkargo.bulletinboard.service.PostCategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PostCategoryServiceImpl implements PostCategoryService {

    CategoryRepository categoryRepository;
    PostCategoryRepository postCategoryRepository;

    public PostCategoryServiceImpl (CategoryRepository categoryRepository,
                                    PostCategoryRepository postCategoryRepository) {
        this.categoryRepository = categoryRepository;
        this.postCategoryRepository = postCategoryRepository;
    }

    @Override
    public void saveAllPostCategory(Post post, Long categoryId) {
        //postCategory 테이블에 게시글 * 카테고리 만큼 저장
        List<PostCategory> postCategoryList = new ArrayList<>();

        Category category = categoryRepository
                .findById(categoryId)
                .orElseThrow(NoSuchElementException::new);

        PostCategory postCategory = new PostCategory(post, category);
        postCategoryList.add(postCategory);

        //연관 카테고리 전체 검색
        do {
            category = categoryRepository
                    .findById(category.getParentId())
                    .orElseThrow(NoSuchElementException::new);
            postCategory = new PostCategory(post, category);
            postCategoryList.add(postCategory);
        } while (!category.getParentId().equals(0L));

        postCategoryRepository.saveAll(postCategoryList);
    }
}
