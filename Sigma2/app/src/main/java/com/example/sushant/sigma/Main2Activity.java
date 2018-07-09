package com.example.sushant.sigma;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class Main2Activity extends AppCompatActivity {
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private MediaRecorder mRecorder = null;
    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};
    private TextView replyMessage;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            switch (requestCode){
                case REQUEST_RECORD_AUDIO_PERMISSION:
                    permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    break;
            }


            if (!permissionToRecordAccepted ) {
                Toast.makeText(this, "Please accept the request", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this,permissions,REQUEST_RECORD_AUDIO_PERMISSION);

            }
        }

    }



    // Yo bhanda mathi ko value chai microphone testing ko lagi ho :D
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        replyMessage=findViewById(R.id.replymessage);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ImageView img= findViewById(R.id.imageView);
        Button btn=findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Main2Activity.this, TextToSpeechDemo.class);
                startActivity(intent);
//                finish();
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(Main2Activity.this, "listening", Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(MainActivity.this,Main2Activity.class);
//                startActivity(intent);
                promptSpeechInput();

            }
            private void promptSpeechInput(){
//
                Intent i=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say Something");
//                System.out.println("Haha working thik ahileh samma");

                try{

                    startActivityForResult(i,3000);
                }
                catch(ActivityNotFoundException a){
                    Toast.makeText(Main2Activity.this, "Sorry your device doesn't support voice activity", Toast.LENGTH_LONG).show();
                }

            }
            });
    }


    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        switch(requestCode){
            case 3000:
                if(resultCode==RESULT_OK && data !=null){
                    ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//                     try{
//                        Toast.makeText(this, result.get(0), Toast.LENGTH_LONG).show();
//                        long SLEEP_TIME=5000;
//                        Thread.sleep(SLEEP_TIME);
//                    }
//                    catch(InterruptedException e){
//                        e.printStackTrace();
//                    }
                    if(result.get(0).equals("settings")){
                        startActivity(new Intent(Settings.ACTION_SETTINGS));
                    }
                    else if(result.get(0).equals("camera")){
                         startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
                    }
                    else{
                        Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
                    }

                }
                break;
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
