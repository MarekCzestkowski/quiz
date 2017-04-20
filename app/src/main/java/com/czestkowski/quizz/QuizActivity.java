package com.czestkowski.quizz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends MainActivity {

    TextView textView;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    int score = 0;

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
        String urel = model.getUrlToPhoto();
        ImageView im = (ImageView) findViewById(R.id.imageView2);
        new DownloadImageTask(im).execute(urel);

        List<String> questionsList = new ArrayList<>(model.getTextList());
        List<List<String>> answersList = new ArrayList<>(model.getAnswerList());
        List<String> answersForOneQ = new ArrayList<>(answersList.get(0));
        final List<Integer> numberOfCorrectAnswer = new ArrayList<>(model.getListNumberOfCorrectAnswer());

        askQuestion(questionsList.get(0), numberOfCorrectAnswer.get(0), answersForOneQ.get(0), answersForOneQ.get(1), answersForOneQ.get(2), answersForOneQ.get(3));


        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x = 0;
                if (numberOfCorrectAnswer.get(0) == x) {
//                    updateQuestion();
                    Toast.makeText(QuizActivity.this, "correct", Toast.LENGTH_SHORT).show();
                    score++;
                } else {
                    Toast.makeText(QuizActivity.this, "wrong", Toast.LENGTH_SHORT).show();
//                    updateQuestion();
                }
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x = 1;
                if (numberOfCorrectAnswer.get(0) == x) {
//                    updateQuestion();
                    Toast.makeText(QuizActivity.this, "correct", Toast.LENGTH_SHORT).show();
                    score++;

                } else {
                    Toast.makeText(QuizActivity.this, "wrong", Toast.LENGTH_SHORT).show();
//                    updateQuestion();
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x = 2;
                if (numberOfCorrectAnswer.get(0) == x) {
//                    updateQuestion();
                    Toast.makeText(QuizActivity.this, "correct", Toast.LENGTH_SHORT).show();
                    score++;

                } else {
                    Toast.makeText(QuizActivity.this, "wrong", Toast.LENGTH_SHORT).show();
//                    updateQuestion();
                }
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x = 3;
                if (numberOfCorrectAnswer.get(0) == x) {
//                    updateQuestion();
                    Toast.makeText(QuizActivity.this, "correct", Toast.LENGTH_SHORT).show();
                    score++;

                } else {
                    Toast.makeText(QuizActivity.this, "wrong", Toast.LENGTH_SHORT).show();
//                    updateQuestion();
                }
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

    public void askQuestion(String q, Integer x, String a, String b, String c, String d) {

// x - number of the correct answer
        textView.setText(q);
        button0.setText(a);
        button1.setText(b);
        button2.setText(c);
        button3.setText(d);


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
