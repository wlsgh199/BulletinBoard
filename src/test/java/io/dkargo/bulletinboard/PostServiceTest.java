package io.dkargo.bulletinboard;

import io.dkargo.bulletinboard.dto.common.OrderByListEnum;

import io.dkargo.bulletinboard.dto.request.post.*;
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

    @Test
    @DisplayName("게시글 삭제 테스트")
    public void deleteCategoryTest() throws IOException {
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

        ReqDeletePostDTO reqDeletePostDTO = new ReqDeletePostDTO();
        reqDeletePostDTO.setId(post.getId());
        reqDeletePostDTO.setUserId(post.getUser().getId());

        //delete 테스트
        postService.deletePost(reqDeletePostDTO);

        //해당 아이디는 삭제했으니 null
        Assertions.assertNull(postRepository.findById(post.getId()).orElse(null));
    }

    @Test
    @DisplayName("게시글 patch 테스트")
    public void patchCategoryTest() throws IOException {
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

        ReqPatchPostDTO reqPatchPostDTO = new ReqPatchPostDTO();
        reqPatchPostDTO.setId(post.getId());
        reqPatchPostDTO.setUserId(post.getUser().getId());
        reqPatchPostDTO.setPostPassword("4321");

        postService.patchPost(reqPatchPostDTO);

        Post findPost = postRepository.findById(post.getId()).orElse(null);

//        // 비밀번호 1234 -> 4321 으로 변경한것 확인
        Assertions.assertEquals(findPost.getPostPassword(), "4321");
//
//        // 나머지 데이터는 null 으로 안바뀌고 그대로 있는지 확인
        Assertions.assertEquals(post.getTitle(), findPost.getTitle());
        Assertions.assertEquals(post.getPostFileList(), findPost.getPostFileList());
    }

    @Test
    @DisplayName("게시글 put 테스트")
    public void putCategoryTest() throws IOException {
        ReqAddPostDTO reqAddPostDTO = new ReqAddPostDTO();
        reqAddPostDTO.setTitle("put before");
        reqAddPostDTO.setContent("put before");
        reqAddPostDTO.setCategoryId(3L);
        reqAddPostDTO.setUserId(1L);
        reqAddPostDTO.setPostOpenUseFlag("N");
        reqAddPostDTO.setReplyCommentUseFlag("N");
        reqAddPostDTO.setPostPassword("1234");

        postService.addPost(reqAddPostDTO);

        //addPost함수의 리턴이 없으니 게시물 전체 조회해서 가장 마지막에있는것을 가져온다.
        List<Post> postList = postRepository.findAll();
        Post post = postList.get(postList.size() - 1);

        ReqPutPostDTO reqPutPostDTO = new ReqPutPostDTO();
        reqPutPostDTO.setId(post.getId());
        reqPutPostDTO.setTitle("put after");
        reqPutPostDTO.setContent("put after");
        reqPutPostDTO.setCategoryId(3L);
        reqPutPostDTO.setUserId(1L);
        reqPutPostDTO.setPostOpenUseFlag("N");
        reqPutPostDTO.setReplyCommentUseFlag("N");

        postService.putPost(reqPutPostDTO);

        Post findPost = postRepository.findById(post.getId()).orElse(null);

//        // 비밀번호 1234 -> 4321 으로 변경한것 확인
        Assertions.assertEquals(findPost.getTitle(), "put after");
        Assertions.assertEquals(findPost.getContent(), "put after");
        Assertions.assertEquals(findPost.getPostOpenUseFlag(), "N");
        Assertions.assertEquals(findPost.getReplyCommentUseFlag(), "N");

//        // 입력안한값 null 으로 바뀌는지 확인
        Assertions.assertNull(findPost.getPostPassword());
    }

}
