package io.dkargo.bulletinboard;

import io.dkargo.bulletinboard.dto.common.OrderByListEnum;
import io.dkargo.bulletinboard.dto.request.post.ReqAddPostDTO;
import io.dkargo.bulletinboard.dto.request.post.ReqFindOptionPostDTO;
import io.dkargo.bulletinboard.entity.Post;
import io.dkargo.bulletinboard.repository.PostRepository;
import io.dkargo.bulletinboard.service.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.io.IOException;
import java.util.List;

@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("게시물 전체 조회 테스트")
    public void findPostByReqGetDTO() {
        ReqFindOptionPostDTO reqFindOptionPostDTO = new ReqFindOptionPostDTO();
        reqFindOptionPostDTO.setOrderByListEnum(OrderByListEnum.ORDER_BY_POST_ID_DESC);
        reqFindOptionPostDTO.setPage(0);
        reqFindOptionPostDTO.setSize(1);

        postService.findPostByReqGetDTO(reqFindOptionPostDTO);
    }

    @Test
    @DisplayName("게시물 저장 테스트")
    public void addPost() throws IOException {

        ReqAddPostDTO reqAddPostDTO = new ReqAddPostDTO();
        reqAddPostDTO.setTitle("test title");
        reqAddPostDTO.setContent("test content");
        reqAddPostDTO.setCategoryId(4L);
        reqAddPostDTO.setUserId(1L);
        reqAddPostDTO.setPostOpenUseFlag("Y");
        reqAddPostDTO.setReplyCommentUseFlag("Y");
        reqAddPostDTO.setPostPassword("1234");

        postService.addPost(reqAddPostDTO);

        //addPost함수의 리턴이 없으니 게시물 전체 조회해서 가장 마지막에있는것을 가져온다.
        List<Post> postList = postRepository.findAll();
        Post post = postList.get(postList.size() - 1);

        //내가 저장한 게시물 맞는지 확인
        Assertions.assertEquals(reqAddPostDTO.getTitle(), post.getTitle());
        Assertions.assertEquals(reqAddPostDTO.getContent(), post.getContent());
        Assertions.assertEquals(reqAddPostDTO.getUserId(), post.getUser().getId());
        Assertions.assertEquals(reqAddPostDTO.getReplyCommentUseFlag(), post.getReplyCommentUseFlag());
        Assertions.assertEquals(reqAddPostDTO.getPostPassword(), post.getPostPassword());
        Assertions.assertEquals(reqAddPostDTO.getPostOpenUseFlag(), post.getPostOpenUseFlag());
    }

}
