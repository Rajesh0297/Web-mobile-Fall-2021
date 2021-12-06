package com.vijaya.speechtotext;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView mVoiceInputTv;
    private ImageButton mSpeakBtn;
    private String CurrentSpeech;
    private TextToSpeech t1;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    public static final String PREFS_NAME = "mypref";
    private static final String name = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CurrentSpeech = "Hello, How can I help you?";
        preferences = getSharedPreferences(PREFS_NAME, 0);
        editor = preferences.edit();
        mVoiceInputTv = (TextView) findViewById(R.id.voiceInput);
        mSpeakBtn = (ImageButton) findViewById(R.id.btnSpeak);
        mSpeakBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startVoiceInput();
            }
        });
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                    String toSpeak = CurrentSpeech;
                    Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
        mVoiceInputTv.setText(CurrentSpeech);
    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if(result.get(0).equalsIgnoreCase("hello")){
                        mVoiceInputTv.append("\n\t\t\t"+result.get(0).toUpperCase(Locale.ROOT));
                        CurrentSpeech = "What's your name?";
                        mVoiceInputTv.append("\n"+ CurrentSpeech);
                        Toast.makeText(getApplicationContext(), CurrentSpeech,Toast.LENGTH_SHORT).show();
                        t1.speak(CurrentSpeech, TextToSpeech.QUEUE_FLUSH, null);
                    }
                    else if(result.get(0).toLowerCase(Locale.ROOT).contains("my name is")){
                        String tempName = result.get(0).split("name is")[1];
                        editor.putString(name, tempName).apply();
                        mVoiceInputTv.append("\n\t\t\t"+ tempName);
                    }
                    else if(result.get(0).toLowerCase(Locale.ROOT).contains("not feeling well")){
                        mVoiceInputTv.append("\n\t\t\t"+ result.get(0));
                        CurrentSpeech = "I can understand. Please tell your symptoms in short";
                        mVoiceInputTv.append("\n"+ CurrentSpeech);
                        Toast.makeText(getApplicationContext(), CurrentSpeech,Toast.LENGTH_SHORT).show();
                        t1.speak(CurrentSpeech, TextToSpeech.QUEUE_FLUSH, null);
                    }
                    else if(result.get(0).toLowerCase(Locale.ROOT).contains("thank you")){
                        mVoiceInputTv.append("\n\t\t\t"+ result.get(0));
                        if(preferences.getString(name, null)!=null)
                            CurrentSpeech = "Thank you too "+ preferences.getString(name, null) +" Take care";
                        else
                            CurrentSpeech = "Thank you too take care";

                        mVoiceInputTv.append("\n"+ CurrentSpeech);
                        Toast.makeText(getApplicationContext(), CurrentSpeech,Toast.LENGTH_SHORT).show();
                        t1.speak(CurrentSpeech, TextToSpeech.QUEUE_FLUSH, null);
                    }
                    else if(result.get(0).toLowerCase(Locale.ROOT).contains("what time")){
                        mVoiceInputTv.append("\n\t\t\t"+ result.get(0));
                        SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm");//dd/MM/yyyy
                        Date now = new Date();
                        String[] strDate = sdfDate.format(now).split(":");
                        if(strDate[1].contains("00"))
                            strDate[1] = "o'clock";
                        CurrentSpeech = "The time is "+ sdfDate.format(now);
                        mVoiceInputTv.append("\n"+ CurrentSpeech);
                        Toast.makeText(getApplicationContext(), CurrentSpeech,Toast.LENGTH_SHORT).show();
                        t1.speak(CurrentSpeech, TextToSpeech.QUEUE_FLUSH, null);
                    }
                    else if(result.get(0).toLowerCase(Locale.ROOT).contains("what medicine")){
                        mVoiceInputTv.append("\n\t\t\t"+ result.get(0));
                        CurrentSpeech = "I think you have a fever. Please take this medicine.";
                        mVoiceInputTv.append("\n"+ CurrentSpeech);
                        Toast.makeText(getApplicationContext(), CurrentSpeech,Toast.LENGTH_SHORT).show();
                        t1.speak(CurrentSpeech, TextToSpeech.QUEUE_FLUSH, null);
                    }
                    else{
                        mVoiceInputTv.append("\n\t\t\t"+ result.get(0));
                        CurrentSpeech = "No Action is associated with this command";
                        Toast.makeText(getApplicationContext(), CurrentSpeech,Toast.LENGTH_SHORT).show();
                        t1.speak(CurrentSpeech, TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
                break;
            }

        }
    }
}