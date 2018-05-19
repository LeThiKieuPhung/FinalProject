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
import com.example.phungle.finalproject.adapter.ExpandableListviewSentAdapter;

public class ViewSentence extends AppCompatActivity {

    private Button btn_add;
    private ExpandableListView expand_list_view_sentence;
    private ExpandableListviewSentAdapter expandableListviewAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_sentence_layout);
        getSupportActionBar().setHomeButtonEnabled(true);
        btn_add = (Button) findViewById(R.id.btn_add_sentence_view);
        expand_list_view_sentence = findViewById(R.id.expand_list_view_sentence);
        expandableListviewAdapter = new ExpandableListviewSentAdapter(this,GlobalData.listTopicSent);
        expand_list_view_sentence.setAdapter(expandableListviewAdapter);
        btn_add.setOnClickListener(view -> {
            Intent intent = new Intent(ViewSentence.this, AddSentence.class);
            startActivity(intent);
        });
        expand_list_view_sentence.setOnGroupClickListener((expandableListView, view, i, l) -> {
            if (i != GlobalData.topicSentChoise) {
                Toast.makeText(this, "You choose " +
                        GlobalData.listTopicSent.get(i).name + "to use", Toast.LENGTH_SHORT).show();
                GlobalData.topicSentChoise = i;
            }
            return false;
        });
    }
}
