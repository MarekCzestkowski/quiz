package com.czestkowski.quizz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinalActivity extends AppCompatActivity {

    TextView congratsTextView;
    int score;
    String content;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        Button goToMainActivity;
        goToMainActivity = (Button) findViewById(R.id.goToMainActivity);
        goToMainActivity.setText("Idź do Wyboru Quizów");
        congratsTextView = (TextView) findViewById(R.id.congratsTextView);

        Intent it = getIntent();
        Bundle b = it.getExtras();
        if (b.get("score") != null) {
            score = (int) b.get("score");
            int scoreMax = (int) b.get("scoreMax");
            congratsTextView.setText("Gratulacje!\nZdobyłeś " + score + "/" +scoreMax+" pkt");
        } else {
            content = (String) b.get("abcd1");
            title = (String) b.get("abcd2");
            congratsTextView.setText("Wynik Quizu: " + title +"\n\n"+content);
        }

        goToMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(FinalActivity.this, MainActivity.class);
                startActivity(it);
            }
        });
    }
}
