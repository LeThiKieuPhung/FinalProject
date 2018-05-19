package com.example.phungle.finalproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phungle.finalproject.model.QuesAndAnswer;
import com.example.phungle.finalproject.model.Topic;
import com.example.phungle.finalproject.model.TopicSent;

import org.apache.commons.lang3.StringUtils;

public class AddSentence extends AppCompatActivity {

    private EditText edit_topic_sent,edit_content;
    private Button btn_add_to_sentence_file;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_sentence_view);
        getSupportActionBar().setHomeButtonEnabled(true);

        edit_topic_sent = findViewById(R.id.edit_topic_sent);
        edit_content = findViewById(R.id.edit_content);

        btn_add_to_sentence_file = findViewById(R.id.btn_add_to_sentence_file);

        btn_add_to_sentence_file.setOnClickListener(view -> {
            String topicName = edit_topic_sent.getText().toString();
            String content = edit_content.getText().toString();
            if (StringUtils.isEmpty(topicName) || StringUtils.isEmpty(content)){
                Toast.makeText(this, "Name topic empty or Content empty", Toast.LENGTH_SHORT).show();
                return;
            }
            int i;
            for (i = 0 ; i < GlobalData.listTopicSent.size(); i++){
                if (GlobalData.listTopicSent.get(i).name.equals(topicName)){
                    break;
                }
            }
            if (i == GlobalData.listTopicSent.size()){
                TopicSent newTopic = new TopicSent(topicName);
                newTopic.listWord.add(content);
                GlobalData.listTopicSent.add(newTopic);
            }
            else{
                GlobalData.listTopicSent.get(i).listWord.add(content);
            }
            edit_content.setText("");
            Toast.makeText(this,"Add done",Toast.LENGTH_SHORT).show();
        });
    }
}
