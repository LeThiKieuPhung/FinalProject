package com.example.phungle.finalproject.model;

import java.util.ArrayList;
import java.util.List;

public class Topic {
    public String name;
    public List<QuesAndAnswer> quesAndAnswerList;

    public Topic(String name, List<QuesAndAnswer> quesAndAnswerList) {
        this.name = name;
        this.quesAndAnswerList = quesAndAnswerList;
    }

    public Topic(String name) {
        this.name = name;
        this.quesAndAnswerList = new ArrayList<>();
    }
}
