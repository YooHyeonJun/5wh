package com.example.alarm_proto;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.prefs.AbstractPreferences;

public class UserSetting extends AppCompatActivity {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    public void onClick(View v) {

        Button send;
        EditText time_edit;
        time_edit = (EditText)findViewById(R.id.time_edit);

        switch(v.getId()){
            case R.id.go_back_button:
                    this.onBackPressed();
                    break;

            case R.id.save_button:
                    String time = time_edit.getText().toString();
                    editor.putInt("time", Integer.parseInt(time));
                    editor.apply();
                    this.onBackPressed();
                    break;
        }
        RadioButton r_btnSLOW, r_btnNORMAL, r_btnFAST;//라디오 버튼
        RadioGroup radioGroup;
        View.OnClickListener radioButtonClickListener = null;
        r_btnSLOW = (RadioButton) findViewById(R.id.moving_speed_slow);
        r_btnNORMAL = (RadioButton) findViewById(R.id.moving_speed_normal);
        r_btnFAST = (RadioButton) findViewById(R.id.moving_speed_fast);

        r_btnSLOW.setOnClickListener(radioButtonClickListener);
        r_btnNORMAL.setOnClickListener(radioButtonClickListener);
        r_btnFAST.setOnClickListener(radioButtonClickListener);

        radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);
        RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = null;
        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);

        RadioGroup.OnCheckedChangeListener RadioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (i == R.id.moving_speed_slow) {

                    editor.putInt("speed",75/100);
                    editor.apply();
                }
                else if (i == R.id.moving_speed_normal) {

                    editor.putInt("speed",1);
                    editor.apply();
                }
                else if (i == R.id.moving_speed_fast) {

                    editor.putInt("speed",125/100);
                    editor.apply();
                }


            }
        };






};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();




    }
}
