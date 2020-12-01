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

    public void onClick(View v) {

        EditText_usertime= (EditText) findViewById(R.id.time_edit);
        String strSaveData = EditText_usertime.getText().toString();
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        SharedPreferences sharedPref1 = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedPref.edit();

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

        switch(v.getId()){
            case R.id.go_back_button:

                    this.onBackPressed();
                    break;

            case R.id.save_button:

                    editor.putString(getString(R.string.savedata_private_key), strSaveData);
                    editor.commit();
                    this.onBackPressed();
                    break;

        }


        RadioGroup.OnCheckedChangeListener RadioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (i == R.id.moving_speed_slow) {
                    String userspeed="slow";
                    editor.putString(getString(R.string.savedata_private_key1), userspeed);
                    editor.commit();

                }
                else if (i == R.id.moving_speed_normal) {
                    String userspeed="normal";
                    editor.putString(getString(R.string.savedata_private_key1), userspeed);
                    editor.commit();
                }
                else if (i == R.id.moving_speed_fast) {
                    String userspeed="fast";
                    editor.putString(getString(R.string.savedata_private_key1), userspeed);
                    editor.commit();

                }


            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String strLoadData = sharedPref.getString(getString(R.string.savedata_private_key), "Default Value");
        TextView_get=findViewById(R.id.TextView_get);
        TextView_get.setText(strLoadData);

        SharedPreferences sharedPref1 = this.getPreferences(Context.MODE_PRIVATE);
        String strLoadData1 = sharedPref.getString(getString(R.string.savedata_private_key), "Default Value");
        if(strLoadData=="slow")
        {
            r_btnSLOW.setChecked(true);
        }
        else if(strLoadData=="normal")
        {
            r_btnNORMAL.setChecked(true);
        }
        else if(strLoadData=="fast")
        {
            r_btnFAST.setChecked(true);
        }



    }}


