package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.request.post.*;
import io.dkargo.bulletinboard.dto.response.post.ResFindOptionPostDTO;
import io.dkargo.bulletinboard.dto.response.post.ResFindDetailPostDTO;
import io.dkargo.bulletinboard.service.PostService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/posts")
@RequiredArgsConstructor
@Transactional
public class PostController {
    private final PostService postService;

    @Operation(summary = "게시물 상세 조회")
    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public ResFindDetailPostDTO findDetailPostById(@PathVariable Long postId,
                                                   @RequestParam Long userId,
                                                   @RequestParam String password) {
        return postService.findDetailPostById(postId, userId, password);
    }

    @Operation(summary = "게시물 옵션별 조회")
    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public List<ResFindOptionPostDTO> findPostByOption(@ModelAttribute @Valid ReqFindOptionPostDTO reqFindOptionPostDTO) {
        return postService.findPostByFindOptionDTO(reqFindOptionPostDTO);
    }

    @Operation(summary = "게시물 등록")
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void addPost(@ModelAttribute @Valid ReqAddPostDTO reqAddPostDTO) throws IOException {
        postService.addPost(reqAddPostDTO);
    }

    @Operation(summary = "게시물 부분 수정")
    @PatchMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void patchPost(@ModelAttribute @Valid ReqPatchPostDTO reqPatchPostDTO) throws IOException {
        postService.patchPost(reqPatchPostDTO);
    }

    @Operation(summary = "게시물 수정")
    @PutMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void putPost(@ModelAttribute @Valid ReqPutPostDTO reqPutPostDTO) throws IOException {
        postService.putPost(reqPutPostDTO);
    }

    @Operation(summary = "게시물 삭제")
    @DeleteMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public void deletePost(@RequestBody @Valid ReqDeletePostDTO reqDeletePostDTO) {
        postService.deletePost(reqDeletePostDTO);
    }


}
