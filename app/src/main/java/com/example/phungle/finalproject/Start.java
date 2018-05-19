package com.example.phungle.finalproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.RatingBar;
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

import org.apache.commons.lang3.StringUtils;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;
import io.vertx.core.json.JsonObject;

import static com.example.phungle.finalproject.Const.RECORD_REQUEST_CODE;
import static com.example.phungle.finalproject.Const.REQUEST_RECORD_AUDIO_PERMISSION;

public class Start extends AppCompatActivity {


    public String SENTENCE = "Sentence";
    public String ASKANWSER = "AskAnwser";
    String STT_username;
    String STT_password;

    RingProgressBar progressBar;
    TextView textTime;
    ImageButton imageButton;
    RatingBar ratingBar;

    TextView txtQues;
    TextView txtAnswer;

    AtomicInteger quesNumber;

    MicrophoneInputStream capture;
    SpeechToText speechService;
    MicrophoneHelper microphoneHelper;

    int progress = 0;
    boolean permissionToRecordAccepted = false;

    int timeCountdown;
    String Type;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_ask_anwser_layout);

        progressBar = (RingProgressBar) findViewById(R.id.processBarTime);
        textTime = (TextView) findViewById(R.id.textTime);
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        ratingBar = findViewById(R.id.ratingBar);

        txtQues = findViewById(R.id.txtQues);
        txtAnswer = findViewById(R.id.txtAnswer);

        Intent intent = getIntent();
        Type = intent.getStringExtra(Intent.EXTRA_TEXT);

        microphoneHelper = new MicrophoneHelper(this);
        STT_username = getBaseContext().getString(R.string.STT_username);
        STT_password = getBaseContext().getString(R.string.STT_password);

        timeCountdown = GlobalData.getTime();
        textTime.setText(String.valueOf(timeCountdown));
        quesNumber = new AtomicInteger(0);

        mixQuestion();


        if (Type.equals(ASKANWSER)) {
            txtQues.setText(GlobalData.listTopic.get(GlobalData.topicChoise).quesAndAnswerList.get(quesNumber.getAndIncrement()).Quesion);
        } else if (Type.equals(SENTENCE)) {
            txtQues.setText(GlobalData.listTopicSent.get(GlobalData.topicSentChoise).listWord.get(quesNumber.getAndIncrement()));
        }
        txtAnswer.setText("");
        imageButton.setOnClickListener(view -> {
            record();
        });

    }

    public void calculateResult() {

        String defaultAnswer = "";

        if (Type.equals(ASKANWSER)) {
            defaultAnswer =  GlobalData.listTopic.get(GlobalData.topicChoise).quesAndAnswerList.get(quesNumber.get() - 1).Answer;
        } else if (Type.equals(SENTENCE)) {
            defaultAnswer =  GlobalData.listTopicSent
                    .get(GlobalData.topicSentChoise).listWord.get(quesNumber.get() - 1);
        }

        String userAnswer = txtAnswer.getText().toString();


        if (StringUtils.isEmpty(userAnswer)) {
            Toast.makeText(this, "You don't speak anything", Toast.LENGTH_SHORT).show();
            return;
        }

        defaultAnswer = defaultAnswer.toLowerCase();
        userAnswer = userAnswer.toLowerCase();
        String[] listWordInUserAnswer = userAnswer.split(" ");
        int numberWordInDefaultAnswer = defaultAnswer.split(" ").length;
        int countNumberWordMatch = 0;

        if (defaultAnswer.equals(userAnswer)) {
            Toast.makeText(this, "Excellent", Toast.LENGTH_SHORT).show();
            ratingBar.setNumStars(5);
        } else {
            for (String word : listWordInUserAnswer) {
                if (defaultAnswer.contains(word)) {
                    countNumberWordMatch++;
                }
            }
            if (countNumberWordMatch > numberWordInDefaultAnswer) {
                Toast.makeText(this, "Good", Toast.LENGTH_SHORT).show();
                ratingBar.setNumStars(4);
            } else {
                float numberWordMatchPerStar = (float) numberWordInDefaultAnswer / 5;
                ratingBar.setRating((float) countNumberWordMatch / numberWordMatchPerStar);
            }
        }
    }

    public void startCountDown() {
        long delay = GlobalData.getTime() * 10;
        new Thread(() -> {
            for (int i = 0; i <= 100; i++) {
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
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
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

    public void mixQuestion() {
        Random random = new Random();
        if (Type.equals(ASKANWSER)) {
            int listSize = GlobalData.listTopic.get(GlobalData.topicChoise).quesAndAnswerList.size();
            int randomIndex = random.nextInt(listSize);

            for (int i = 0; i < listSize; i++) {
                QuesAndAnswer quesAndAnswer = GlobalData.listTopic.get(GlobalData.topicChoise).quesAndAnswerList.get(randomIndex);
                GlobalData.listTopic.get(GlobalData.topicChoise).quesAndAnswerList.remove(quesAndAnswer);
                GlobalData.listTopic.get(GlobalData.topicChoise).quesAndAnswerList.add(0, quesAndAnswer);
                randomIndex = random.nextInt(listSize);
            }
        } else if (Type.equals(SENTENCE)) {
            int listSize = GlobalData.listTopicSent.get(GlobalData.topicSentChoise).listWord.size();
            int randomIndex = random.nextInt(listSize);

            for (int i = 0; i < listSize; i++) {
                String content = GlobalData.listTopicSent.get(GlobalData.topicSentChoise)
                        .listWord.get(randomIndex);
                GlobalData.listTopicSent.get(GlobalData.topicSentChoise).listWord.remove(content);
                GlobalData.listTopicSent.get(GlobalData.topicSentChoise).listWord.add(0, content);
                randomIndex = random.nextInt(listSize);
            }
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        microphoneHelper.closeInputStream();
        finish();
    }


    @SuppressLint("HandlerLeak")
    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0)
                if (progress < 100) {
                    if (progress % (int) (100 / GlobalData.getTime()) == 0) {
                        textTime.setText(String.valueOf(timeCountdown));
                        timeCountdown--;
                    }
                    progress++;
                    progressBar.setProgress(progress);
                    super.handleMessage(msg);
                } else if (progress == 100) {
                    calculateResult();

                    final Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        if (Type.equals(ASKANWSER)) {
                            if (quesNumber.get() == GlobalData.listTopic.get(GlobalData.topicChoise).quesAndAnswerList.size()) {
                                Toast.makeText(Start.this, "The test is over", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } else if (Type.equals(SENTENCE)) {
                            if (quesNumber.get() == GlobalData.listTopicSent
                                    .get(GlobalData.topicSentChoise).listWord.size()){
                                Toast.makeText(Start.this,"The test is over",Toast.LENGTH_SHORT).show();
                                return;
                            }                        }

                        ratingBar.setNumStars(0);
                        ratingBar.setRating(0);
                        progress = 0;
                        if (microphoneHelper != null)
                            microphoneHelper.closeInputStream();
                        timeCountdown = GlobalData.getTime();
                        record();
                        if (Type.equals(ASKANWSER)) {
                            txtQues.setText(GlobalData.listTopic.get(GlobalData.topicChoise).quesAndAnswerList.get(quesNumber.getAndIncrement()).Quesion);
                        } else if (Type.equals(SENTENCE)) {
                            txtQues.setText(GlobalData.listTopicSent
                                    .get(GlobalData.topicSentChoise).listWord
                                    .get(quesNumber.getAndIncrement()));

                        }
                        txtAnswer.setText("");
                    }, 3000);


                    super.handleMessage(msg);

                }
        }
    };

    private class MicrophoneRecognizeDelegate implements RecognizeCallback {
        @Override
        public void onTranscription(SpeechResults speechResults) {
            if (speechResults.getResults() != null && !speechResults.getResults().isEmpty()) {
                String text = speechResults.getResults().get(0).getAlternatives().get(0).getTranscript();

                if (!text.equals("%hesitation") && !text.equals("%HESITATION")) {
                    String newText = text;
                    runOnUiThread(() -> txtAnswer.setText(newText));
                }
            }
        }

        @Override
        public void onConnected() {
            runOnUiThread(() -> {
                Toast.makeText(Start.this, "Listening", Toast.LENGTH_LONG).show();
                startCountDown();
            });
        }

        @Override
        public void onError(Exception e) {
            showError(e);
        }

        @Override
        public void onDisconnected() {

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
