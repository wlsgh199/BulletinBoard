package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.annotation.CurrentMember;
import io.dkargo.bulletinboard.dto.request.post.*;
import io.dkargo.bulletinboard.dto.response.post.ResCreatePostDTO;
import io.dkargo.bulletinboard.dto.response.post.ResFindOptionPostDTO;
import io.dkargo.bulletinboard.dto.response.post.ResFindDetailPostDTO;
import io.dkargo.bulletinboard.dto.response.post.ResUpdatePostDTO;
import io.dkargo.bulletinboard.entity.Member;
import io.dkargo.bulletinboard.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @Operation(summary = "게시물 상세 조회")
    @GetMapping("/details/{postId}")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResFindDetailPostDTO findDetailPostById(@PathVariable Long postId,
                                                   @RequestParam Long userId,
                                                   @RequestParam(required = false) String password) {
        return postService.findDetailPostById(postId, userId, password);
    }

    @Operation(summary = "게시물(옵션별) 전체 조회")
    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public List<ResFindOptionPostDTO> findPostByOption(@ModelAttribute @Valid ReqFindOptionPostDTO reqFindOptionPostDTO) {
        return postService.findPostByFindOptionDTO(reqFindOptionPostDTO);
    }

    @Operation(summary = "게시물 등록")
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResCreatePostDTO createPost(@ModelAttribute @Valid ReqCreatePostDTO reqCreatePostDTO,
                                       @CurrentMember Member member) throws IOException {
        return postService.createPost(reqCreatePostDTO, member);
    }

    @Operation(summary = "게시물 수정")
    @PutMapping(value = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResUpdatePostDTO updatePost(@PathVariable long postId,
                                       @ModelAttribute @Valid ReqUpdatePostDTO reqUpdatePostDTO,
                                       @CurrentMember Member member) throws IOException {
        return postService.updatePost(postId, reqUpdatePostDTO, member);
    }

    @Operation(summary = "게시물 단건 삭제")
    @DeleteMapping(value = "/{postId}")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public void deletePost(@PathVariable long postId,
                           @CurrentMember Member member) {
        postService.deletePost(postId, member);
    }

    @Operation(summary = "게시물 다건 삭제")
    @DeleteMapping(value = "/")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public void deletePosts(@RequestParam Set<Long> postIds,
                            @CurrentMember Member member) {
        for (Long postId : postIds) {
            postService.deletePost(postId, member);
        }
    }
}
