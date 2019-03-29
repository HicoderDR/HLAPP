package com.example.jsrgjhl.hlapp.PersonalSetting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jsrgjhl.hlapp.Activity.DeviceSituationActivity;
import com.example.jsrgjhl.hlapp.Activity.LoginActivity;
import com.example.jsrgjhl.hlapp.Activity.PersonActivity;
import com.example.jsrgjhl.hlapp.R;
import com.example.jsrgjhl.hlapp.Utils.jsonstr2map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChangePassword extends AppCompatActivity {

    private LoginActivity login=new LoginActivity();
    private EditText oldpassword;
    private EditText newpassword;
    private EditText confirmpassword;
    private String getuserInfo="http://47.100.107.158:8080/api/user/getuserInfo";
    private String changePassword="http://47.100.107.158:8080/api/user/changepassword";

    SharedPreferences sp;

    private static int flag;
    private final static String Tag=ChangePassword.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.changePasswordBar);
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
        oldpassword=(EditText)findViewById(R.id.oldPassword);
        newpassword=(EditText)findViewById(R.id.newPassword);
        confirmpassword=(EditText)findViewById(R.id.confirmPassword);
        Button confirmButton=(Button)findViewById(R.id.confirm_Btn);

        /**
         * 修改密码
         * 1.确认旧密码对不对
         * 2.确认新密码两次输入对不对
         */
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(confirmOldPassword())
                {
                    if(confirmTwoPassword()){
                        if(changePassword()){
                            /**
                             * 如何将密码直接保存到本地？？
                             */
                            showToast("密码修改成功");

                        }else{
                            showToast("密码修改失败");
                        }
                    }else{
                        showToast("请确认新密码");
                    }
                }else{
                    showToast("旧密码错误");
                }

            }
        });
    }

    private boolean changePassword() {
        flag=0;
        try{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    OkHttpClient client=new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("username",login.userName ).add("password",newpassword.getText().toString() ).build();
                    Request request = new Request.Builder().url(changePassword).post(requestBody).build();
                    try{
                        Response response=client.newCall(request).execute();
                        String result = response.body().string();
                        Map<String, Object> map= jsonstr2map.jsonstr2map(result);

                        String x=map.get("data").toString();
                        if(x=="true"){
                            flag=1;
                        }else{
                            flag=2;
                        }
                    }catch (Exception e){

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
                Log.i(Tag,"password"+"成功");
                return true;

            }else {
                Log.i(Tag,"password"+"失败");
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private boolean confirmTwoPassword() {
        if(newpassword.getText().toString().equals(confirmpassword.getText().toString())){
            return true;
        }else {
            newpassword.setText("");
            confirmpassword.setText("");
            return false;
        }
    }

    private boolean confirmOldPassword() {
        flag=0;
        try{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(getuserInfo+"?username="+login.userName).build();
                    try {
                        Response response = client.newCall(request).execute();//发送请求
                        String result = response.body().string();
                        Map<String, Object> map= jsonstr2map.jsonstr2map(result);
                        /**
                         * 将 string 转为json格式
                         */
                        String temp=map.get("data").toString();
                        temp=temp.substring(1,temp.length()-1).replace(" ", "");;
                        String[] strs=temp.split(",");
                         Map<String,String> m=new HashMap<String,String>();
                        for(String s:strs){
                            String[] ms=s.split("=");
                            m.put(ms[0],ms[1]);
                        }
                        if(m.get("password").equals(String.valueOf(oldpassword.getText()))){
                            flag=1;
                        }
                        else flag=2;

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
                Log.i(Tag,"password"+"成功");
                return true;

            }else {
                oldpassword.setText("");
                newpassword.setText("");
                confirmpassword.setText("");
                return false;
            }
        }catch (Exception e) {
                e.printStackTrace();
        }
        return false;
    }
    //showToast提示窗
    public void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ChangePassword.this,msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
