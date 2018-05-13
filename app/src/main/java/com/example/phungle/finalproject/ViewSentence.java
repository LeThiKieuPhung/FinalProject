package com.example.phungle.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class ViewSentence extends AppCompatActivity {

    private Button btn_add;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_sentence_layout);
        getSupportActionBar().setHomeButtonEnabled(true);
        btn_add = (Button) findViewById(R.id.btn_add_sentence_view);
        btn_add.setOnClickListener(view -> {
            Intent intent = new Intent(ViewSentence.this, AddSentence.class);
            startActivity(intent);
        });
    }
}
