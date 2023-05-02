package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.request.ReqCommentDTO;
import io.dkargo.bulletinboard.dto.request.ReqPostDTO;
import io.dkargo.bulletinboard.dto.request.ReqReplyDTO;
import io.dkargo.bulletinboard.dto.response.ResCommentReplyDTO;
import io.dkargo.bulletinboard.dto.response.ResPostDTO;
import io.dkargo.bulletinboard.dto.response.ResReplyDTO;
import io.dkargo.bulletinboard.entity.Comment;
import io.dkargo.bulletinboard.repository.support.CommentRepositorySupport;
import io.dkargo.bulletinboard.service.CommentService;
import io.dkargo.bulletinboard.service.PostService;
import io.dkargo.bulletinboard.service.ReplyService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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


    @ApiOperation(value = "게시물 memberId로 조회")
    @GetMapping(value = "", params = {"memberId"})
    @ResponseStatus(HttpStatus.OK)
    public List<ResPostDTO> findPostByMemberId(@RequestParam Long memberId, Pageable pageable) {
        return postService.findPostByMemberId(memberId, pageable);
    }

    @ApiOperation(value = "게시물 title 로 조회")
    @GetMapping(value = "", params = {"title"})
    @ResponseStatus(HttpStatus.OK)
    public List<ResPostDTO> findPostByTitle(@RequestParam String title, Pageable pageable) {
        return postService.findPostByTitle(title, pageable);
    }

    @ApiOperation(value = "게시물 content 로 조회")
    @GetMapping(value = "", params = {"content"})
    @ResponseStatus(HttpStatus.OK)
    public List<ResPostDTO> findPostByContent(@RequestParam String content, Pageable pageable) {
        return postService.findPostByContent(content, pageable);
    }

    @ApiOperation(value = "게시물 category 로 조회")
    @GetMapping(value = "", params = {"category"})
    @ResponseStatus(HttpStatus.OK)
    public List<ResPostDTO> findPostByCategory(@RequestParam Long category, Pageable pageable) {
        return postService.findPostByCategory(category, pageable);
    }

    @ApiOperation(value = "게시물 등록")
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void savePost(@ModelAttribute ReqPostDTO reqPostDTO,
                         @RequestPart(required = false) List<MultipartFile> fileList) throws IOException {
        postService.savePost(reqPostDTO, fileList);
    }
}
