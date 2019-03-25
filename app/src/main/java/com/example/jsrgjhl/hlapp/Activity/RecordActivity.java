package com.example.jsrgjhl.hlapp.Activity;

import android.content.Intent;
import android.icu.text.AlphabeticIndex;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jsrgjhl.hlapp.Adapter.Device;
import com.example.jsrgjhl.hlapp.Adapter.Records;
import com.example.jsrgjhl.hlapp.Adapter.RecordsAdapter;
import com.example.jsrgjhl.hlapp.PersonalSetting.OperateRecord;
import com.example.jsrgjhl.hlapp.R;
import com.example.jsrgjhl.hlapp.Utils.jsonstr2map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecordActivity extends AppCompatActivity implements Serializable{

    private TextView mstatictv;
    private List<Records> mrecordsList=new ArrayList<>();
    private String getrecordpath="http://47.100.107.158:8080/api/record/getrecordlist";
    private int flag;
    private final static String Tag= OperateRecord.class.getSimpleName();
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
        flag=0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(getrecordpath).build();
                try {
                    Response response = client.newCall(request).execute();//发送请求
                    String result = response.body().string();
                    Map<String, Object> map= jsonstr2map.jsonstr2map(result);
                    /**
                     * 将 string 转为json格式
                     */

                        String temp = map.get("data").toString();
                        Log.i(Tag, temp);
                        temp = temp.substring(1, temp.length() - 1).replace(" ", "").replace("{", "").replace("}", "");
                        ;
                        String[] strs = temp.split(",");
                        Map<String, String> map2 = new HashMap<String, String>();
                        for (String s : strs) {
                            String sss=s.replace(" ","");
                            String[] ms = sss.split("=");

                            /**
                             * 这个地方警报类型 强制判断
                             * 如果解决方案为空 就是未处理
                             * 如果解决方案不为空 就是已处理
                             */
                            String recordstatus="";
                            if(ms[0].equals("title")) {
                                if (ms[1].equals("null")) {
                                    recordstatus = "未处理";
                                } else {
                                    recordstatus = "已处理";
                                }
                            }
                            if (ms[1].equals("null")) {
                                ms[1] = "";
                            }
                            map2.put(ms[0], ms[1]);

                            if (ms[0].equals("username")) {
                                Records record1 = new Records((String) map2.get("devicestatus"), recordstatus, (String) map2.get("recordtime"), (String) map2.get("deviceaddress"), (String) map2.get("devicenum"), (String) map2.get("title"), (String) map2.get("context"));
                                mrecordsList.add(record1);
                            }
                        }

                    if(mrecordsList.size()!=0){
                        flag=1;
                    }else flag=2;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        while (flag==0){
            try{
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        if(flag==1){
            Log.i(Tag,"设备记录请求成功");
        }

    }

}
