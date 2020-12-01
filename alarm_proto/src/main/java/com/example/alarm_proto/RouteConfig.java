package com.example.alarm_proto;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class RouteConfig extends AppCompatActivity {
    EditText start_et = null;
    EditText finish_et = null;
    ListView listView;
    double mStart_x;
    double mStart_y;
    double mFinish_x;
    double mFinish_y;

    private Context context;

    private ODsayService odsayService;
    private JSONObject jsonObject;
    private Map mapObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_config);

        init();
    }

    private void init() {
        context = this;
        start_et = (EditText) findViewById(R.id.textbox1);
        finish_et = (EditText) findViewById(R.id.textbox2);
        listView = (ListView) findViewById(R.id.listView);

        odsayService = ODsayService.init(RouteConfig.this, getString(R.string.odsay_key));
        odsayService.setReadTimeout(5000);
        odsayService.setConnectionTimeout(5000);

    }


    private OnResultCallbackListener onResultCallbackListener = new OnResultCallbackListener() {
        @Override
        public void onSuccess(ODsayData oDsayData, API api) {
            jsonObject = oDsayData.getJson();
            mapObject = oDsayData.getMap();
            transportaion(jsonObject);
        }

        @Override
        public void onError(int i, String errorMessage, API api) {

        }
    };

    public void onClickRoute(View v){
        Intent rSelectIntent = new Intent(this,RouteSelect.class);

        switch(v.getId())
        {
            case R.id.imageButton6:
                rSelectIntent.putExtra("val","finish");
                startActivityForResult(rSelectIntent,0);
                break;
            case R.id.imageButton5:
                rSelectIntent.putExtra("val","start");
                startActivityForResult(rSelectIntent,0);
                break;
            case R.id.imageButton4:
                this.onBackPressed();
                break;
            case R.id.routingButton:
                String sStart_x = Double.toString(mStart_x);
                String sStart_y = Double.toString(mStart_y);
                String sFinish_x = Double.toString(mFinish_x);
                String sFinish_y = Double.toString(mFinish_y);
                odsayService.requestSearchPubTransPath(sStart_x,sStart_y,sFinish_x,sFinish_y,"0","0","0",onResultCallbackListener);
                //odsayService.requestSearchPubTransPath("126.926493082645", "37.6134436427887", "127.126936754911", "37.5004198786564", "0", "0", "0", onResultCallbackListener);

                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode, data);
        if(resultCode == 0) {


            String val = data.getStringExtra("val");
            if (val.equals("start")) {
                String name = data.getStringExtra("name");
                mStart_x = data.getDoubleExtra("x", -1);
                mStart_y = data.getDoubleExtra("y", -1);
                start_et.setText(name);
            } else if (val.equals("finish")) {
                String name = data.getStringExtra("name");
                mFinish_x = data.getDoubleExtra("x", -1);
                mFinish_y = data.getDoubleExtra("y", -1);
                finish_et.setText(name);
            }
        }
    }
    protected  void onSaveInstatnceState(Bundle outState){
        String bk_start, bk_finish;
        if(start_et.getText().toString() != null)
        {
            bk_start = start_et.getText().toString();
            outState.putString("BACKUP_START", bk_start);
            outState.putDouble("ST_X",mStart_x);
            outState.putDouble("ST_Y",mStart_y);
        }

        if(finish_et.getText().toString() != null)
        {
            bk_finish = finish_et.getText().toString();
            outState.putString("BACKUP_FINISH", bk_finish);
            outState.putDouble("FH_X",mFinish_x);
            outState.putDouble("FH_Y",mFinish_y);
        }

        super.onSaveInstanceState(outState);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            start_et.setText(savedInstanceState.getString("BACKUP_START"));
            finish_et.setText(savedInstanceState.getString("BACKUP_FINISH"));
        }

    }
    void transportaion(JSONObject jsonObject)
    {
        try {
            final ArrayList<String> list = new ArrayList<String>();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
            listView.setAdapter(adapter);
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            if(mStart_x == -1 || mStart_y == -1) {
                list.add("출발지의 위치를 다시 설정하여 주십시오.");
                return;
            }
            if(mFinish_x == -1 || mFinish_y == -1) {
                list.add("도착지의 위치를 다시 설정하여 주십시오.");
                return;
            }
            if(jsonObject.toString().contains("no data")) {
                list.add("검색된 결과가 없습니다.");
                //Toast.makeText(getApplicationContext(),"검색된 결과가 없습니다.", Toast.LENGTH_SHORT).show();

                return;
            }

            JSONObject result = jsonObject.getJSONObject("result");
            final JSONArray pathArray = result.getJSONArray("path");

            int pathArrayCount = pathArray.length();

            for(int i = 0; i < pathArrayCount; i++)
            {
                JSONObject pathArrayDetailOBJ = pathArray.getJSONObject(i);
                int totalTime = pathArrayDetailOBJ.getJSONObject("info").getInt("totalTime"); //총 시간 구하기
                JSONArray subPathArray = pathArrayDetailOBJ.getJSONArray("subPath");
                System.out.println(totalTime);

                String data = "";
                data += pathArrayDetailOBJ.getJSONObject("info").get("firstStartStation");
                for(int j = 0; j < subPathArray.length(); j++) {
                    JSONObject subPathOBJ = subPathArray.getJSONObject(j);
                    if(subPathOBJ.getInt("trafficType") != 2) continue;
                    System.out.println(subPathOBJ.getJSONArray("lane").getJSONObject(0).get("busNo"));
                    data = data + ">" + subPathOBJ.getJSONArray("lane").getJSONObject(0).get("busNo");
                }
                data += ">" + pathArrayDetailOBJ.getJSONObject("info").get("lastEndStation");
                //list.add(pathArrayDetailOBJ.toString());
                data = data + "\n" + "totalTime: " + totalTime;
                list.add(data);
                System.out.println(pathArrayDetailOBJ.toString());
            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(RouteConfig.this, AlarmConfig.class);
                    try{
                        intent.putExtra("time", pathArray.getJSONObject(position).getJSONObject("info").getInt("totalTime"));
                        setResult(0,intent);
                        finish();
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
