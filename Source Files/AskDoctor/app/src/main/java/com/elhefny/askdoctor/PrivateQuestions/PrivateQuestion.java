package com.elhefny.askdoctor.PrivateQuestions;

import java.io.Serializable;

public class PrivateQuestion implements Serializable {

    private String questionSender, questionReceiver, questionBody,questionTime, questionAnswer;

    public PrivateQuestion() {
    }

    public PrivateQuestion(String questionSender, String questionReceiver, String questionBody, String questionTime, String questionAnswer) {
        this.questionSender = questionSender;
        this.questionReceiver = questionReceiver;
        this.questionBody = questionBody;
        this.questionTime = questionTime;
        this.questionAnswer = questionAnswer;
    }

    public String getQuestionSender() {
        return questionSender;
    }

    public void setQuestionSender(String questionSender) {
        this.questionSender = questionSender;
    }

    public String getQuestionReceiver() {
        return questionReceiver;
    }

    public void setQuestionReceiver(String questionReceiver) {
        this.questionReceiver = questionReceiver;
    }

    public String getQuestionBody() {
        return questionBody;
    }

    public void setQuestionBody(String questionBody) {
        this.questionBody = questionBody;
    }

    public String getQuestionTime() {
        return questionTime;
    }

    public void setQuestionTime(String questionTime) {
        this.questionTime = questionTime;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }
}
