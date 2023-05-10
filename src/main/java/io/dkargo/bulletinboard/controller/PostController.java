package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.request.post.*;
import io.dkargo.bulletinboard.dto.response.post.ResFindOptionPostDTO;
import io.dkargo.bulletinboard.dto.response.post.ResFindDetailPostDTO;
import io.dkargo.bulletinboard.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @Operation(summary = "게시물 상세 조회")
    @GetMapping("/details/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public ResFindDetailPostDTO findDetailPostById(@PathVariable Long postId,
                                                   @RequestParam Long userId,
                                                   @RequestParam(required = false) String password) {
        return postService.findDetailPostById(postId, userId, password);
    }

    @Operation(summary = "게시물(옵션별) 전체 조회")
    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public List<ResFindOptionPostDTO> findPostByOption(@ModelAttribute @Valid ReqFindOptionPostDTO reqFindOptionPostDTO) {
        return postService.findPostByFindOptionDTO(reqFindOptionPostDTO);
    }

    @Operation(summary = "게시물 등록")
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createPost(@ModelAttribute @Valid ReqCreatePostDTO reqCreatePostDTO) throws IOException {
        postService.createPost(reqCreatePostDTO);
    }

    @Operation(summary = "게시물 수정")
    @PutMapping(value = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void updatePost(@PathVariable long postId,
                           @ModelAttribute @Valid ReqUpdatePostDTO reqUpdatePostDTO) throws IOException {
        postService.updatePost(postId, reqUpdatePostDTO);
    }

    @Operation(summary = "게시물 삭제")
    @DeleteMapping(value = "{postId}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePost(@PathVariable long postId) {
        postService.deletePost(postId);
    }


}
