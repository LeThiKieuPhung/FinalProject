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
import com.example.phungle.finalproject.model.Topic;
import com.example.phungle.finalproject.model.TopicSent;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

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
        btn_sentence.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Sentence.class);
            startActivity(intent);
        });
        btn_ask_anwser.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AskAnwser.class);
            startActivity(intent);
        });
    }

    private void initSomeData() {


        List<QuesAndAnswer> list = new ArrayList<>();
        list.add(new QuesAndAnswer("How are you ?","I'm fine"));
        list.add(new QuesAndAnswer("Good Morning","Good Morning"));
        list.add(new QuesAndAnswer("what’s your name?","my name’s Nancy"));
        list.add(new QuesAndAnswer("How was your day?","The same as usua"));
        GlobalData.listTopic.add(new Topic("Topic 1",list));

        List<QuesAndAnswer> list2 = new ArrayList<>();
        list2.add(new QuesAndAnswer("How old are you ?","I 15 year old"));
        list2.add(new QuesAndAnswer("Would you like some tea ?","Yes I do"));
        list2.add(new QuesAndAnswer("Do you like travel ?","Yes. I love traveling"));
        list2.add(new QuesAndAnswer("4\tWhat do you have for breakfast ?",
                "I eat sandwich and drink milk"));
        GlobalData.listTopic.add(new Topic("Topic 2",list2));

        List<String> list3= new ArrayList<>();
        list3.add("My name’s John");
        list3.add("I’m busy now");
        list3.add("What’s up");
        list3.add("Don’t forget to close the door");
        list3.add("Is there anything else? ");
        GlobalData.listTopicSent.add(new TopicSent("Topic 1",list3));

        List<String> list4 = new ArrayList<>();
        list4.add("Let’s have dinner");
        list4.add("I got flu What should I do");
        list4.add("I like spending time with my family");
        list4.add("See you later");
        list4.add("See you soon");
        GlobalData.listTopicSent.add(new TopicSent("Topic 2",list4));
    }
}
