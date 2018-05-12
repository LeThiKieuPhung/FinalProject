package com.example.phungle.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.phungle.finalproject.model.QuesAndAnswer;

import org.json.JSONObject;
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

        initSomeData();


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

    private void initSomeData() {
        GlobalData.listQuesAndAnswer.add(new QuesAndAnswer("How are you ?","I fine"));
        GlobalData.listQuesAndAnswer.add(new QuesAndAnswer("GoodMorning","GoodMorning"));
        GlobalData.listQuesAndAnswer.add(new QuesAndAnswer("How old are you ?","I 15 year old"));
        GlobalData.listQuesAndAnswer.add(new QuesAndAnswer("Would you like some tea ?","Yes I do"));
    }
}
