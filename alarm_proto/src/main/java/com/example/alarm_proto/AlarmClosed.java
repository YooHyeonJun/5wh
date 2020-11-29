package com.example.alarm_proto;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AlarmClosed extends AppCompatActivity {
    PendingIntent pendingIntent;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_closed);

        button = (Button)findViewById(R.id.shutup);

        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //알람 리시버 인텐트
                Intent al_intent = new Intent(AlarmClosed.this, Alarm_Receiver.class);
                // reveiver에 string 값 넘겨주기
                al_intent.putExtra("state","alarm off");
                pendingIntent = PendingIntent.getBroadcast(AlarmClosed.this,0,al_intent, PendingIntent.FLAG_UPDATE_CURRENT);
            }
        });
    }
}