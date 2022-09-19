package com.exam.controller;

import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;
import com.exam.service.impl.QuestionServiceImpl;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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
class QuestionControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;
    @MockBean
    private QuestionServiceImpl questionService;
    @MockBean
    private QuizServiceImpl quizService;

    @BeforeEach
    public void setUp(){
        this.mvc = MockMvcBuilders.webAppContextSetup(context).build();

    }
    @Test
    @DisplayName("add new question")
    void addQuestionTest() throws Exception {
        Question question = getQuestionObject();
        when(questionService.addQuestion(question)).thenReturn(question);
        mvc.perform( MockMvcRequestBuilders
                        .post("/question/")
                        .content(asJsonString(question))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
    @Test
    @DisplayName("update question")
    void updateQuestionTest() throws Exception {
        Question question = getQuestionObject();
        when(questionService.addQuestion(question)).thenReturn(question);
        mvc.perform( MockMvcRequestBuilders
                        .put("/question/")
                        .content(asJsonString(question))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("get questions of a quiz")
    void getQuestionsTest() throws Exception {
        Quiz quiz = new Quiz();
        quiz.setQid(99L);
        quiz.setTitle("Spring");
        quiz.setDescription("This is spring quiz");
        quiz.setMaxMarks("10");
        quiz.setNumberOfQuestions("5");

        Set<Question> questionList = new HashSet<>();
        Question qOne = new Question();
        Question qTwo = new Question();
        Question qThree = new Question();
        questionList.add(qOne);
        questionList.add(qTwo);
        questionList.add(qThree);
        when(quizService.getQuiz(99L)).thenReturn(quiz);
        when(questionService.getQuestionOfQuiz(quiz)).thenReturn(questionList);

        mvc.perform( MockMvcRequestBuilders
                        .get("/question/quiz/{qid}", 99L)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }
    @Test
    @DisplayName("get a single question")
    void getQuestionTest() throws Exception {
        Question question = new Question();
        question.setQuesId(99L);
        question.setContent("What is life?");
        when(questionService.get(99L)).thenReturn(question);

        mvc.perform( MockMvcRequestBuilders
                        .get("/question/{quesId}", 99L)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());


    }
    @Test
    @DisplayName("delete question by Id")
    void deleteQuizTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .delete("/question/{quesId}",999L))
                .andExpect(status().isOk());
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private Question getQuestionObject(){
        Question question = new Question();
        question.setContent("What is Java?");
        question.setOption1("op1");
        question.setOption2("op2");
        question.setOption3("op3");
        question.setOption4("op4");
        question.setAnswer("op4");
        return question;
    }

}
