package io.dkargo.bulletinboard.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    MockMvc mockMvc;

    @InjectMocks
    CategoryController categoryController;

    @Test
    void findCategoryByParentId() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("parentId", "1")
                )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());


//        mockMvc.perform(MockMvcRequestBuilders.post("/categories")
//                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                        .param("id", "")
//                        .param("description", "some string")
//                )
//                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
//                .andExpect(MockMvcResultMatchers.view().name("redirect:/recipe/2/show"));
    }


    @Test
    void createCategory() {
    }

    @Test
    void updateCategoryName() {
    }

    @Test
    void deletePost() {
    }
}