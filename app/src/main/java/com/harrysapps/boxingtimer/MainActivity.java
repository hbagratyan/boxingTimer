package com.harrysapps.boxingtimer;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
        if (view == findViewById(R.id.btnRemoveRoundSecs) && roundTime > 30000) {
            roundTime -= 30000;
            TextView roundTextView = findViewById(R.id.roundTextView);
            int minutesRound = (roundTime / 60000);
            int secsRound = roundTime / 1000 % 60;
            String roundString = String.format(Locale.getDefault(), "%d:%02d", minutesRound, secsRound);
            roundTextView.setText(roundString);
        } else if (view == findViewById(R.id.btnRemoveRestSecs)  && restTime > 30000) {
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
        } else if (view == findViewById(R.id.btnRemovePreparationSecs) && preparation > 5000){
            preparation -= 5000;
            TextView preparationTextView = findViewById(R.id.preparationTextView);
            int secsPreparation = preparation / 1000;
            String preparationTimeString = (secsPreparation + " seconds");
            preparationTextView.setText(preparationTimeString);
        }
    }

    public void onClickSave(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.save_title)
                .setMessage(R.string.save_message)
                .setCancelable(true)
                .setPositiveButton(R.string.save_positive_answer, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setNegativeButton(R.string.save_negative_answer,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void onClickReset(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.reset_title)
                .setCancelable(true)
                .setPositiveButton(R.string.reset_positive_answer, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setNegativeButton(R.string.reset_negative_answer,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
