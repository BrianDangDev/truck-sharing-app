package com.data.trucksharingapp;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class feedbackPage extends AppCompatActivity {
    TextView textSpoken;
    ImageView micIcon;

    // resource: https://www.geeksforgeeks.org/how-to-convert-speech-to-text-in-android/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_page);
        textSpoken = findViewById(R.id.textSpoken);
        micIcon = findViewById(R.id.micIcon);
    }
    public void speak(View view){
        //  begins an activity that prompts the user to speak and sends it via voice recognition.
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent, 100);
    }

    // returns strings
    // replaces text in the textview
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK){
            textSpoken.setText(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0));
        }
    }
}