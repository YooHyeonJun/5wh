package com.example.alarm_proto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class AddDestination extends AppCompatActivity {

    public void onClick(View v){
        Intent desSrcIntent = new Intent(this,DestinationSearch.class);
        switch(v.getId()){
            case R.id.go_back_button:
                this.onBackPressed();
                break;
            case R.id.add:
                startActivity(desSrcIntent);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_destination);
        final ArrayList<String> items = new ArrayList<String>();
        // ArrayAdapter 생성. 아이템 View를 선택(single choice)가능하도록 만듦
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, items);

        // listview 생성 및 adapter 지정.
        final ListView listview = (ListView) findViewById(R.id.list_view1);
        listview.setAdapter(adapter);
        
        Button addButton = (Button) findViewById(R.id.add);
        addButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                int count;
                count = adapter.getCount();

                // 아이템 추가.
                items.add("LIST" + Integer.toString(count + 1));
                // listview 갱신
                adapter.notifyDataSetChanged();
            }
        });
        Button deleteButton = (Button) findViewById(R.id.delete);
        deleteButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                int count, checked;
                count = adapter.getCount();

                if (count > 0) {
                    // 현재 선택된 아이템의 position 획득.
                    checked = listview.getCheckedItemPosition();

                    if (checked > -1 && checked < count) {
                        // 아이템 삭제
                        items.remove(checked);

                        // listview 선택 초기화.
                        listview.clearChoices();

                        // listview 갱신.
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
