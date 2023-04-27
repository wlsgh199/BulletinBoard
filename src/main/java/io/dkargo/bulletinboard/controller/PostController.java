package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.request.ReqPostDTO;
import io.dkargo.bulletinboard.service.PostService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    private final PostService postService;

    protected PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/posts")
    public void savePost(@RequestBody ReqPostDTO reqPostDTO) {
        postService.savePost(reqPostDTO);
    }
}
