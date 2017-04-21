package com.czestkowski.quizz.models;



public class QuizModel {
//    SharedPreferences sharedPreferences;
//    SharedPreferences.Editor editor  ;

    private String title;
    private String photo; //URL of the photo
    private String id;
    private int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        if (score > this.score) {
            this.score = score;
//            editor.putInt("BestScore", score);
//            editor.commit();
        }
    }


    public String getPhoto() {
        return photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}

