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

import org.apache.commons.lang3.StringUtils;

public class AddAskAnwser extends AppCompatActivity {

    private EditText edit_answer,edit_question,edit_topic;
    private Button btn_add_to_ask_anwser_file;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ask_anwser_view);
        getSupportActionBar().setHomeButtonEnabled(true);

        edit_answer = findViewById(R.id.edit_answer);
        edit_question = findViewById(R.id.edit_question);
        edit_topic = findViewById(R.id.edit_topic);

        btn_add_to_ask_anwser_file = findViewById(R.id.btn_add_to_ask_anwser_file);

        btn_add_to_ask_anwser_file.setOnClickListener(view -> {
            if (StringUtils.isEmpty(edit_answer.getText().toString()) ||
                    StringUtils.isEmpty(edit_question.getText().toString()) ||
                    StringUtils.isEmpty(edit_topic.getText().toString()))
            {
                Toast.makeText(this,"Please fill in all fields",Toast.LENGTH_SHORT).show();
            }
            else{
                int i;
                for (i = 0 ; i < GlobalData.listTopic.size(); i++){
                    if (GlobalData.listTopic.get(i).name.equals(edit_topic.getText().toString())){
                        break;
                    }
                }
                if (i == GlobalData.listTopic.size()){
                    Topic newTopic = new Topic(edit_topic.getText().toString());
                    newTopic.quesAndAnswerList.add(new QuesAndAnswer(edit_question.getText().toString(),
                            edit_answer.getText().toString()));
                    GlobalData.listTopic.add(newTopic);
                }
                else{
                    GlobalData.listTopic.get(i).quesAndAnswerList.add(new QuesAndAnswer(edit_question.getText().toString(),
                            edit_answer.getText().toString()));
                }
                edit_question.setText("");
                edit_answer.setText("");
                Toast.makeText(this,"Add done",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
