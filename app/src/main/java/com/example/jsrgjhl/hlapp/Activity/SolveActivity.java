package com.example.jsrgjhl.hlapp.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jsrgjhl.hlapp.Adapter.Device;
import com.example.jsrgjhl.hlapp.Adapter.Records;
import com.example.jsrgjhl.hlapp.Adapter.RecordsAdapter;
import com.example.jsrgjhl.hlapp.R;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SolveActivity extends AppCompatActivity implements Serializable {
    private EditText tabletitle,tablecontent;
    private TextView Warn_status;
    private TextView Slove_status,Timetv,Addresstv,Idtv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Intent intent = getIntent();
        Records record = (Records)intent.getSerializableExtra("record");


        tablecontent=findViewById(R.id.tablecontent);
        tabletitle=findViewById(R.id.tabletitle);
        Warn_status=findViewById(R.id.warn_status);
        Slove_status=findViewById(R.id.slove_status);
        Button submit=(Button)findViewById(R.id.submit_Btn);
        Timetv=findViewById(R.id.timetv);
        Addresstv=findViewById(R.id.addresstv);
        Idtv=findViewById(R.id.idtv);



        Warn_status.setText(record.getWarn_status());
        Slove_status.setText(record.getSolve_status());
        Timetv.setText(record.getTime());
        Idtv.setText(record.getId());
        Addresstv.setText(record.getAddress());
        tabletitle.setText(record.getSolve_title());
        tablecontent.setText(record.getSolve_context());


        //处理状态为已处理 页面布局的调整
        if(record.getSolve_status().equals("已处理")){
            Slove_status.setTextColor(Color.rgb(00, 00, 00));
            tabletitle.setEnabled(false);
            tablecontent.setEnabled(false);
            submit.setVisibility(View.GONE);

        }
        //提交按钮事件
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SolveActivity.this,"提交成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
