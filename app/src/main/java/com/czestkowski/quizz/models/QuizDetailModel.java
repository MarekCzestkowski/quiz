package com.czestkowski.quizz.models;


import android.content.Intent;

import java.io.Serializable;
import java.util.List;

public class QuizDetailModel  implements Serializable{
    public String text;
    private List<String> textList;
    private List<List<String>> answerList;
    private String urlToPhoto;
    private List<Integer> ListNumberOfCorrectAnswer;

    public List<Integer> getListNumberOfCorrectAnswer() {
        return ListNumberOfCorrectAnswer;
    }

    public void setListNumberOfCorrectAnswer(List<Integer> listNumberOfCorrectAnswer) {
        ListNumberOfCorrectAnswer = listNumberOfCorrectAnswer;
    }


    public String getUrlToPhoto() {
        return urlToPhoto;
    }

    public void setUrlToPhoto(String urlToPhoto) {
        this.urlToPhoto = urlToPhoto;
    }


    public List<List<String>> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<List<String>> answerList) {
        this.answerList = answerList;
    }


    public List<String> getTextList() {
        return textList;
    }



    public void setTextList(List<String> textList) {
        this.textList = textList;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }



}

