package com.harrysapps.boxingtimer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.ToggleButton;
import android.app.AlertDialog;
import android.content.DialogInterface;

import java.util.Locale;

public class RoundTimerActivity extends AppCompatActivity {

    private int roundTime = MainActivity.roundTime;
    private int rounds = MainActivity.rounds;
    private int preparationSecs = MainActivity.preparation;
    private int restTime = MainActivity.restTime;
    private boolean running;
    private int currentRound = 1;
    private int timerID;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        TextView topTimerView = findViewById(R.id.timerTopLogo);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        topTimerView.setText("Prepare to fight");
        progressBar.setProgress(100);
        runCountDownTimer(preparationSecs);
    }

    public void playSound(int resource) {
        mp = MediaPlayer.create(this, resource);
        mp.start();
         mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (mp != null) {
                    mp.stop();
                    mp.release();
                }
            }
        });
    }

    public void runRoundTimer(int roundTime) {
        TextView topTimerView = findViewById(R.id.timerTopLogo);
        TextView mainTimerView = findViewById(R.id.mainTimerView);
        timerID = 2;
        topTimerView.setText("Round " + Integer.toString(currentRound) + "/" + Integer.toString(rounds));
        int minutes = (roundTime / 1000) / 60;
        int secs = roundTime / 1000 % 60;
        String time = String.format(Locale.getDefault(),
                "%02d:%02d", minutes, secs);
        mainTimerView.setText(time);
    }


    public void runRestTimer(int restTime) {
        TextView topTimerView = findViewById(R.id.timerTopLogo);
        TextView mainTimerView = findViewById(R.id.mainTimerView);
        timerID = 3;
        topTimerView.setText("Rest after round " + Integer.toString(currentRound) + '/' + Integer.toString(rounds));
        int minutes = (restTime / 1000) / 60;
        int secs = restTime / 1000 % 60;
        String time = String.format(Locale.getDefault(),
                "%02d:%02d", minutes, secs);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(100);
        mainTimerView.setText(time);
    }

    public void backToMainActivity() {
        roundTime = MainActivity.roundTime;
        currentRound = 1;
        rounds = MainActivity.rounds;
        running = false;
        if (mp != null) {
             mp.reset();
             mp.release();
        }
        Intent intent = new Intent(RoundTimerActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }



    public void runCountDownTimer(final int millisecondsRemaining) {
        timerID = 1;
        running = true;
        final int[] millisInFuture = {millisecondsRemaining}; /*It's a bit hacky way to access the
                                                                variable from inner class */
        final TextView mainTimerView = findViewById(R.id.mainTimerView);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int minutes = (millisInFuture[0] / 1000) / 60;
                int secs = millisInFuture[0] / 1000 % 60;
                String time = String.format(Locale.getDefault(),
                        "%02d:%02d", minutes, secs);
                if (running) mainTimerView.setText(time);
                ProgressBar progressBar = findViewById(R.id.progressBar);
                switch (timerID) {
                    case 1:
                        if (running) progressBar.setProgress((millisInFuture[0]) * 100 / preparationSecs);
                        break;
                    case 2:
                        if (running) progressBar.setProgress(millisInFuture[0] * 100 / roundTime);
                        break;
                    case 3:
                        if (running) progressBar.setProgress(millisInFuture[0] * 100 / restTime);
                        break;
                }
                if (running && millisInFuture[0] >= 1000) {
                    millisInFuture[0] -= 1000;
                }


                if (time.equals("00:05") && timerID == 1 | timerID == 3)
                    playSound(R.raw.flatline_sound);
                if (time.equals("00:10") && timerID == 2) playSound(R.raw.ten_sec_countdown);

                handler.postDelayed(this, 1000);
                if (time.equals("00:00")) {
                    switch (timerID) {
                        case 1:
                            playSound(R.raw.ding_sound);
                            millisInFuture[0] = roundTime;
                            runRoundTimer(millisInFuture[0]);
                            millisInFuture[0] -= 1000;
                            break;
                        case 2:
                            playSound(R.raw.ding_sound);
                            if (currentRound == rounds)
                                backToMainActivity();
                            else {
                                millisInFuture[0] = restTime;
                                runRestTimer(millisInFuture[0]);
                                millisInFuture[0] -= 1000;
                            }
                            break;
                        case 3:
                            playSound(R.raw.ding_sound);
                            currentRound++;
                            millisInFuture[0] = roundTime;
                            runRoundTimer(millisInFuture[0]);
                            millisInFuture[0] -= 1000;
                            break;
                    }
                }
            }
        });
        ToggleButton btnPauseResume = findViewById(R.id.btnPauseResume);
        btnPauseResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (running) {
                    running = false;
                    if (mp != null) {
                        mp.stop();
                        mp.release();
                        mp = null;
                    }
                } else {
                    running = true;
                }
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




