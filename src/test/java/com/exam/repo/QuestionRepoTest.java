package com.exam.repo;

import com.exam.model.exam.Question;
import com.exam.model.exam.Quiz;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class QuestionRepoTest {

    @Mock
    QuestionRepository questionRepository;
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findByQuizTest(){
        Quiz quiz = getQuiz();

        Set<Question> quesList = new HashSet<>();
        Question qOne = new Question();
        Question qTwo = new Question();
        Question qThree = new Question();
        quesList.add(qOne);
        quesList.add(qTwo);
        quesList.add(qThree);

        when(questionRepository.findByQuiz(quiz)).thenReturn(quesList);

        Set<Question> expectedList = questionRepository.findByQuiz(quiz);
        assertEquals(quesList, expectedList);
        assertThat(expectedList).size().isEqualTo(3);
    }
    private Quiz getQuiz(){
        Quiz quiz = new Quiz();
        quiz.setQid(1L);
        quiz.setTitle("Test quiz");
        quiz.setMaxMarks("100");
        return quiz;
    }
}
