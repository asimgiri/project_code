package com.example.sushant.sigma;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class TextToSpeechDemo extends AppCompatActivity {
    EditText editText;
    TextToSpeechDemo txtToSpeech;
    private Button start,stop;

    String test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);
        editText=findViewById(R.id.getText);
        this.start=findViewById(R.id.start);
        this.stop=findViewById(R.id.stop);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test=editText.getText().toString();
                System.out.println(test);
                Toast.makeText(TextToSpeechDemo.this, "Opening", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Settings.ACTION_SETTINGS));
            }
        });


    }
}
