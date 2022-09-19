package com.exam.service;

import com.exam.exception.QuestionNotFoundException;
import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;
import com.exam.repo.QuestionRepository;
import com.exam.service.impl.QuestionServiceImpl;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class QuestionServiceTest {
    @InjectMocks
    private QuestionServiceImpl questionService;
    @Mock
    private QuestionRepository questionRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void getQuestionsTest(){
        List<Question> questionList = new ArrayList<>();
        Question qOne = new Question();
        Question qTwo = new Question();
        Question qThree = new Question();
        questionList.add(qOne);
        questionList.add(qTwo);
        questionList.add(qThree);
        when(questionRepository.findAll()).thenReturn(questionList);

        //test
        Set<Question> list = questionService.getQuestions();
        assertEquals(3, list.size());
        verify(questionRepository, times(1)).findAll();

    }
    @SneakyThrows
    @Test
    public void getQuestionByIdTest(){
        Question question = new Question();
        question.setQuesId(99L);
        question.setContent("What is life?");

        when(questionRepository.findById(99L)).thenReturn(Optional.of(question));
        Question questObj = questionService.getQuestion(99L);

        assertEquals("What is life?", questObj.getContent());


    }
    @Test
    public void addQuestionTest(){
        Question question = new Question();
        question.setQuesId(99L);
        question.setContent("What is life?");

        when(questionRepository.save(question)).thenReturn(question);

        assertThat(questionService.addQuestion(question).getContent())
                .isEqualTo(question.getContent());
    }
    @Test
    public void updateQuestionTest(){
        Question question = new Question();
        question.setQuesId(99L);
        question.setContent("What is not life?");

        when(questionRepository.save(question)).thenReturn(question);

        assertThat(questionService.addQuestion(question).getContent())
                .isEqualTo(question.getContent());
    }
    @Test
    public void questionExceptionTest(){
        assertThatThrownBy(() -> {
            questionService.getQuestion(9999L);
        }).isInstanceOf(QuestionNotFoundException.class)
                .hasMessage("Question not found.");
    }
    @Test
    public void getQuestionOfQuizTest(){
        Set<Question> quesOfQuizList = new HashSet<>();
        Question qOne = new Question();
        Question qTwo = new Question();
        Question qThree = new Question();
        quesOfQuizList.add(qOne);
        quesOfQuizList.add(qTwo);
        quesOfQuizList.add(qThree);

        Quiz quiz = new Quiz();
        when(questionRepository.findByQuiz(quiz)).thenReturn(quesOfQuizList);
        Assertions.assertThat(questionService.getQuestionOfQuiz(quiz))
                .hasSize(3);

    }
}
