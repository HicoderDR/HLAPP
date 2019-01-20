package com.example.jsrgjhl.hlapp.Activity;

import android.content.Intent;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends AppCompatActivity implements Serializable{

    private TextView mstatictv;
    private List<Records> mrecordsList=new ArrayList<>();
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

    private void initRecords() {
        Records record1=new Records("预警","待处理","2018-12-7 19:26:00","金沙路","MAC1000");
        mrecordsList.add(record1);
        Records record2=new Records("预警","已处理","2018-12-7 19:26:00","金沙路","MAC1000");
        mrecordsList.add(record2);
    }

}
