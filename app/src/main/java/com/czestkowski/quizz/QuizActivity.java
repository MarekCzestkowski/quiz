package com.czestkowski.quizz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.czestkowski.quizz.models.QuizDetailModel;
import com.czestkowski.quizz.models.QuizModel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends MainActivity {

    TextView textView;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    int score = 0;
    int counter = 0;
    List<List<String>> answersList;
    List<String> questionsList;
    List<String> answersForOneQ;
    List<Integer> numberOfCorrectAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);


        textView = (TextView) findViewById(R.id.textView);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        QuizDetailModel model = (QuizDetailModel) getIntent().getSerializableExtra("value");

        ImageView im = (ImageView) findViewById(R.id.imageView2);
        new DownloadImageTask(im).execute(model.getUrlToPhoto());
        questionsList = new ArrayList<>(model.getTextList());
        answersList = new ArrayList<>(model.getAnswerList());
        answersForOneQ = new ArrayList<>(answersList.get(0));
        numberOfCorrectAnswer = new ArrayList<>(model.getListNumberOfCorrectAnswer());

        askQuestion(counter);


        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x = 0;
                if (numberOfCorrectAnswer.get(counter) == x) {
//                    updateQuestion();
                    Toast.makeText(QuizActivity.this, "DOBRZE", Toast.LENGTH_SHORT).show();
                    score++;
                } else {
                    Toast.makeText(QuizActivity.this, "ŹLE", Toast.LENGTH_SHORT).show();
//                    updateQuestion();
                }
                counter++;
                askQuestion(counter);


            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x = 1;
                if (numberOfCorrectAnswer.get(counter) == x) {
                    Toast.makeText(QuizActivity.this, "DOBRZE", Toast.LENGTH_SHORT).show();
                    score++;

                } else {
                    Toast.makeText(QuizActivity.this, "ŹLE", Toast.LENGTH_SHORT).show();
                }
                counter++;
                askQuestion(counter);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x = 2;
                if (numberOfCorrectAnswer.get(counter) == x) {
                    Toast.makeText(QuizActivity.this, "DOBRZE", Toast.LENGTH_SHORT).show();
                    score++;

                } else {
                    Toast.makeText(QuizActivity.this, "ŹLE", Toast.LENGTH_SHORT).show();
                }
                counter++;
                askQuestion(counter);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x = 3;
                if (numberOfCorrectAnswer.get(counter) == x) {
                    Toast.makeText(QuizActivity.this, "DOBRZE", Toast.LENGTH_SHORT).show();
                    score++;

                } else {
                    Toast.makeText(QuizActivity.this, "ŹLE", Toast.LENGTH_SHORT).show();
                }
                counter++;
                askQuestion(counter);
            }
        });


    }
//        button0.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
    //My logic for Button goes in here

//                if (mButtonChoice1.getText() == mAnswer){
//                    mScore = mScore + 1;
//                    updateScore(mScore);
//                    updateQuestion();
//                    //This line of code is optiona
//                    Toast.makeText(QuizActivity.this, "correct", Toast.LENGTH_SHORT).show();
//
//                }else {
//                    Toast.makeText(QuizActivity.this, "wrong", Toast.LENGTH_SHORT).show();
//                    updateQuestion();
//                }
//            }
//        });

    //End of Button Listener for Button1

    //Start of Button Listener for Button2
//        button1.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
    //My logic for Button goes in here

//                if (mButtonChoice2.getText() == mAnswer){
//                    mScore = mScore + 1;
//                    updateScore(mScore);
//                    updateQuestion();
//                    //This line of code is optiona
//                    Toast.makeText(QuizActivity.this, "correct", Toast.LENGTH_SHORT).show();
//
//                }else {
//                    Toast.makeText(QuizActivity.this, "wrong", Toast.LENGTH_SHORT).show();
//                    updateQuestion();
//                }
//            }
//        });

    //End of Button Listener for Button2


    //Start of Button Listener for Button3
//        button2.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
    //My logic for Button goes in here

//                if (mButtonChoice3.getText() == mAnswer){
//                    mScore = mScore + 1;
//                    updateScore(mScore);
//                    updateQuestion();
//                    //This line of code is optiona
//                    Toast.makeText(QuizActivity.this, "correct", Toast.LENGTH_SHORT).show();
//
//                }else {
//                    Toast.makeText(QuizActivity.this, "wrong", Toast.LENGTH_SHORT).show();
//                    updateQuestion();
//                }
//            }
//        });

    //End of Button Listener for Button3

    //    }
//    int[] toIntArray(List<Integer> list) {
//        int[] ret = new int[list.size()];
//        for (int i = 0; i < ret.length; i++)
//            ret[i] = list.get(i);
//        return ret;
//    }

    public void askQuestion(int counter) {

        if (questionsList.size() < counter + 1) {
            Intent it = new Intent(QuizActivity.this, FinalActivity.class);
            it.putExtra("score", score);

            startActivity(it);
        }
        answersForOneQ = new ArrayList<>(answersList.get(counter));


// x - number of the correct answer
        textView.setText(questionsList.get(counter));
        button0.setText(answersForOneQ.get(0));
        button1.setText(answersForOneQ.get(1));
        button2.setText(answersForOneQ.get(2));
        button3.setText(answersForOneQ.get(3));

//startActivity
//        Toast.makeText(QuizActivity.this, x, Toast.LENGTH_SHORT).show();


//        askQuestion();
    }

    public void onClick(View v) {
        startActivity(new Intent(this, QuizModel.class));
        finish();
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
