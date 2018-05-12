package com.example.phungle.finalproject;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phungle.finalproject.model.QuesAndAnswer;
import com.ibm.watson.developer_cloud.android.library.audio.MicrophoneHelper;
import com.ibm.watson.developer_cloud.android.library.audio.MicrophoneInputStream;
import com.ibm.watson.developer_cloud.android.library.audio.utils.ContentType;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.websocket.RecognizeCallback;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;
import io.vertx.core.json.JsonObject;

import static com.example.phungle.finalproject.Const.RECORD_REQUEST_CODE;
import static com.example.phungle.finalproject.Const.REQUEST_RECORD_AUDIO_PERMISSION;

public class Start extends AppCompatActivity {
    String STT_username;
    String STT_password;

    RingProgressBar progressBar;
    TextView textTime;
    ImageButton imageButton;

    TextView txtQues;
    TextView txtAnswer;

    AtomicInteger quesNumber;

    MicrophoneInputStream capture;
    SpeechToText speechService;
    MicrophoneHelper microphoneHelper;

    int progress = 0;
    boolean permissionToRecordAccepted = false;

    int timeCountdown;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_ask_anwser_layout);

        progressBar = (RingProgressBar) findViewById(R.id.processBarTime);
        textTime = (TextView) findViewById(R.id.textTime);
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        txtQues = findViewById(R.id.txtQues);
        txtAnswer = findViewById(R.id.txtAnswer);

        microphoneHelper = new MicrophoneHelper(this);
        STT_username = getBaseContext().getString(R.string.STT_username);
        STT_password = getBaseContext().getString(R.string.STT_password);

        timeCountdown = GlobalData.getTime();
        textTime.setText(String.valueOf(timeCountdown));
        quesNumber = new AtomicInteger(0);
        mixQuestion();


        txtQues.setText(GlobalData.listQuesAndAnswer.get(quesNumber.getAndIncrement()).Quesion);
        txtAnswer.setText("");
        imageButton.setOnClickListener(view -> {
            record();
        });

    }

    public void startCountDown(){
        long delay = GlobalData.getTime() * 10;
        new Thread(() -> {
            for (int i = 0 ; i <= 100 ; i++){
                try {
                    Thread.sleep(delay);
                    myHandler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
            case RECORD_REQUEST_CODE: {

                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {

                    Log.i(this.getPackageName(), "Permission has been denied by user");
                } else {
                    Log.i(this.getPackageName(), "Permission has been granted by user");
                }
                return;
            }

            case MicrophoneHelper.REQUEST_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission to record audio denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
        // if (!permissionToRecordAccepted ) finish();

    }

    public void mixQuestion(){
        Random random = new Random();
        int listSize = GlobalData.listQuesAndAnswer.size();
        int randomIndex = random.nextInt(listSize);

        for (int i = 0 ; i < listSize ; i++){
            QuesAndAnswer quesAndAnswer = GlobalData.listQuesAndAnswer.get(randomIndex);
            GlobalData.listQuesAndAnswer.remove(quesAndAnswer);
            GlobalData.listQuesAndAnswer.add(0, quesAndAnswer);
            randomIndex = random.nextInt(listSize);
        }
    }

    private void record() {
        speechService = new SpeechToText();
        speechService.setUsernameAndPassword(STT_username, STT_password);


        capture = microphoneHelper.getInputStream(true);
        new Thread(() -> {
            try {
                speechService.recognizeUsingWebSocket(capture,
                        getRecognizeOptions(),
                        new MicrophoneRecognizeDelegate());
            } catch (Exception e) {
                showError(e);
            }
        }).start();


    }

    public RecognizeOptions getRecognizeOptions() {
        return new RecognizeOptions.Builder()
                .contentType(ContentType.OPUS.toString())
                .interimResults(true)
                .inactivityTimeout(1000)
                .build();
    }

    @SuppressLint("HandlerLeak")
    Handler myHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0)
                if (progress < 100) {
                    if (progress % (int)(100/GlobalData.getTime()) == 0)
                    {
                        textTime.setText(String.valueOf(timeCountdown));
                        timeCountdown--;
                    }
                    progress++;
                    progressBar.setProgress(progress);
                    super.handleMessage(msg);
                }
                else if (progress == 100){
                    try {
                        Thread.sleep(10); // delay to show star or something
                        // or do something bla bla bla

                        progress = 0;
                        if (microphoneHelper != null)
                            microphoneHelper.closeInputStream();
                        timeCountdown = GlobalData.getTime();
                        record();

                        txtQues.setText(GlobalData.listQuesAndAnswer.get(quesNumber.getAndIncrement()).Quesion);
                        txtAnswer.setText("");
                        super.handleMessage(msg);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
        }
    };

    private class MicrophoneRecognizeDelegate implements RecognizeCallback {
        @Override
        public void onTranscription(SpeechResults speechResults) {
            if(speechResults.getResults() != null && !speechResults.getResults().isEmpty()) {
                String text = speechResults.getResults().get(0).getAlternatives().get(0).getTranscript();
                runOnUiThread(() -> txtAnswer.setText(text));
            }
        }

        @Override public void onConnected() {
            runOnUiThread(() -> {
                Toast.makeText(Start.this,"Listening", Toast.LENGTH_LONG).show();
                startCountDown();
            });
        }

        @Override public void onError(Exception e) {
            showError(e);
        }

        @Override public void onDisconnected() {

        }

        @Override
        public void onInactivityTimeout(RuntimeException runtimeException) {

        }

        @Override
        public void onListening() {

        }

        @Override
        public void onTranscriptionComplete() {

        }
    }


    private void showError(final Exception e) {
        runOnUiThread(() -> {
            Toast.makeText(Start.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        });
    }
}
