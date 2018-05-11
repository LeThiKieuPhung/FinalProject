package com.example.phungle.finalproject;

import com.example.phungle.finalproject.model.QuesAndAnswer;

import java.util.ArrayList;
import java.util.List;

public class GlobalData {

    public static final List<QuesAndAnswer> listQuesAndAnswer = new ArrayList<>();
    public static int Level = 1;
    public static int time = 12 - 3;

    public static int getTime() {
        return 12 - 3 * GlobalData.Level;
    }
}
