package com.harrysapps.boxingtimer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.SharedPreferences;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    public static int roundTime = DefaultValues.ROUND_TIME;
    public static int restTime = DefaultValues.REST_TIME;
    public static int rounds = DefaultValues.ROUNDS;
    public static int preparation = DefaultValues.PREPARATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView roundTextView = findViewById(R.id.roundTextView);
        int minutesRound = (roundTime / 60000);
        int secsRound = roundTime / 1000 % 60;
        String roundTime = String.format(Locale.getDefault(), "%d:%02d", minutesRound, secsRound);
        roundTextView.setText(roundTime);

        TextView restTextView = findViewById(R.id.restTextView);
        int minutesRest = (restTime / 60000);
        int secsRest = restTime / 1000 % 60;
        String restTime = String.format(Locale.getDefault(), "%d:%02d", minutesRest, secsRest);
        restTextView.setText(restTime);

        TextView roundsTextView = findViewById(R.id.roundsTextView);
        roundsTextView.setText(Integer.toString(rounds));

        TextView preparationTextView = findViewById(R.id.preparationTextView);
        int secsPreparation = preparation / 1000 % 60;
        String preparationTime = (secsPreparation + " seconds");
        preparationTextView.setText(preparationTime);
    }

    public void onClickOpenSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onClickStart(View view) {
        Intent intent = new Intent(this, RoundTimerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onClickAddX(View view) {
        if (view == findViewById(R.id.btnAddRoundSecs)) {
            roundTime += 30000;
            TextView roundTextView = findViewById(R.id.roundTextView);
            int minutesRound = (roundTime / 60000);
            int secsRound = roundTime / 1000 % 60;
            String roundString = String.format(Locale.getDefault(), "%d:%02d", minutesRound, secsRound);
            roundTextView.setText(roundString);
        } else if (view == findViewById(R.id.btnAddRestSecs)) {
            restTime += 30000;
            TextView restTextView = findViewById(R.id.restTextView);
            int minutesRound = (restTime / 60000);
            int secsRound = restTime / 1000 % 60;
            String restString = String.format(Locale.getDefault(), "%d:%02d", minutesRound, secsRound);
            restTextView.setText(restString);
        } else if (view == findViewById(R.id.btnAddRound)) {
            rounds += 1;
            TextView roundsTextView = findViewById(R.id.roundsTextView);
            roundsTextView.setText(Integer.toString(rounds));
        } else if (view == findViewById(R.id.btnAddPreparationSecs)) {
            preparation += 5000;
            TextView preparationTextView = findViewById(R.id.preparationTextView);
            int secsPreparation = preparation / 1000;
            String preparationTimeString = (secsPreparation + " seconds");
            preparationTextView.setText(preparationTimeString);
        }
    }

    public void onClickRemoveX(View view) {
        if (view == findViewById(R.id.btnRemoveRoundSecs) && roundTime > 0) {
            roundTime -= 30000;
            TextView roundTextView = findViewById(R.id.roundTextView);
            int minutesRound = (roundTime / 60000);
            int secsRound = roundTime / 1000 % 60;
            String roundString = String.format(Locale.getDefault(), "%d:%02d", minutesRound, secsRound);
            roundTextView.setText(roundString);
        } else if (view == findViewById(R.id.btnRemoveRestSecs)  && restTime > 0) {
            restTime -= 30000;
            TextView restTextView = findViewById(R.id.restTextView);
            int minutesRound = (restTime / 60000);
            int secsRound = restTime / 1000 % 60;
            String restString = String.format(Locale.getDefault(), "%d:%02d", minutesRound, secsRound);
            restTextView.setText(restString);
        } else if (view == findViewById(R.id.btnRemoveRound) && rounds > 1) {
            rounds -= 1;
            TextView roundsTextView = findViewById(R.id.roundsTextView);
            roundsTextView.setText(Integer.toString(rounds));
        } else if (view == findViewById(R.id.btnRemovePreparationSecs) && preparation > 0){
            preparation -= 5000;
            TextView preparationTextView = findViewById(R.id.preparationTextView);
            int secsPreparation = preparation / 1000;
            String preparationTimeString = (secsPreparation + " seconds");
            preparationTextView.setText(preparationTimeString);
        }
    }
}
