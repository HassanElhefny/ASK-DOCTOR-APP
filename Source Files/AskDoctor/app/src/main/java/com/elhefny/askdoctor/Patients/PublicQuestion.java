package com.elhefny.askdoctor.Patients;

import java.io.Serializable;

public class PublicQuestion implements Serializable {
    private String questionBody, date, answer, patientEmail;


    public PublicQuestion() {
    }

    public PublicQuestion(String questionBody, String date, String answer, String patientEmail) {
        this.questionBody = questionBody;
        this.date = date;
        this.answer = answer;
        this.patientEmail = patientEmail;
    }

    public String getQuestionBody() {
        return questionBody;
    }

    public String getDate() {
        return date;
    }

    public String getAnswer() {
        return answer;
    }

    public void setQuestionBody(String questionBody) {
        this.questionBody = questionBody;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }
}
