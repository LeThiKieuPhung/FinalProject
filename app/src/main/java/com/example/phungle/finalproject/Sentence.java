package com.example.phungle.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageButton;

public class Sentence extends AppCompatActivity {

    private Button btn_start;
    private Button btn_view;
    private ImageButton btn_setting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sentence_layout);

        getSupportActionBar().setHomeButtonEnabled(true);
        btn_start = (Button) findViewById(R.id.btn_start_sentence);
        btn_view = (Button) findViewById(R.id.btn_view_sentence);
        btn_setting = (ImageButton) findViewById(R.id.btn_setting);

        btn_start.setOnClickListener(view -> {
            Intent intent = new Intent(Sentence.this, Start.class);
            startActivity(intent);
        });
        btn_view.setOnClickListener(view -> {
            Intent intent = new Intent(Sentence.this, ViewSentence.class);
            startActivity(intent);
        });
        btn_setting.setOnClickListener(view -> {
            Intent intent = new Intent(Sentence.this, Setting.class);
            startActivity(intent);
        });
    }
}
