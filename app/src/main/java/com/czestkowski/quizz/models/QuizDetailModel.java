package com.czestkowski.quizz.models;



import java.io.Serializable;
import java.util.List;

public class QuizDetailModel  implements Serializable{
    private List<String> textList;
    private List<List<String>> answerList;
    private String urlToPhoto;
    private List<Integer> ListNumberOfCorrectAnswer;
    private List<List<String>> abcdList;
    private List<String> flagContent;
    private List<String> flagTitle;

    public List<String> getFlagTitle() {
        return flagTitle;
    }

    public void setFlagTitle(List<String> flagTitle) {
        this.flagTitle = flagTitle;
    }

    public List<String> getFlagContent() {
        return flagContent;
    }

    public void setFlagContent(List<String> flagContent) {
        this.flagContent = flagContent;
    }

    public List<List<String>> getAbcdList() {
        return abcdList;
    }

    public void setAbcdList(List<List<String>> abcdList) {
        this.abcdList = abcdList;
    }

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


}

