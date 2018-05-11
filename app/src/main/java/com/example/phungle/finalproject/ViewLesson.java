package com.example.phungle.finalproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.example.phungle.finalproject.model.QuesAndAnswer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ViewLesson extends AppCompatActivity {


    public Button btnAddFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_ask_anwser_layout);
        btnAddFile = (Button) findViewById(R.id.btn_add_ask_anwser_view);
        btnAddFile.setOnClickListener(view -> {
            Intent intent = new Intent()
                    .setType("*/*")
                    .setAction(Intent.ACTION_GET_CONTENT);

            startActivityForResult(Intent.createChooser(intent, "Select a file"), 123);
        });
    }

    /**
     * format file
     * Hello how are you | i'm fine thank and you
     * Hello how are you | i'm fine thank and you
     * Hello how are you | i'm fine thank and you
     * Hello how are you | i'm fine thank and you
     * */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==123 && resultCode==RESULT_OK) {
            Uri selectedfile = data.getData();
            Log.d("Path???", selectedfile.getPath());
            BufferedReader reader = null;
            try {

                InputStream in = getContentResolver().openInputStream(selectedfile);
                reader = new BufferedReader(new InputStreamReader(in));
                String line;

                while ((line = reader.readLine()) != null){
                    String [] buffer = line.split("|");
                    if (buffer.length == 2)
                    {
                        GlobalData.listQuesAndAnswer.add(new QuesAndAnswer(buffer[0],buffer[1]));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
