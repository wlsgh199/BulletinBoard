package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.request.ReqPostDTO;
import io.dkargo.bulletinboard.entity.Member;
import io.dkargo.bulletinboard.entity.Post;
import io.dkargo.bulletinboard.service.FileService;
import io.dkargo.bulletinboard.service.MemberService;
import io.dkargo.bulletinboard.service.PostCategoryService;
import io.dkargo.bulletinboard.service.PostService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostCategoryService postCategoryService;
    private final MemberService memberService;
    private final FileService fileService;


    @ApiOperation(value = "게시물 등록")
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void savePost(@RequestPart(value = "reqPostDTO") ReqPostDTO reqPostDTO,
                         @RequestPart(value = "fileList", required = false) List<MultipartFile> fileList) throws IOException {
        //Member 조회
        Member member = memberService.findMemberById(reqPostDTO.getUserId());

        //게시글 저장
        Post post = postService.savePost(member, reqPostDTO);

        //게시글 * 카테고리 뎁스만큼 저장
        postCategoryService.saveAllPostCategory(post, reqPostDTO.getCategoryId());

        //파일 있을시 저장
        int fileCount = fileList.size();

        if (fileCount > 0) {
            //파일 개수 3개 이상 x
            if (fileCount < 3) {
                // error
            }
            fileService.saveAllFile(post, fileList);
        }
    }
}
