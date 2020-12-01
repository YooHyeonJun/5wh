package com.example.alarm_proto;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import android.support.v7.app.AppCompatActivity;

import java.util.Calendar;

public class AlarmConfig extends AppCompatActivity {
    AlarmManager alarm_manager; //알람 메니저
    PendingIntent pendingIntent;
    EditText estimate_et;

    public void onClickAlarmConfig(View v){
        //알람 메니저 설정
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        EditText et = (EditText)findViewById(R.id.recom_input);
        Intent routeIntent = new Intent(this, RouteConfig.class);
        //알람 리시버 인텐트 생성
        final Intent al_intent = new Intent(this, Alarm_Receiver.class);
        //캘린더 객체 생성
        final Calendar calendar = Calendar.getInstance();
        switch(v.getId()){
            case R.id.route_sel: startActivityForResult(routeIntent,0); //경로 선택 버튼
            break;
            case R.id.alarmcon_back: this.onBackPressed(); //뒤로가기
            break;
            case R.id.config_finish: //알람 설정 완료 버튼
            //AlarmManagement로 결과값 전송
                String time = et.getText().toString();
                Intent intent = new Intent(AlarmConfig.this, AlarmManagement.class);
                if(et.getText().toString().length() == 0){
                    Toast myToast = Toast.makeText(this.getApplicationContext(),"input time first", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else{
                    intent.putExtra("alarm_clock",time);
                    setResult(0,intent);
                    finish();
                }

                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_config);
        estimate_et = (EditText) findViewById(R.id.estitime_input);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode, data);
        if(resultCode == 0) {
            int val = data.getIntExtra("time",0);
            estimate_et.setText(Integer.toString(val));

        }
    }
    protected  void onSaveInstatnceState(Bundle outState){
        super.onSaveInstanceState(outState);
        //저장할 변수 여기에
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            //start_et.setText(savedInstanceState.getString("BACKUP_START"));
            //finish_et.setText(savedInstanceState.getString("BACKUP_FINISH"));

            //복구 작업 여기에
        }

    }
}