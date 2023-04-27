package io.dkargo.bulletinboard.service.Impl;


import io.dkargo.bulletinboard.dto.request.ReqPostDTO;
import io.dkargo.bulletinboard.entity.Category;
import io.dkargo.bulletinboard.entity.Member;
import io.dkargo.bulletinboard.entity.Post;
import io.dkargo.bulletinboard.entity.PostCategory;
import io.dkargo.bulletinboard.repository.CategoryRepository;
import io.dkargo.bulletinboard.repository.MemberRepository;
import io.dkargo.bulletinboard.repository.PostCategoryRepository;
import io.dkargo.bulletinboard.repository.PostRepository;
import io.dkargo.bulletinboard.service.PostService;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostCategoryRepository postCategoryRepository;
    private final CategoryRepository categoryRepository;

    private final MemberRepository memberRepository;

    public PostServiceImpl(PostRepository postRepository,
                           PostCategoryRepository postCategoryRepository,
                           CategoryRepository categoryRepository,
                           MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.postCategoryRepository = postCategoryRepository;
        this.categoryRepository = categoryRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public void savePost(ReqPostDTO reqPostDTO) {

        Member member = memberRepository
                .findById(reqPostDTO.getUserId())
                .orElseThrow(null);

        Post post = new Post(member, reqPostDTO);
        post.setCreateTime(LocalDateTime.now());
        postRepository.save(post);

        List<Category> categoryList = categoryRepository.findAll();
        List<PostCategory> postCategoryList = new ArrayList<>();

        PostCategory postCategory = new PostCategory();
        postCategory.setPostId(post);
        postCategory.setCategoryId(categoryRepository.findById(0L).get());

        PostCategory postCategory2 = new PostCategory();
        postCategory2.setPostId(post);
        postCategory2.setCategoryId(categoryRepository.findById(0L).get());

        PostCategory postCategory3 = new PostCategory();
        postCategory3.setPostId(post);
        postCategory3.setCategoryId(categoryRepository.findById(0L).get());

        postCategoryList.add(postCategory);
        postCategoryList.add(postCategory2);
        postCategoryList.add(postCategory3);
        postCategoryRepository.save(postCategory);
        postCategoryRepository.save(postCategory2);
        postCategoryRepository.save(postCategory3);

//        postCategoryRepository.saveAll(postCategoryList);
//        postCategoryRepository.saveAndFlush(postCategory);
//        postCategoryRepository.save(postCategory);
    }
}
