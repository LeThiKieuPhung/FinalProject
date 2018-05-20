package com.example.phungle.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.phungle.finalproject.adapter.ExpandableListviewAdapter;

public class ViewAskAnwser extends AppCompatActivity {

    private Button btn_add;
    private ExpandableListView expand_list_view_ask_answer;
    private ExpandableListviewAdapter expandableListviewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_ask_anwser_layout);
        getSupportActionBar().setHomeButtonEnabled(true);
        btn_add = (Button) findViewById(R.id.btn_add_ask_anwser_view);
        expand_list_view_ask_answer = findViewById(R.id.expand_list_view_ask_answer);
        expandableListviewAdapter = new ExpandableListviewAdapter(this,GlobalData.listTopic);
        expand_list_view_ask_answer.setAdapter(expandableListviewAdapter);
        btn_add.setOnClickListener(view -> {
            Intent intent = new Intent(ViewAskAnwser.this, AddAskAnwser.class);
            startActivity(intent);
        });
        expand_list_view_ask_answer.setOnGroupClickListener((expandableListView, view, i, l) -> {
            if (i != GlobalData.topicChoise) {
                Toast.makeText(ViewAskAnwser.this, "You choose " +
                        GlobalData.listTopic.get(i).name + "to use", Toast.LENGTH_SHORT).show();
                GlobalData.topicChoise = i;
            }
            return false;
        });
    }
}
