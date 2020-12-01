package com.example.alarm_proto;


import android.content.Context;
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
import android.widget.TextView;

import java.text.BreakIterator;
import java.util.prefs.AbstractPreferences;

public class UserSetting extends AppCompatActivity {
    EditText EditText_usertime;
    TextView TextView_get;
    RadioButton r_btnSLOW, r_btnNORMAL, r_btnFAST;//라디오 버튼
    RadioGroup radioGroup;
    String mUsrspeed;

    public void onClick(View v) {

        EditText_usertime= (EditText) findViewById(R.id.time_edit);
        String strSaveData = EditText_usertime.getText().toString();
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();


        switch(v.getId()){
            case R.id.go_back_button:

                this.onBackPressed();
                break;

            case R.id.save_button:

                editor.putString("usr_speed",mUsrspeed);
                if(!strSaveData.equals(""))
                    editor.putString("usr_time",strSaveData);
                editor.apply();
                this.onBackPressed();
                break;

        }



    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
        View.OnClickListener radioButtonClickListener = null;

        r_btnSLOW = (RadioButton) findViewById(R.id.moving_speed_slow);
        r_btnNORMAL = (RadioButton) findViewById(R.id.moving_speed_normal);
        r_btnFAST = (RadioButton) findViewById(R.id.moving_speed_fast);

        r_btnSLOW.setOnClickListener(radioButtonClickListener);
        r_btnNORMAL.setOnClickListener(radioButtonClickListener);
        r_btnFAST.setOnClickListener(radioButtonClickListener);

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String strLoadData = sharedPref.getString("usr_time", "0");
        String strSpeedData = sharedPref.getString("usr_speed", "normal");
        TextView_get=findViewById(R.id.time_edit);
        TextView_get.setText(strLoadData);

        if(strSpeedData=="slow")
        {
            r_btnSLOW.setChecked(true);
        }
        else if(strSpeedData=="normal")
        {
            r_btnNORMAL.setChecked(true);
        }
        else if(strSpeedData=="fast")
        {
            r_btnFAST.setChecked(true);
        }

        radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                if (i == R.id.moving_speed_slow) {
                    String userspeed="slow";
                    mUsrspeed = userspeed;

                }
                else if (i == R.id.moving_speed_normal) {
                    String userspeed="normal";
                    mUsrspeed = userspeed;
                }
                else if (i == R.id.moving_speed_fast) {
                    String userspeed="fast";
                    mUsrspeed = userspeed;

                }
            }
        });


    }
}


