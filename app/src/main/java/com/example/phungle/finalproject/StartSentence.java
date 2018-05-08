package com.example.phungle.finalproject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

public class StartSentence extends AppCompatActivity {

    RingProgressBar progressBar;

    int progress = 0;

    Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0)
                if (progress < 100) {
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

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0 ; i < 100 ; i++){
                    try {
                        Thread.sleep(100);
                        myHandler.sendEmptyMessage(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
