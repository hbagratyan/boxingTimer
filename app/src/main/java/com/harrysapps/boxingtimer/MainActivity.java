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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView roundTextView = findViewById(R.id.roundTextView);
        int minutesRound = (DefaultValues.ROUND_TIME/60000);
        int secsRound = DefaultValues.ROUND_TIME/1000%60;
        String roundTime = String.format(Locale.getDefault(), "%d:%02d", minutesRound, secsRound);
        roundTextView.setText(roundTime);

        TextView restTextView = findViewById(R.id.restTextView);
        int minutesRest = (DefaultValues.REST_TIME/60000);
        int secsRest = DefaultValues.REST_TIME/1000%60;
        String restTime = String.format(Locale.getDefault(), "%d:%02d", minutesRest, secsRest);
        restTextView.setText(restTime);

        TextView roundsTextView = findViewById(R.id.roundsTextView);
        int rounds = DefaultValues.ROUNDS;
        roundsTextView.setText(Integer.toString(rounds));

        TextView preparationTextView = findViewById(R.id.preparationTextView);
        int secsPreparation = DefaultValues.PREPARATION/1000%60;
        String preparationTime = (secsPreparation + " seconds");
        preparationTextView.setText(preparationTime);
    }

    public void onClickOpenSettings (View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onClickStart (View view) {
        Intent intent = new Intent(this, TimerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
