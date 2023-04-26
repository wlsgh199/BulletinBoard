package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.entity.Category;
import io.dkargo.bulletinboard.entity.PostCategory;
import io.dkargo.bulletinboard.repository.PostCategoryRepository;
import io.dkargo.bulletinboard.service.PostCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostCategoryImpl implements PostCategoryService {

    private final PostCategoryRepository postCategoryRepository;

    protected PostCategoryImpl(PostCategoryRepository postCategoryRepository) {
        this.postCategoryRepository = postCategoryRepository;
    }

    @Override
    public void saveAllPostCategory(List<PostCategory> postCategoryList) {

        postCategoryRepository.saveAll(postCategoryList);
//        Category category = new Category();
//        category.setId(1L);
//
////        List<PostCategory> postCategoryList = new ArrayList<>();
//
//        PostCategory postCategory = new PostCategory();
//        postCategory.setPostId(post);
//        postCategory.setCategoryId(category);
////        postCategoryList.add(postCategory);
//        post.getPostCategoryList().add(postCategory);
//        category.getPostCategoryList().add(postCategory);

//        category.setId(2L);
//        postCategory.setCategoryId(category);
//        postCategoryList.add(postCategory);
//        category.setId(4L);
//        postCategory.setCategoryId(category);
//        postCategoryList.add(postCategory);

//        postCategoryRepository.saveAll(postCategoryList);



    }
}
