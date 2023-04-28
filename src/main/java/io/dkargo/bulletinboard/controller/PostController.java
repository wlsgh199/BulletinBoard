package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.request.ReqPostDTO;
import io.dkargo.bulletinboard.entity.Member;
import io.dkargo.bulletinboard.entity.Post;
import io.dkargo.bulletinboard.service.MemberService;
import io.dkargo.bulletinboard.service.PostCategoryService;
import io.dkargo.bulletinboard.service.PostService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class PostController {

    private final PostService postService;
    private final PostCategoryService postCategoryService;
    private final MemberService memberService;

    protected PostController(PostService postService,
                             PostCategoryService postCategoryService,
                             MemberService memberService) {
        this.postService = postService;
        this.postCategoryService = postCategoryService;
        this.memberService = memberService;
    }

    @PostMapping(value = "/posts")
    public void savePost(@RequestPart ReqPostDTO reqPostDTO,
                         @RequestPart(required = false) List<MultipartFile> files) {
        //Member 조회
        Member member = memberService.findByIdMember(reqPostDTO.getUserId());
        //게시글 저장
        Post post = postService.savePost(member, reqPostDTO);
        //게시글 * 카테고리 뎁스만큼 저장
        postCategoryService.saveAllPostCategory(post , reqPostDTO.getCategoryId());

    }
}
