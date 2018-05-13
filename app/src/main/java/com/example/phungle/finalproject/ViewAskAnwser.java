package com.example.phungle.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class ViewAskAnwser extends AppCompatActivity {

    private Button btn_add;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_ask_anwser_layout);
        getSupportActionBar().setHomeButtonEnabled(true);
        btn_add = (Button) findViewById(R.id.btn_add_ask_anwser_view);
        btn_add.setOnClickListener(view -> {
            Intent intent = new Intent(ViewAskAnwser.this, AddAskAnwser.class);
            startActivity(intent);
        });
    }
}
