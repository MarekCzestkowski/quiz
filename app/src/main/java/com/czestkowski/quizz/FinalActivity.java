package com.czestkowski.quizz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FinalActivity extends AppCompatActivity {

    TextView congratsTextView;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        Button goToMainActivity;
        goToMainActivity = (Button) findViewById(R.id.goToMainActivity);
        goToMainActivity.setText("Idź do Wyboru Quizów");

        Intent it = getIntent();
        Bundle b = it.getExtras();
        score = (int) b.get("score");

        congratsTextView = (TextView) findViewById(R.id.congratsTextView);
        congratsTextView.setText("gratulacje! Zdobyłeś " + score + " punkty/ów");



        goToMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(FinalActivity.this, MainActivity.class);
//                it.putExtra("score", score);
                startActivity(it);
            }
        });
    }
}
