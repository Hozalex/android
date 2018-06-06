package org.orangecorn.basketscore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int scoreA = 0;
    int scoreB = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void displayForTeamA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.score_teamA);
        scoreView.setText(String.valueOf(score));

    }

    public void displayForTeamB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.score_teamB);
        scoreView.setText(String.valueOf(score));

    }

    public void twoPointClickA(View view) {
        scoreA += 2;
        displayForTeamA(scoreA);
    }

    public void twoPointClickB(View view) {
        scoreB += 2;
        displayForTeamB(scoreB);
    }

    public void threePointClickA(View view) {
        scoreA += 3;
        displayForTeamA(scoreA);
    }

    public void threePointClickB(View view) {
        scoreB += 3;
        displayForTeamB(scoreB);
    }

    public void freePointClickA(View view) {
        scoreA += 1;
        displayForTeamA(scoreA);
    }

    public void freePointClickB(View view) {
        scoreB += 1;
        displayForTeamB(scoreB);
    }

    public void minusPointA(View view) {
        if (scoreA != 0) {
            scoreA -= 1;
            displayForTeamA(scoreA);
        } else return;

    }

    public void minusPointB(View view) {
        if (scoreB != 0) {
            scoreB -= 1;
            displayForTeamB(scoreB);
        } else return;
    }


    public void reset(View view) {
        scoreB = 0;
        scoreA = 0;
        displayForTeamA(scoreA);
        displayForTeamB(scoreB);
    }
}
