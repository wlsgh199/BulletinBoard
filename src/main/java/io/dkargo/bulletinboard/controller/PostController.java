package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.request.post.*;
import io.dkargo.bulletinboard.dto.response.post.ResPostDTO;
import io.dkargo.bulletinboard.dto.response.post.ResPostDetailDTO;
import io.dkargo.bulletinboard.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Transactional
public class PostController {
    private final PostService postService;

    @ApiOperation(value = "게시물 상세 조회")
    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public ResPostDetailDTO findPostById(@PathVariable Long postId,
                                         @RequestParam Long userId,
                                         @RequestParam String password) {
        return postService.findDetailPostById(postId, userId, password);
    }

    @ApiOperation(value = "게시물 조건별 조회")
    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public List<ResPostDTO> findPostByReqGetDTO(@ModelAttribute ReqGetDTO reqGetDTO) {
        return postService.findPostByReqGetDTO(reqGetDTO);
    }

    @ApiOperation(value = "게시물 등록")
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void addPost(@ModelAttribute ReqAddPostDTO reqAddPostDTO) throws IOException {
        postService.addPost(reqAddPostDTO);
    }

    @ApiOperation(value = "게시물 부분 수정")
    @PatchMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void patchPost(@ModelAttribute ReqPatchPostDTO reqPatchPostDTO) throws IOException {
        postService.patchPost(reqPatchPostDTO);
    }

    @ApiOperation(value = "게시물 수정")
    @PutMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void putPost(@ModelAttribute ReqPutPostDTO reqPutPostDTO) throws IOException {
        postService.putPost(reqPutPostDTO);
    }

    @ApiOperation(value = "게시물 삭제")
    @DeleteMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public void deletePost(@RequestBody ReqDeletePostDTO reqDeletePostDTO) {
        postService.deletePost(reqDeletePostDTO);
    }


}
