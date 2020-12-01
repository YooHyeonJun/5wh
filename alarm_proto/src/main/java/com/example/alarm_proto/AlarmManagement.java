package com.example.alarm_proto;



import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.alarm_proto.adapter.AlarmItemView;

import java.util.ArrayList;
import java.util.Calendar;

public class AlarmManagement extends AppCompatActivity {
    int count = -1;
    boolean isChecked = false;
    AlarmManager alarm_manager; //알람 메니저
    PendingIntent pendingIntent;
    ArrayList<String> alarms;
    ListView lv;
    MyAdapter adapter;
    Calendar calendar;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    public void onClickAlarm(View v) {

        switch (v.getId()) {
            case R.id.alarmmng_back:
                this.onBackPressed();
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_management);
        preferences = PreferenceManager.getDefaultSharedPreferences(AlarmManagement.this);
        editor = preferences.edit();
        lv = (ListView) findViewById(R.id.listview2);
        alarms = new ArrayList<String>(); // 나중에 수정 필요
        if(preferences.getInt("count",-1) != -1)
        {
            count = preferences.getInt("count",-1);
            for(int i=0;i<=count;i++)
            {
                alarms.add(preferences.getString("text"+ i ,"NAN"));
            }
        }


        adapter = new MyAdapter();
        lv.setAdapter(adapter);

        Button addButton = (Button) findViewById(R.id.alarm_add);
        addButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent aconfigIntent = new Intent(AlarmManagement.this, AlarmConfig.class);
                startActivityForResult(aconfigIntent, 0);
            }
        });
    }

    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return alarms.size();
        }

        @Override
        public Object getItem(int position) {
            return alarms.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            AlarmItemView view = new AlarmItemView(getApplicationContext());
            view.setAlarm_name(alarms.get(position));
            view.setAlarm_switch(isChecked);
            Switch toggle = (Switch)view.findViewById(R.id.alarm_switch);
            toggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isChecked = !isChecked;
                    toggle.setChecked(isChecked);
                    String altime = alarms.get(position);
                    if ( isChecked == false) {

                        //Toast myToast = Toast.makeText(this.getApplicationContext(),"input time first", Toast.LENGTH_SHORT);
                        //myToast.show();


                    } else {
                        String[] arr= altime.split(":");
                        int hour, minute;
                        hour = Integer.parseInt(arr[0]);
                        minute = Integer.parseInt(arr[1]);
                        calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(System.currentTimeMillis());
                        calendar.set(Calendar.HOUR_OF_DAY, hour);
                        calendar.set(Calendar.MINUTE,minute);
                        calendar.set(Calendar.SECOND, 0);

                        Toast.makeText(AlarmManagement.this,"Alarm 예정 " + hour + "시 " + minute + "분", Toast.LENGTH_SHORT).show();
                        //이미 지나간 시간 지정이면 다음날로 지정
                        if (calendar.before(Calendar.getInstance())) {
                            calendar.add(Calendar.DATE, 1);
                        }
                    }
                    //알람 메니저 설정
                    alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    //알람 리시버 인텐트
                    Intent al_intent = new Intent(AlarmManagement.this, Alarm_Receiver.class);
                    // reveiver에 string 값 넘겨주기
                    if(isChecked == true)
                        al_intent.putExtra("state","alarm on");
                    else
                        al_intent.putExtra("state","alarm off");



                    //알람 세팅
                    if(isChecked == true)
                    {
                        pendingIntent = PendingIntent.getBroadcast(AlarmManagement.this,0,al_intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        alarm_manager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                    }
                    else
                    {
                        pendingIntent = PendingIntent.getBroadcast(AlarmManagement.this,0,al_intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        alarm_manager.cancel(pendingIntent);
                        pendingIntent.cancel();

                    }


                }
            });

            return view;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //adapter = new MyAdapter();
        //lv.setAdapter(adapter); // 리스트뷰 어댑터 설정
        if(resultCode == 0){
            String time = data.getStringExtra("alarm_clock");
            alarms.add(time);
            editor.putString("text"+(++count),time);
            editor.putInt("count",count);
            editor.apply();
            lv.setAdapter(adapter);
        }
    }

}