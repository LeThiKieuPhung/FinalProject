package com.example.phungle.finalproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Setting extends AppCompatActivity {

    RadioGroup radioGroupLevel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);

        getSupportActionBar().setHomeButtonEnabled(true);

        radioGroupLevel = (RadioGroup) findViewById(R.id.radioGroupLevel);

        checkRadioButtonLevel();

        radioGroupLevel.setOnCheckedChangeListener((radioGroup, i) -> {
            switch(i){
                case R.id.radioLevel1:
                    GlobalData.Level = 1;
                    break;
                case R.id.radioLevel2:
                    GlobalData.Level = 2;
                    break;
                case R.id.radioLevel3:
                    GlobalData.Level = 3;
                    break;
            }
            Toast.makeText(getBaseContext(),"Level set to " + GlobalData.Level,Toast.LENGTH_SHORT).show();
        });
    }

    public void checkRadioButtonLevel(){
        switch (GlobalData.Level){
            case 1:
                radioGroupLevel.check(R.id.radioLevel1);
                break;
            case 2:
                radioGroupLevel.check(R.id.radioLevel2);
                break;
            case 3:
                radioGroupLevel.check(R.id.radioLevel3);
                break;
        }
    }
}
