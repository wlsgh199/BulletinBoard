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
import java.util.NoSuchElementException;

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
                .orElseThrow(NoSuchElementException::new);

        //게시글 저장
        Post post = new Post(member, reqPostDTO);
        post.setCreateTime(LocalDateTime.now());
        postRepository.save(post);


        //postCategory 테이블에 게시글 * 카테고리 만큼 저장
        List<PostCategory> postCategoryList = new ArrayList<>();

        Category category = categoryRepository
                .findById(reqPostDTO.getCategoryId())
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
