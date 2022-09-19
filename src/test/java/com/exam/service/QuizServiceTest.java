package com.exam.service;

import com.exam.exception.QuizNotFoundException;
import com.exam.model.exam.Category;
import com.exam.model.exam.Quiz;
import com.exam.repo.QuizRepository;
import com.exam.service.impl.QuizServiceImpl;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class QuizServiceTest {
    @InjectMocks
    private QuizServiceImpl quizService;
    @Mock
    private QuizRepository quizRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getQuizzesTest(){
        List<Quiz> quizList = new ArrayList<>();
        Quiz quizOne = new Quiz();
        Quiz quizTwo = new Quiz();
        Quiz quizThree = new Quiz();
        quizList.add(quizOne);
        quizList.add(quizTwo);
        quizList.add(quizThree);
        when(quizRepository.findAll()).thenReturn(quizList);

        //test
        Set<Quiz> list = quizService.getQuizzes();
        assertEquals(3, list.size());
        verify(quizRepository, times(1)).findAll();

    }
    @SneakyThrows
    @Test
    public void getQuizByIdTest(){
        Quiz quiz = new Quiz();
        quiz.setQid(99L);
        quiz.setTitle("Test");
        quiz.setDescription("This is test quiz");
        quiz.setMaxMarks("10");
        quiz.setNumberOfQuestions("5");

        when(quizRepository.findById(99L)).thenReturn(Optional.of(quiz));
        Quiz quizObj = quizService.getQuiz(99L);

        assertEquals("Test", quizObj.getTitle());
        assertEquals("This is test quiz",quizObj.getDescription());
        assertEquals("5",quizObj.getNumberOfQuestions());

    }
    @Test
    public void addQuizTest(){
        Quiz quiz = getQuizObject();

        when(quizRepository.save(quiz)).thenReturn(quiz);
        assertThat(quizService.addQuiz(quiz).getTitle())
                .isEqualTo(quiz.getTitle());
    }
    @Test
    public void quizExceptionTest(){
        assertThatThrownBy(() -> {
            quizService.getQuiz(9999L);
        }).isInstanceOf(QuizNotFoundException.class)
                .hasMessage("Quiz not found.");
    }
    @Test
    public void updateQuizTest(){
        Quiz quiz = getQuizObject();

        when(quizRepository.save(quiz)).thenReturn(quiz);
        assertThat(quizService.addQuiz(quiz).getTitle())
                .isEqualTo(quiz.getTitle());
    }
    @Test
    public void getQuizzesOfCategoryTest(){
        Category category = new Category();

        List<Quiz> quizList = new ArrayList<>();
        Quiz quizOne = new Quiz();
        Quiz quizTwo = new Quiz();
        Quiz quizThree = new Quiz();
        quizList.add(quizOne);
        quizList.add(quizTwo);
        quizList.add(quizThree);

        when(quizRepository.findBycategory(category)).thenReturn(quizList);
        Assertions.assertThat(quizService.getQuizzesOfCategory(category))
                .hasSize(3);


    }
    @Test
    public void getActiveQuizzesTest(){
        List<Quiz> quizList = new ArrayList<>();
        Quiz quizOne = new Quiz();
        Quiz quizTwo = new Quiz();
        quizList.add(quizOne);
        quizList.add(quizTwo);
        when(quizRepository.findByActive(true)).thenReturn(quizList);
        Assertions.assertThat(quizService.getActiveQuizzes())
                .hasSize(2);

    }
    @Test
    public void getActiveQuizzesOfCategoryTest(){
        Category category = new Category();
        category.setTitle("Programming");
        category.setDescription("Java 8 feature");

        List<Quiz> activeQuizList = new ArrayList<>();
        Quiz quizOne = new Quiz();
        Quiz quizTwo = new Quiz();
        activeQuizList.add(quizOne);
        activeQuizList.add(quizTwo);

        when(quizRepository.findByCategoryAndActive(category, true)).thenReturn(activeQuizList);

        Assertions.assertThat(quizService.getActiveQuizzesOfCategory(category))
                .hasSize(2);
    }
    //utility method
    private Quiz getQuizObject(){
        Quiz quiz = new Quiz();
        quiz.setTitle("Test");
        quiz.setDescription("This is test quiz");
        quiz.setMaxMarks("10");
        quiz.setNumberOfQuestions("5");
        return quiz;

    }
}
