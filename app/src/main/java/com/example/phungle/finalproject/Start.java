package com.example.phungle.finalproject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.TextView;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

public class Start extends AppCompatActivity {

    RingProgressBar progressBar;
    TextView textTime;
    ImageButton imageButton;
    int progress = 0;

    int timeCountdown;


    Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0)
                if (progress < 100) {
                    if (progress % (int)(100/GlobalData.getTime()) == 0)
                    {
                        textTime.setText(String.valueOf(timeCountdown));
                        timeCountdown--;
                    }
                    progress++;
                    progressBar.setProgress(progress);
                    super.handleMessage(msg);
                }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_ask_anwser_layout);

        progressBar = (RingProgressBar) findViewById(R.id.processBarTime);
        textTime = (TextView) findViewById(R.id.textTime);
        imageButton = (ImageButton) findViewById(R.id.imageButton);

        timeCountdown = GlobalData.getTime();
        textTime.setText(String.valueOf(timeCountdown));



        imageButton.setOnClickListener(view -> {
            startCountDown();
        });

    }

    public void startCountDown(){
        long delay = GlobalData.getTime() * 10;
        new Thread(() -> {
            for (int i = 0 ; i < 100 ; i++){
                try {
                    Thread.sleep(delay);
                    myHandler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
