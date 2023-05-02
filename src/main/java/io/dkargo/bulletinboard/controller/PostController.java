package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.request.post.ReqDeletePostDTO;
import io.dkargo.bulletinboard.dto.request.post.ReqPatchPostDTO;
import io.dkargo.bulletinboard.dto.request.post.ReqAddPostDTO;
import io.dkargo.bulletinboard.dto.request.post.ReqPutPostDTO;
import io.dkargo.bulletinboard.dto.response.post.ResPostDTO;
import io.dkargo.bulletinboard.dto.response.post.ResPostDetailDTO;
import io.dkargo.bulletinboard.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Transactional
public class PostController {
    private final PostService postService;

    @ApiOperation(value = "게시물 전체 조회")
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<ResPostDTO> findAllPost(Pageable pageable) {
        return postService.findAllPost(pageable);
    }

    @ApiOperation(value = "게시물 상세 조회")
    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public ResPostDetailDTO findPostById(@PathVariable Long postId,
                                         @RequestParam Long userId,
                                         @RequestParam String password) {
        return postService.findDetailPostById(postId, userId, password);
    }


    @ApiOperation(value = "게시물 memberId로 조회")
    @GetMapping(value = "", params = {"userId"})
    @ResponseStatus(HttpStatus.OK)
    public List<ResPostDTO> findPostByMemberId(@RequestParam Long userId, Pageable pageable) {
        return postService.findPostByMemberId(userId, pageable);
    }

    @ApiOperation(value = "게시물 title 로 조회")
    @GetMapping(value = "", params = {"title"})
    @ResponseStatus(HttpStatus.OK)
    public List<ResPostDTO> findPostByTitle(@RequestParam String title, Pageable pageable) {
        return postService.findPostByTitle(title, pageable);
    }

    @ApiOperation(value = "게시물 content 로 조회")
    @GetMapping(value = "", params = "content")
    @ResponseStatus(HttpStatus.OK)
    public List<ResPostDTO> findPostByContent(@RequestParam String content, Pageable pageable) {
        return postService.findPostByContent(content, pageable);
    }

    @ApiOperation(value = "게시물 category 로 조회")
    @GetMapping(value = "", params = "category")
    @ResponseStatus(HttpStatus.OK)
    public List<ResPostDTO> findPostByCategory(@RequestParam Long category, Pageable pageable) {
        return postService.findPostByCategory(category, pageable);
    }

    @ApiOperation(value = "게시물 등록")
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void savePost(@ModelAttribute ReqAddPostDTO reqAddPostDTO,
                         @RequestPart(required = false) List<MultipartFile> fileList) throws IOException {

        postService.savePost(reqAddPostDTO, fileList);
    }

    @ApiOperation(value = "게시물 부분 수정")
    @PatchMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void patchPost(@ModelAttribute ReqPatchPostDTO reqPatchPostDTO,
                          @RequestPart(required = false) List<MultipartFile> fileList) throws IOException {
        postService.patchPost(reqPatchPostDTO, fileList);
    }

    @ApiOperation(value = "게시물 수정")
    @PutMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void putPost(@ModelAttribute ReqPutPostDTO reqPutPostDTO,
                          @RequestPart(required = false) List<MultipartFile> fileList) throws IOException {
        postService.putPost(reqPutPostDTO, fileList);
    }

    @ApiOperation(value = "게시물 삭제")
    @DeleteMapping(value = "")
    public void deletePost(@RequestBody ReqDeletePostDTO reqDeletePostDTO) {
        postService.deletePost(reqDeletePostDTO);
    }


}
