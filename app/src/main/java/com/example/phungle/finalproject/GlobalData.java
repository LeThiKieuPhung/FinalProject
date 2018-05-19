package com.example.phungle.finalproject;

import com.example.phungle.finalproject.model.Topic;
import com.example.phungle.finalproject.model.TopicSent;

import java.util.ArrayList;
import java.util.List;

public class GlobalData {

    public static List<Topic> listTopic = new ArrayList<>();
    public static List<TopicSent> listTopicSent = new ArrayList<>();
    public static int topicChoise = 0;
    public static int topicSentChoise = 0;
    public static int Level = 1;
    public static int time = 12 - 3;

    public static int getTime() {
        return 12 - 3 * GlobalData.Level;
    }
}
