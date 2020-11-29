package com.example.alarm_proto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.alarm_proto.R;

public class AlarmItemView extends LinearLayout {
    CheckBox cbMark;
    TextView alarm_name;
    Switch alarm_switch;
    public AlarmItemView(Context context) {
        super(context);
        inflation_init(context);
        cbMark = (CheckBox) findViewById(R.id.cbMark);
        alarm_name = (TextView) findViewById(R.id.alarm_name);
        alarm_switch = (Switch) findViewById(R.id.alarm_switch);
    }

    private void inflation_init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.list_view_alarm, this,true);
    }

    public void setAlarm_name(String name) {
        alarm_name.setText(name);
    }

    public void setAlarm_switch(boolean val){
        alarm_switch.setChecked(val);
    }
}
