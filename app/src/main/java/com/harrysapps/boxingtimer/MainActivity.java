package com.harrysapps.boxingtimer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    public static int roundTime;
    public static int restTime;
    public static int rounds;
    public static int preparation;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String RoundTime = "RoundTimeKey";
    public static final String RestTime = "RestTimeKey";
    public static final String Rounds = "RoundsKey";
    public static final String Preparation = "PreparationKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        roundTime = sharedpreferences.getInt(RoundTime, DefaultValues.ROUND_TIME);
        restTime = sharedpreferences.getInt(RestTime, DefaultValues.REST_TIME);
        rounds = sharedpreferences.getInt(Rounds, DefaultValues.ROUNDS);
        preparation = sharedpreferences.getInt(Preparation, DefaultValues.PREPARATION);

        TextView roundTextView = findViewById(R.id.roundTextView);
        View root = roundTextView.getRootView();
        root.setBackgroundColor(getResources().getColor(R.color.colorBackgroundMain));


        setTextViews(roundTime, restTime, rounds, preparation);
    }

    public void onClickStart(View view) {
        Intent intent = new Intent(this, RoundTimerActivity.class);
        startActivity(intent);
        finish();
    }

    public void setTextViews(int roundTimeInt, int restTimeInt, int roundsInt, int preparationInt) {
        TextView roundTextView = findViewById(R.id.roundTextView);
        int minutesRound = (roundTimeInt / 60000);
        int secsRound = roundTimeInt / 1000 % 60;
        String roundTime = String.format(Locale.getDefault(), "%d:%02d", minutesRound, secsRound);
        roundTextView.setText(roundTime);

        TextView restTextView = findViewById(R.id.restTextView);
        int minutesRest = (restTimeInt / 60000);
        int secsRest = restTimeInt / 1000 % 60;
        String restTime = String.format(Locale.getDefault(), "%d:%02d", minutesRest, secsRest);
        restTextView.setText(restTime);

        TextView roundsTextView = findViewById(R.id.roundsTextView);
        roundsTextView.setText(Integer.toString(roundsInt));

        TextView preparationTextView = findViewById(R.id.preparationTextView);
        int secsPreparation = preparationInt / 1000 % 60;
        String preparationTime = (secsPreparation + " seconds");
        preparationTextView.setText(preparationTime);

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
        } else if (view == findViewById(R.id.btnRemoveRestSecs) && restTime > 30000) {
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
        } else if (view == findViewById(R.id.btnRemovePreparationSecs) && preparation > 5000) {
            preparation -= 5000;
            TextView preparationTextView = findViewById(R.id.preparationTextView);
            int secsPreparation = preparation / 1000;
            String preparationTimeString = (secsPreparation + " seconds");
            preparationTextView.setText(preparationTimeString);
        }
    }

    public void onClickSave(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.save_title)
                .setMessage(R.string.save_message)
                .setCancelable(true)
                .setPositiveButton(R.string.save_positive_answer, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putInt(RoundTime, roundTime);
                        editor.putInt(RestTime, restTime);
                        editor.putInt(Rounds, rounds);
                        editor.putInt(Preparation, preparation);
                        editor.apply();
                        editor.commit();
                        Toast.makeText(MainActivity.this, R.string.saved_toast, Toast.LENGTH_SHORT).show();
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

    public void onClickReset(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.reset_title)
                .setCancelable(true)
                .setPositiveButton(R.string.reset_positive_answer, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putInt(RoundTime, DefaultValues.ROUND_TIME);
                        editor.putInt(RestTime, DefaultValues.REST_TIME);
                        editor.putInt(Rounds, DefaultValues.ROUNDS);
                        editor.putInt(Preparation, DefaultValues.PREPARATION);
                        editor.apply();
                        editor.commit();
                        roundTime = DefaultValues.ROUND_TIME;
                        restTime = DefaultValues.REST_TIME;
                        rounds = DefaultValues.ROUNDS;
                        preparation = DefaultValues.PREPARATION;
                        setTextViews(roundTime, restTime, rounds, preparation);
                        Toast.makeText(MainActivity.this, R.string.reset_toast, Toast.LENGTH_SHORT).show();
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
