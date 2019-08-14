package com.harrysapps.boxingtimer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;

import java.util.Locale;

public class TimerActivity extends AppCompatActivity {

    public int counter = MainActivity.roundTime;
    public boolean isRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        final TextView mainTimerView = findViewById(R.id.mainTimerView);
        final CountDownTimer timer = new CountDownTimer(counter, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                isRunning = true;
                int minutesRest = (counter / 60000);
                int secsRest = counter / 1000 % 60;
                String roundTimeLeft = String.format(Locale.getDefault(), "%d:%02d", minutesRest, secsRest);
                mainTimerView.setText(roundTimeLeft);
                counter -= 1000;
            }

            @Override
            public void onFinish() {
                mainTimerView.setText("Finished");
            }

        }.start();
        ToggleButton btnPauseResume = findViewById(R.id.btnPauseResume);
        btnPauseResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    timer.cancel();
                    isRunning = false;
                } else timer.start();
            }
        });
    }

    public void onClickBack(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(TimerActivity.this);
        builder.setTitle(R.string.exit_title)
                .setMessage(R.string.exit_message)
                .setCancelable(true)
                .setPositiveButton(R.string.exit_positive_answer, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(TimerActivity.this, MainActivity.class);
                startActivity(intent);
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


