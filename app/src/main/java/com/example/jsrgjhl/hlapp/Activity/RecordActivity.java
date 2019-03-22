package com.example.jsrgjhl.hlapp.Activity;

import android.content.Intent;
import android.icu.text.AlphabeticIndex;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jsrgjhl.hlapp.Adapter.Device;
import com.example.jsrgjhl.hlapp.Adapter.Records;
import com.example.jsrgjhl.hlapp.Adapter.RecordsAdapter;
import com.example.jsrgjhl.hlapp.R;
import com.example.jsrgjhl.hlapp.Utils.jsonstr2map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecordActivity extends AppCompatActivity implements Serializable{

    private TextView mstatictv;
    private List<Records> mrecordsList=new ArrayList<>();
    private String getallrecordpath="http://47.100.107.158:8080/api/record/getrecordlist";
    private int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        //toolbar部分
        Toolbar toolbar = (Toolbar) findViewById(R.id.recordtoolbar);
        mstatictv=(TextView)findViewById(R.id.static_tv);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        }
        //统计按钮事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mstatictv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RecordActivity.this,"这是要跳转到统计页面后面在做", Toast.LENGTH_SHORT).show();
            }
        });

        //初始化Records列表
        initRecords();
        RecordsAdapter adapter=new RecordsAdapter(RecordActivity.this, R.layout.record_list,mrecordsList);
        ListView recordlistView=(ListView)findViewById(R.id.record_listview);
        recordlistView.setAdapter(adapter);

        //item点击事件
        recordlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(RecordActivity.this, SolveActivity.class);
                Records record0=mrecordsList.get(i);
                intent.putExtra("record",(Serializable)record0);
                startActivity(intent);
            }
        });

    }

    //向服务器请求记录列表
    private void initRecords() {
        Records record1=new Records("预警","待处理","2018-12-7 19:26:00","金沙路","MAC1000",null,null);
        mrecordsList.add(record1);
        Records record2=new Records("预警","已处理","2018-12-7 19:26:00","金沙路","MAC1000","没啥问题","系统故障");
        mrecordsList.add(record2);

//        flag=0;
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                OkHttpClient client=new OkHttpClient();
//                Request request = new Request.Builder().url(getallrecordpath).build();
//
//                try{
//                    //发送请求
//                    Response response=client.newCall(request).execute();
//                    String result=response.body().toString();
//                    Map<String, Object> map= jsonstr2map.jsonstr2map(result);
//                    List<Map<String, Object>> map2=jsonstr2map.jsonstr2list(map.get("data").toString());
//
//                    for(int i=0;i<map2.size();i++){
//                        if (map2.get(i).get("devicestatus"))
//                        Records records=new Records("预警","")
//                    }
//                }catch (Exception e){
//
//                }
//
//            }
//        }).start();

    }

}
