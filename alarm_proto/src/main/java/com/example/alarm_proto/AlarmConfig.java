package com.example.alarm_proto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

public class AlarmConfig extends AppCompatActivity {
    EditText hour;
    EditText minute;
    EditText estitime_input;
    TextView recom_input;
    Button set;



    AlarmManager alarm_manager; //알람 메니저
    PendingIntent pendingIntent;

    public void onClickAlarmConfig(View v){

        //알람 메니저 설정
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        TextView et = (TextView)findViewById(R.id.recom_input);
        Intent routeIntent = new Intent(this,RouteConfig.class);
        //알람 리시버 인텐트 생성
        final Intent al_intent = new Intent(this, Alarm_Receiver.class);
        //캘린더 객체 생성
        final Calendar calendar = Calendar.getInstance();
        switch(v.getId()) {
            case R.id.set_time:
                hour = (EditText) findViewById(R.id.promise_o);
                minute = (EditText) findViewById(R.id.promise_clock);
                estitime_input = (EditText) findViewById(R.id.estitime_input);
                recom_input = (TextView) findViewById(R.id.recom_input);
                set = (Button) findViewById(R.id.set_time);

                String s1 = hour.getText().toString();//시 값 가져오기(xml->java)
                String s2 = minute.getText().toString();//분 값 가져오기(xml->java)
                String s3 = estitime_input.getText().toString();//예측 값 가져오기(xml->java)
                int result = (Integer.parseInt(s1) * 60 + Integer.parseInt(s2) - Integer.parseInt(s3));
                int result1 = result / 60;
                int result2 = result % 60;
                recom_input.setText(result1 + "시" + result2 + "분");


                break;
            case R.id.route_sel: startActivity(routeIntent); //경로 선택 버튼
            break;
            case R.id.alarmcon_back: this.onBackPressed(); //뒤로가기
            break;
            case R.id.config_finish: //알람 설정 완료 버튼
            //리시버에 string값 넘겨주기

                String time = et.getText().toString();

                if ( et.getText().toString().length() == 0 ) {

                    Toast myToast = Toast.makeText(this.getApplicationContext(),"input time first", Toast.LENGTH_SHORT);
                    myToast.show();
                    break;

                } else {
                    String[] arr= time.split(":");
                    int hour, minute;
                    hour = Integer.parseInt(arr[0]);
                    minute = Integer.parseInt(arr[1]);
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE,minute);
                    calendar.set(Calendar.SECOND, 0);

                    Toast.makeText(this,"Alarm 예정 " + hour + "시 " + minute + "분",Toast.LENGTH_SHORT).show();
                    //이미 지나간 시간 지정이면 다음날로 지정
                    if (calendar.before(Calendar.getInstance())) {
                        calendar.add(Calendar.DATE, 1);
                    }
                }

                // reveiver에 string 값 넘겨주기
                al_intent.putExtra("state","alarm on");

                pendingIntent = PendingIntent.getBroadcast(this,0,al_intent,PendingIntent.FLAG_UPDATE_CURRENT);
                //알람 세팅
                alarm_manager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_config);



    }
}