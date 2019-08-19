package com.harrysapps.boxingtimer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.app.AlertDialog;
import android.content.DialogInterface;

import java.util.Locale;

public class RoundTimerActivity extends AppCompatActivity {

    public int roundTime = MainActivity.roundTime;
    public int rounds = MainActivity.rounds;
    public boolean countdownTimerIsRunning = false;
    public boolean timerFinished = false;
    public static int currentRound = 1;
    public int preparationSecs = MainActivity.preparation;
    public int restTime = MainActivity.restTime;
    int timerID;
    CountDownTimer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        runPreparationTimer();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        timer.cancel();
    }

    @Override
    protected void onPause(){
        super.onPause();
        timer.cancel();
    }

    public void runPreparationTimer() {
        timerID = 1;
        TextView topTimerView = findViewById(R.id.timerTopLogo);
        topTimerView.setText("Prepare to fight");
        runCountDownTimer(preparationSecs);
    }

    public void runMainTimer(int roundTime, int currentRound) {
        timerID = 2;
        TextView topTimerView = findViewById(R.id.timerTopLogo);
        topTimerView.setText("Round " + Integer.toString(currentRound) + "/" + Integer.toString(rounds));
        runCountDownTimer(roundTime);
    }

    public void runRestTimer(int nextRound) {
        timerID = 3;
        TextView topTimerView = findViewById(R.id.timerTopLogo);
        topTimerView.setText("Rest after round " + Integer.toString(RoundTimerActivity.currentRound) + '/' + Integer.toString(rounds));
        runCountDownTimer(restTime);
    }

    public void playSound(int resource) {
        MediaPlayer mp = MediaPlayer.create(this, resource);
        mp.start();
    }

    public void backToMainActivity() {
        roundTime = MainActivity.roundTime;
        currentRound = 1;
        rounds = MainActivity.rounds;
        Intent intent = new Intent(RoundTimerActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void runCountDownTimer(int millisecondsRemaining) {
        final int[] millisInFuture = {millisecondsRemaining}; /*It's a bit hacky way to access the
                                                                variable from inner class */
        final TextView mainTimerView = findViewById(R.id.mainTimerView);
        timer = new CountDownTimer(millisInFuture[0], 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerFinished = false;
                countdownTimerIsRunning = true;
                int minutes = (millisInFuture[0] / 60000);
                int secs = millisInFuture[0] / 1000 % 60;
                String timeLeft = String.format(Locale.getDefault(), "%d:%02d", minutes, secs);
                mainTimerView.setText(timeLeft);
                if (timerID == 2 && millisInFuture[0] == 10000) playSound(R.raw.ten_sec_countdown);
                if (timerID != 2 && millisInFuture[0] == 5000) playSound(R.raw.flatline_sound);
                if (millisInFuture[0] > 0) millisInFuture[0] -= 1000;
            }

            @Override
            public void onFinish() {
                playSound(R.raw.ding_sound);
                switch (timerID) {
                    case 1:
                        runMainTimer(roundTime, currentRound);
                        break;
                    case 2:
                        if (currentRound == rounds) { backToMainActivity(); }
                        else runRestTimer(currentRound);
                        break;
                    case 3:
                        runMainTimer(roundTime, ++currentRound);
                        break;
                }
            }
        }.start();
        ToggleButton btnPauseResume = findViewById(R.id.btnPauseResume);
        btnPauseResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countdownTimerIsRunning) {
                    timer.cancel();
                    millisInFuture[0] += 1000;
                    countdownTimerIsRunning = false;
                } else timer.start();
            }
        });
    }

    public void onClickBack(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RoundTimerActivity.this);
        builder.setTitle(R.string.exit_title)
                .setMessage(R.string.exit_message)
                .setCancelable(true)
                .setPositiveButton(R.string.exit_positive_answer, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        backToMainActivity();
                    }
                })
                .setNegativeButton(R.string.exit_negative_answer,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}




