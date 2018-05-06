package com.example.phungle.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AskAnwser extends AppCompatActivity{

    private Button btn_start;
    private Button btn_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ask_anwser_layout);

        btn_start = (Button) findViewById(R.id.btn_start_ask_anwser);
        btn_view = (Button) findViewById(R.id.btn_view_ask_anwser);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AskAnwser.this, StartAskAnwser.class);
                startActivity(intent);
            }
        });
        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AskAnwser.this, ViewAskAnwser.class);
                startActivity(intent);
            }
        });
    }
}
