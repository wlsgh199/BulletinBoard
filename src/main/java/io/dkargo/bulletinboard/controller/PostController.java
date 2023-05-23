package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.annotation.CurrentMember;
import io.dkargo.bulletinboard.dto.request.post.*;
import io.dkargo.bulletinboard.dto.response.post.ResCreatePostDTO;
import io.dkargo.bulletinboard.dto.response.post.ResFindOptionPostDTO;
import io.dkargo.bulletinboard.dto.response.post.ResFindDetailPostDTO;
import io.dkargo.bulletinboard.dto.response.post.ResUpdatePostDTO;
import io.dkargo.bulletinboard.jwt.MemberDetailsDTO;
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
                                                   @CurrentMember MemberDetailsDTO memberDetailsDTO,
                                                   @RequestParam(required = false) String password) {
        return postService.findDetailPostById(postId, memberDetailsDTO.getUsername(), password);
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
                                       @CurrentMember MemberDetailsDTO memberDetailsDTO) throws IOException {
        return postService.createPost(reqCreatePostDTO, memberDetailsDTO.getUsername());
    }

    @Operation(summary = "게시물 수정")
    @PutMapping(value = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResUpdatePostDTO updatePost(@PathVariable long postId,
                                       @ModelAttribute @Valid ReqUpdatePostDTO reqUpdatePostDTO,
                                       @CurrentMember MemberDetailsDTO memberDetailsDTO) throws IOException {
        return postService.updatePost(postId, reqUpdatePostDTO, memberDetailsDTO.getUsername());
    }

    @Operation(summary = "게시물 단건 삭제")
    @DeleteMapping(value = "/{postId}")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public void deletePost(@PathVariable long postId,
                           @CurrentMember MemberDetailsDTO memberDetailsDTO) {
        postService.deletePost(postId, memberDetailsDTO.getUsername());
    }

    @Operation(summary = "게시물 다건 삭제")
    @DeleteMapping(value = "/")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public void deletePosts(@RequestParam Set<Long> postIds,
                            @CurrentMember MemberDetailsDTO memberDetailsDTO) {
        for (Long postId : postIds) {
            postService.deletePost(postId, memberDetailsDTO.getUsername());
        }
    }
}
