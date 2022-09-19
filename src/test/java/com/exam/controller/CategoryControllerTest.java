package com.exam.controller;

import com.exam.model.exam.Category;
import com.exam.service.impl.CategoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class CategoryControllerTest {
    @MockBean
    CategoryServiceImpl categoryService;

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mvc;
    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @DisplayName("get all categories")
    void getCategoriesTest() throws Exception
    {
        Set<Category> catList = new HashSet<>();
        Category catOne = new Category();
        Category catTwo = new Category();
        Category catThree = new Category();
        catList.add(catOne);
        catList.add(catTwo);
        catList.add(catThree);
        when(categoryService.getCategories()).thenReturn(catList);
        mvc.perform( MockMvcRequestBuilders
                        .get("/category/")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].cid").exists());

    }
    @Test
    @DisplayName("get category by id")
    void getCategoryTest() throws Exception
    {
        Category category = new Category();
        category.setCid(99L);
        category.setTitle("GK");
        category.setDescription("General knowledge category");

        when(categoryService.getCategory(99L)).thenReturn(category);
        mvc.perform( MockMvcRequestBuilders
                        .get("/category/{categoryId}", 99L)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());


    }
    @Test
    @DisplayName("add new category")
    void addCategoryTest() throws Exception {
        Category category = new Category();
        category.setTitle("DBMS");
        category.setDescription("DBMS Category");

        when(categoryService.addCategory(category)).thenReturn(category);
        mvc.perform( MockMvcRequestBuilders
                        .post("/category/")
                        .content(asJsonString(category))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }
    @Test
    @DisplayName("update category")
    void updateCategoryTest() throws Exception {
        Category category = new Category();
        category.setTitle("Java");
        category.setDescription("Java Category");

        when(categoryService.updateCategory(category)).thenReturn(category);

        mvc.perform( MockMvcRequestBuilders
                        .put("/category/")
                        .content(asJsonString(category))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
    @Test
    @DisplayName("delete category")
    void deleteCategoryTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .delete("/category/{categoryId}",999L))
                .andExpect(status().isOk());
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
