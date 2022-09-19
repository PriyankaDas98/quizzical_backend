package com.exam.model.exam;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long quesId;
    @Column(length = 1000)
    @NotEmpty(message = "Content Cannot be empty")
    private String content;
    private String image;


    @NotEmpty(message = "Option Cannot be empty")
    private String option1;
    @NotEmpty(message = "Option Cannot be empty")
    private String option2;
    @NotEmpty(message = "Option Cannot be empty")
    private String option3;
    @NotEmpty(message = "Option Cannot be empty")
    private String option4;

    @NotEmpty(message = "Answer Cannot be empty")
    private String answer;
    //this field will not be stored inside db
    @Transient
    private String givenAnswer;

    @ManyToOne(fetch = FetchType.EAGER)
    private Quiz quiz;


    public Question() {
        //default constructor
    }


    public Long getQuesId() {
        return quesId;
    }

    public void setQuesId(Long quesId) {
        this.quesId = quesId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }
    //not to send this field in client side

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public String getGivenAnswer() {
        return givenAnswer;
    }

    public void setGivenAnswer(String givenAnswer) {
        this.givenAnswer = givenAnswer;
    }
}
