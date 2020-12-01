package com.example.alarm_proto;

import android.os.Bundle;
import android.view.View;

import android.support.v7.app.AppCompatActivity;

public class DestinationSearch extends AppCompatActivity {
    public void onClick(View v){

        switch(v.getId()){
            case R.id.go_back_button:
                this.onBackPressed();
                break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_search);
    }
}