package com.exam.controller;


import com.exam.model.exam.Quiz;
import com.exam.service.impl.QuizServiceImpl;
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
class QuizControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;
    @MockBean
    private QuizServiceImpl quizService;

    @BeforeEach
    public void setUp(){
        this.mvc = MockMvcBuilders.webAppContextSetup(context).build();

    }

    @Test
    @DisplayName("get all the quizzes")
    void getQuizzesTest() throws Exception
    {
        Set<Quiz> quizList = new HashSet<>();
        Quiz quizOne = new Quiz();
        Quiz quizTwo = new Quiz();
        Quiz quizThree = new Quiz();
        quizList.add(quizOne);
        quizList.add(quizTwo);
        quizList.add(quizThree);
        when(quizService.getQuizzes()).thenReturn(quizList);
        mvc.perform( MockMvcRequestBuilders
                        .get("/quiz/")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].qid").exists());

    }
    @Test
    @DisplayName("add new quiz")
    void addQuizTest() throws Exception
    {
        Quiz quiz = new Quiz();

        quiz.setTitle("Spring");
        quiz.setDescription("This is spring quiz");
        quiz.setMaxMarks("10");
        quiz.setNumberOfQuestions("5");

        when(quizService.addQuiz(quiz)).thenReturn(quiz);
      mvc.perform( MockMvcRequestBuilders
                        .post("/quiz/")
                        .content(asJsonString(quiz))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("update quiz")
    void updateQuizTest() throws Exception
    {
        Quiz quiz = new Quiz();
        quiz.setTitle("Maven");
        quiz.setDescription("This is maven quiz");
        quiz.setMaxMarks("10");
        quiz.setNumberOfQuestions("5");

        when(quizService.updateQuiz(quiz)).thenReturn(quiz);
        mvc.perform( MockMvcRequestBuilders
                        .put("/quiz/")
                        .content(asJsonString(quiz))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }
    @Test
    @DisplayName("get quiz by Id")
    void getQuizTest() throws Exception
    {
        Quiz quiz = new Quiz();
        quiz.setTitle("Hibernate");
        quiz.setDescription("This is Hibernate quiz");
        quiz.setMaxMarks("10");
        quiz.setNumberOfQuestions("5");
        when(quizService.getQuiz(99L)).thenReturn(quiz);
        mvc.perform( MockMvcRequestBuilders
                        .get("/quiz/{quizId}", 99L)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").isNotEmpty());
    }
    @Test
    @DisplayName("delete quiz by Id")
    void deleteQuizTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .delete("/quiz/{quizId}",999L))
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
