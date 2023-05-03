package io.dkargo.bulletinboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dkargo.bulletinboard.dto.request.post.ReqAddPostDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("게시물 전체 조회 테스트")
    public void findPostByReqGetDTO() throws Exception {
        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("게시물 저장 테스트")
    public void addPost() throws Exception {

//        ReqAddPostDTO reqAddPostDTO = new ReqAddPostDTO();
//        reqAddPostDTO.setTitle();
//        reqAddPostDTO.setContent();
//        reqAddPostDTO.setCategoryId();
//        reqAddPostDTO.setUserId();
//        reqAddPostDTO.setPostOpenUseFlag();
//        reqAddPostDTO.setReplyCommentUseFlag();
//        reqAddPostDTO.setPostPassword();

        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk());
    }

}
