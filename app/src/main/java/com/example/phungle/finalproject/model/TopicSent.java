package com.example.phungle.finalproject.model;

import java.util.ArrayList;
import java.util.List;

public class TopicSent {
    public String name;
    public List<String> listWord = new ArrayList<>();

    public TopicSent(String name) {
        this.name = name;
    }

    public TopicSent(String name, List<String> listWord) {
        this.name = name;
        this.listWord = listWord;
    }
}
