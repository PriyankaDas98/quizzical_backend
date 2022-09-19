package com.exam.repo;

import com.exam.model.exam.Category;
import com.exam.model.exam.Quiz;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class QuizRepoTest {
    @Mock
    QuizRepository quizRepository;
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void findByCategoryTest(){
        List<Quiz> quizList = new ArrayList<>();
        Quiz quizOne = new Quiz();
        Quiz quizTwo = new Quiz();

        quizList.add(quizOne);
        quizList.add(quizTwo);

        Category category = new Category();
        category.setCid(99L);
        category.setTitle("Programming");
        category.setDescription("Test Programming Category");

        when(quizRepository.findBycategory(category)).thenReturn(quizList);

        List<Quiz> expectedList = quizRepository.findBycategory(category);
        assertEquals(quizList, expectedList);
        Assertions.assertThat(expectedList).size().isEqualTo(2);
    }
    @Test
    public void findByActiveTest(){
        List<Quiz> quizList = new ArrayList<>();
        Quiz quizOne = new Quiz();
        Quiz quizTwo = new Quiz();
        Quiz quizThree = new Quiz();
        quizList.add(quizOne);
        quizList.add(quizTwo);
        quizList.add(quizThree);

        when(quizRepository.findByActive(true)).thenReturn(quizList);

        List<Quiz> expectedList = quizRepository.findByActive(true);
        assertEquals(quizList, expectedList);
        Assertions.assertThat(expectedList).size().isEqualTo(3);
    }
    @Test
    public void findByCategoryAndActiveTest(){
        List<Quiz> quizList = new ArrayList<>();
        Quiz quizOne = new Quiz();
        Quiz quizTwo = new Quiz();

        quizList.add(quizOne);
        quizList.add(quizTwo);

        Category category = new Category();
        category.setCid(99L);
        category.setTitle("Programming");
        category.setDescription("Test Programming Category");

        when(quizRepository.findByCategoryAndActive(category, true)).thenReturn(quizList);
        List<Quiz> expectedList = quizRepository.findByCategoryAndActive(category, true);
        assertEquals(quizList, expectedList);
        Assertions.assertThat(expectedList).size().isEqualTo(2);
    }
}
