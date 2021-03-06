package com.example.jsrgjhl.hlapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jsrgjhl.hlapp.Adapter.Record;
import com.example.jsrgjhl.hlapp.R;
import com.example.jsrgjhl.hlapp.Utils.jsonstr2map;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.xiasuhuei321.loadingdialog.view.LoadingDialog.Speed.SPEED_TWO;

public class SolveActivity extends AppCompatActivity implements Serializable {
    private EditText tabletitle,tablecontent;
    private TextView Warn_status;
    private TextView Slove_status,Timetv,Addresstv,Idtv;
    private String addsolutionpath="http://47.100.107.158:8080/api/solution/addsolution";
    private final static String Tag = SolveActivity.class.getSimpleName();
    private static int flag;
    private Record record;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    private LoadingDialog mLoadingDialog;
    private String userName;

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


        sp=getSharedPreferences("userinfo", MODE_PRIVATE);
        editor=sp.edit();
        userName = sp.getString("username","");
        Intent intent = getIntent();
        record = (Record)intent.getSerializableExtra("record");


        tablecontent=findViewById(R.id.tablecontent);
        tabletitle=findViewById(R.id.tabletitle);
        Warn_status=findViewById(R.id.warn_status);
        Slove_status=findViewById(R.id.slove_status);
        final Button submit=(Button)findViewById(R.id.submit_Btn);
        Timetv=findViewById(R.id.timetv);
        Addresstv=findViewById(R.id.addresstv);
        Idtv=findViewById(R.id.idtv);



        Warn_status.setText(record.getDevicestatus());
        Slove_status.setText(record.getRecordstatus());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            Date dt=df.parse(String.valueOf(record.getRecordtime()));
            String date=format.format(dt);
            Timetv.setText(date);
        }catch (ParseException e) {
            e.printStackTrace();
        }

        Idtv.setText(record.getDevicenum());
        Addresstv.setText(record.getDeviceaddress());
        tabletitle.setText(record.getTitle());
        tablecontent.setText(record.getContext());
        //处理状态为已处理 页面布局的调整
        if(record.getRecordstatus().equals("已处理")){
            Slove_status.setTextColor(Color.rgb(00, 00, 00));
            tabletitle.setEnabled(false);
            tablecontent.setEnabled(false);
            submit.setVisibility(View.GONE);

        }
        /**
         * 提交按钮事件
         * 首先 先提交solution  然后更新记录状态
         */
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(addSolution()){
                    Slove_status.setText("已处理");
                    Slove_status.setTextColor(Color.rgb(00, 00, 00));
                    tabletitle.setEnabled(false);
                    tablecontent.setEnabled(false);
                    submit.setVisibility(View.GONE);
                    Log.i(Tag,"更新记录成功");
                }
            }
        });
    }

    private void setLoaded(int result) {
        if (result==1) mLoadingDialog.loadSuccess(); else if(result==2) mLoadingDialog.loadFailed();
    }

    /**
     * 显示加载的进度款
     */
    public void showLoading() {
        mLoadingDialog = new LoadingDialog(this);
        mLoadingDialog.setLoadingText("加载中")
                .setSuccessText("提交成功")//显示加载成功时的文字
                .setFailedText("提交失败")
                .setInterceptBack(false)
                .setLoadSpeed(SPEED_TWO)
                .show();
    }

    /**
     * 隐藏加载的进度框
     */
    public void hideLoading() {
        if (mLoadingDialog != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLoadingDialog.close();
                }
            });
        }
    }

    @Override
    public void onBackPressed(){
        if (mLoadingDialog != null) {
            hideLoading();
            finish();
        } else {
            finish();
        }

    }

    private boolean addSolution() {
        flag=0;
        showLoading();
        Date now=new Date();
        final String ddate=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(now);
        try{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    OkHttpClient client=new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("recordnum",String.valueOf(record.getRecordnum())).add("recordID", String.valueOf(record.getRecordID())).add("deltime",ddate).add("userID",String.valueOf(record.getUserID())).add("username",userName).add("title",tabletitle.getText().toString()).add("context",tablecontent.getText().toString()).add("devicenum",String.valueOf(record.getDevicenum())).build();
                    Request request = new Request.Builder().url(addsolutionpath).post(requestBody).build();
                    try{
                        Response response=client.newCall(request).execute();
                        String result = response.body().string();
                        Map<String, Object> map= jsonstr2map.jsonstr2map(result);
                        String x=map.get("data").toString();
                        if(x=="true"){
                            flag=1;
                        }else {
                            flag=2;
                        }
                    }catch (Exception e){
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
            setLoaded(flag);
            if(flag==1){
                return true;

            }else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
