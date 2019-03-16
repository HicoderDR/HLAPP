package com.example.jsrgjhl.hlapp.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jsrgjhl.hlapp.PersonalSetting.AboutUs;
import com.example.jsrgjhl.hlapp.PersonalSetting.AboutVersion;
import com.example.jsrgjhl.hlapp.PersonalSetting.OperateRecord;
import com.example.jsrgjhl.hlapp.PersonalSetting.PersonalAccount;
import com.example.jsrgjhl.hlapp.R;

public class PersonActivity extends AppCompatActivity {
    private TextView personalAccount;
    private TextView operateRecord;
    private TextView aboutreversion;
    private TextView aboutus;
    private TextView logout;
    private Switch aSwitch;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        Toolbar toolbar = (Toolbar) findViewById(R.id.personalSettingBar);
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
        personalAccount=(TextView)findViewById(R.id.accountTextview);
        operateRecord=(TextView)findViewById(R.id.operateRecordTextview);
        aboutreversion=(TextView)findViewById(R.id.versionTextview);
        aboutus=(TextView)findViewById(R.id.aboutusTextview);
        logout=(TextView)findViewById(R.id.logoutTextview);

        //打开gps的方法
        aSwitch =(Switch)findViewById(R.id.addressShare);
        if (checkGPSIsOpen()){
            aSwitch.setChecked(true);
        }
        else{
        aSwitch.setChecked(false);}

        aSwitch.setSwitchTextAppearance(PersonActivity.this,R.style.s_false);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //控制开关字体颜色
                if (b) {
                    aSwitch.setSwitchTextAppearance(PersonActivity.this,R.style.s_true);
                    openGPSSettings();

                }else {
                    aSwitch.setSwitchTextAppearance(PersonActivity.this,R.style.s_false);
                    if (checkGPSIsOpen()){
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, GPS_REQUEST_CODE);
                    }
                }
            }
        });


        //个人账户点击事件
        personalAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent personAccountIntent=new Intent(PersonActivity.this,PersonalAccount.class);
                startActivity(personAccountIntent);
            }
        });

        //账户操作记录点击事件
        operateRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent operateRecordIntent=new Intent(PersonActivity.this,OperateRecord.class);
                startActivity(operateRecordIntent);
            }
        });

        //版本说明点击事件
        aboutreversion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent versionIntent=new Intent(PersonActivity.this,AboutVersion.class);
                startActivity(versionIntent);
            }
        });

        //关于我点击事件
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aboutusIntent=new Intent(PersonActivity.this,AboutUs.class);
                startActivity(aboutusIntent);
            }
        });

        //注销登录点击事件
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor=sp.edit();
                editor.putString("username","");
                editor.putString("password","");
                editor.putBoolean("first",true);
                editor.putBoolean("rememberpassword",false);
                editor.putBoolean("autologin",false);
                editor.commit();
                Intent intent=new Intent(PersonActivity.this,LoginActivity.class);
                Toast.makeText(PersonActivity.this,"退出登录成功",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }

    private int GPS_REQUEST_CODE = 10;

    /**
     * 检测GPS是否打开
     *
     * @return
     */
    private boolean checkGPSIsOpen() {
        boolean isOpen;
        LocationManager locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);
        isOpen = locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
        return isOpen;
    }

    /**
     * 跳转GPS设置
     */
    private void openGPSSettings() {
        if (checkGPSIsOpen()) {
            initLocation(); //自己写的定位方法
        } else {
            //没有打开则弹出对话框
            new AlertDialog.Builder(this)
                    .setTitle(R.string.notifyTitle)
                    .setMessage(R.string.gpsNotifyMsg)
                    // 拒绝, switch不变
                    .setNegativeButton(R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                   aSwitch.setChecked(false);
                                }
                            })

                    .setPositiveButton(R.string.setting,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //跳转GPS设置界面
                                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivityForResult(intent, GPS_REQUEST_CODE);
                                }
                            })

                    .setCancelable(false)
                    .show();

        }
    }

    private void initLocation() {
        aSwitch.setChecked(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GPS_REQUEST_CODE) {
            //做需要做的事情，比如再次检测是否打开GPS了 或者定位
            openGPSSettings();
        }
    }



}
