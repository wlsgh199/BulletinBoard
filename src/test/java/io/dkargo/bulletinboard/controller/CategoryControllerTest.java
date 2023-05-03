package io.dkargo.bulletinboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dkargo.bulletinboard.dto.request.category.ReqAddCategoryDTO;
import io.dkargo.bulletinboard.dto.request.category.ReqDeleteCategoryDTO;
import io.dkargo.bulletinboard.dto.request.category.ReqPatchCategoryDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("카테고리 전체 조회 테스트")
    public void findAllCategoryTest() throws Exception {
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("카테고리 저장 테스트")
    public void saveCategoryTest() throws Exception {
        ReqAddCategoryDTO reqAddCategoryDTO = new ReqAddCategoryDTO();
        reqAddCategoryDTO.setCategoryName("소");
        reqAddCategoryDTO.setDepth(3);
        reqAddCategoryDTO.setParentId(4L);

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reqAddCategoryDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("카테고리 삭제 테스트")
    public void deleteCategoryTest() throws Exception {
        ReqDeleteCategoryDTO reqDeleteCategoryDTO = new ReqDeleteCategoryDTO();
        reqDeleteCategoryDTO.setCategoryId(1L);

        mockMvc.perform(delete("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reqDeleteCategoryDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("카테고리 patch 테스트")
    public void patchCategoryTest() throws Exception {

        ReqPatchCategoryDTO reqPatchCategoryDTO = new ReqPatchCategoryDTO();
        reqPatchCategoryDTO.setCategoryId(3L);
        reqPatchCategoryDTO.setCategoryName("Test");

        objectMapper.writeValueAsString(reqPatchCategoryDTO);

        mockMvc.perform(patch("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reqPatchCategoryDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("카테고리 put 테스트")
    public void putCategoryTest() throws Exception {
        ReqPatchCategoryDTO reqPatchCategoryDTO = new ReqPatchCategoryDTO();
        reqPatchCategoryDTO.setCategoryId(3L);
        reqPatchCategoryDTO.setCategoryName("중");
        reqPatchCategoryDTO.setDepth(2);
        reqPatchCategoryDTO.setParentId(1L);

        mockMvc.perform(put("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reqPatchCategoryDTO)))
                .andExpect(status().isOk());
    }
}
