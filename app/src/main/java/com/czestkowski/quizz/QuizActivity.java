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
import android.widget.ProgressBar;
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
    List<List<String>> abcdList;
    List<String> abcdAnswers;
    List<String> flagContent;
    List<String> flagTitle;
    ProgressBar progressBar;
    int progress = 0;

    int[] abcdValues = new int[4];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        for (int i = 0; i < abcdValues.length; i++) {
            abcdValues[i] = 0;
        }

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
        abcdList = new ArrayList<>(model.getAbcdList());
        abcdAnswers = new ArrayList<>();
        flagContent = new ArrayList<>(model.getFlagContent());
        flagTitle = new ArrayList<>(model.getFlagTitle());

        askQuestion(counter);

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x = 0;
                if (numberOfCorrectAnswer.size() != 0) {
                    if (numberOfCorrectAnswer.get(counter) == x) {
                        Toast.makeText(QuizActivity.this, "DOBRZE", Toast.LENGTH_SHORT).show();
                        score++;
                    } else {
                        Toast.makeText(QuizActivity.this, "ŹLE", Toast.LENGTH_SHORT).show();
                    }
                    counter++;
                    askQuestion(counter);
                } else {
                    countAbcd(x);
                }
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x = 1;
                if (numberOfCorrectAnswer.size() != 0) {

                    if (numberOfCorrectAnswer.get(counter) == x) {
                        Toast.makeText(QuizActivity.this, "DOBRZE", Toast.LENGTH_SHORT).show();
                        score++;

                    } else {
                        Toast.makeText(QuizActivity.this, "ŹLE", Toast.LENGTH_SHORT).show();
                    }
                    counter++;
                    askQuestion(counter);
                } else {
                    countAbcd(x);
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x = 2;
                if (numberOfCorrectAnswer.size() != 0) {

                    if (numberOfCorrectAnswer.get(counter) == x) {
                        Toast.makeText(QuizActivity.this, "DOBRZE", Toast.LENGTH_SHORT).show();
                        score++;

                    } else {
                        Toast.makeText(QuizActivity.this, "ŹLE", Toast.LENGTH_SHORT).show();
                    }
                    counter++;
                    askQuestion(counter);
                } else {
                    countAbcd(x);
                }
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x = 3;
                if (numberOfCorrectAnswer.size() != 0) {

                    if (numberOfCorrectAnswer.get(counter) == x) {
                        Toast.makeText(QuizActivity.this, "DOBRZE", Toast.LENGTH_SHORT).show();
                        score++;

                    } else {
                        Toast.makeText(QuizActivity.this, "ŹLE", Toast.LENGTH_SHORT).show();
                    }
                    counter++;
                    askQuestion(counter);
                } else {
                    countAbcd(x);
                }
            }
        });
    }


    public void countAbcd(int x) {
        if (abcdList.get(counter).get(x).equals("A")) {
            abcdAnswers.add("A");
            abcdValues[0]++;
            Toast.makeText(QuizActivity.this, "A", Toast.LENGTH_SHORT).show();
        } else if (abcdList.get(counter).get(x).equals("B")) {
            abcdAnswers.add("B");
            abcdValues[1]++;
            Toast.makeText(QuizActivity.this, "B", Toast.LENGTH_SHORT).show();
        } else if (abcdList.get(counter).get(x).equals("C")) {
            abcdAnswers.add("C");
            abcdValues[2]++;
            Toast.makeText(QuizActivity.this, "C", Toast.LENGTH_SHORT).show();
        } else if (abcdList.get(counter).get(x).equals("D")) {
            abcdAnswers.add("D");
            abcdValues[3]++;
            Toast.makeText(QuizActivity.this, "D", Toast.LENGTH_SHORT).show();
        }
        counter++;
        askQuestion(counter);

    }

    public void askQuestion(int counter) {

        if (questionsList.size() < counter + 1) {
            Intent it = new Intent(QuizActivity.this, FinalActivity.class);
            if (numberOfCorrectAnswer.size() != 0) {
                it.putExtra("score", score);
                it.putExtra("scoreMax", questionsList.size());

            } else {
                //Finding which value (A,B,C,D) has the biggest value
                int maxIndex = 0;
                for (int i = 1; i < abcdValues.length; i++) {
                    int newnumber = abcdValues[i];
                    if ((newnumber > abcdValues[maxIndex])) {
                        maxIndex = i;
                    }
                }
                String content = flagContent.get(maxIndex);
                String title = flagTitle.get(maxIndex);
                it.putExtra("abcd1", content);
                it.putExtra("abcd2", title);
            }
            startActivity(it);

        }
        answersForOneQ = new ArrayList<>(answersList.get(counter));
        progressBar.setProgress(progress);
        int x = 100 / questionsList.size();
        progress = progress + x;

        textView.setText(questionsList.get(counter));
        button0.setText(answersForOneQ.get(0));
        button1.setText(answersForOneQ.get(1));
        button2.setText(answersForOneQ.get(2));
        if (answersForOneQ.size() > 3) {
            button3.setText(answersForOneQ.get(3));
        }
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
