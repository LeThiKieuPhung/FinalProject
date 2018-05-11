package com.example.phungle.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainActivity extends AppCompatActivity {

    private Logger log = LoggerFactory.getLogger(MainActivity.class);
    private Button btn_sentence;
    private Button btn_ask_anwser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_sentence = (Button) findViewById(R.id.btn_sentence);
        btn_ask_anwser = (Button) findViewById(R.id.btn_ask_awnser);
        log.info("Start using app");
        btn_sentence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Sentence.class);
                startActivity(intent);
            }
        });
        btn_ask_anwser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AskAnwser.class);
                startActivity(intent);
            }
        });
    }
}
