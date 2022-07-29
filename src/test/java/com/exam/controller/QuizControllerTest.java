package com.exam.controller;

import com.exam.model.User;
import com.exam.model.exam.Category;
import com.exam.model.exam.Quiz;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class QuizControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @DisplayName("Should get all the quizzes")
    public void getQuizzesTest() throws Exception
    {
        mvc.perform( MockMvcRequestBuilders
                        .get("/quiz/")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].qid").exists());

    }
    @Test
    @DisplayName("Should get the user by quiz id")
    public void getQuizTest() throws Exception
    {
        mvc.perform( MockMvcRequestBuilders
                        .get("/quiz/{quizId}", 91)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("ER Model"));
    }
    @Test
    public void addQuizTest() throws Exception
    {
        Quiz quiz = new Quiz();
        quiz.setQid(78l);
        quiz.setTitle("Spring");
        quiz.setDescription("This is spring quiz");
        quiz.setMaxMarks("10");
        quiz.setNumberOfQuestions("5");





        mvc.perform( MockMvcRequestBuilders
                        .post("/quiz/")
                        .content(asJsonString(quiz))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
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
