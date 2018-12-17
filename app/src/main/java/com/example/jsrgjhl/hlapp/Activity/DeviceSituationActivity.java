package com.example.jsrgjhl.hlapp.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jsrgjhl.hlapp.Adapter.Device;
import com.example.jsrgjhl.hlapp.R;

public class DeviceSituationActivity extends AppCompatActivity {

    private EditText idEditText;
    private EditText addressEditText;
    private Spinner sortSpinner;
    private Spinner regionSpinner;
    private Spinner defendSpinner;
    private EditText ipEditText;
    private EditText aboutEditText;
    private ImageView chooseAddressImg;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_situation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.devicesituationbar);
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

//        //选择地图按钮事件
        //这里有个问题，无法点击图片
//        chooseAddressImg.findViewById(R.id.choose_img);
//        chooseAddressImg.setOnClickListener(new android.view.View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(DeviceSituationActivity.this,"选择地图",Toast.LENGTH_SHORT).show();
//            }
//        });
        //设备详情中各个部分
        idEditText=findViewById(R.id.idEditText);
        addressEditText=findViewById(R.id.deviece_address_EditText);
        sortSpinner=findViewById(R.id.sort_de_sp);
        regionSpinner=findViewById(R.id.region_de_sp);
        defendSpinner=findViewById(R.id.defend_de_sp);
        ipEditText=findViewById(R.id.ip_EditText);
        aboutEditText=findViewById(R.id.about_EditText);

        //设置三个下拉框下拉的位置
        sortSpinner.setDropDownVerticalOffset(140);
        regionSpinner.setDropDownVerticalOffset(140);
        defendSpinner.setDropDownVerticalOffset(140);

        //得到设备列表的数据，并填充
        Intent intent = getIntent();
        Device device = (Device) intent.getSerializableExtra("device");
        idEditText.setText(String.valueOf(device.getId()));
        addressEditText.setText((CharSequence) device.getAddress());
        for(int i=0;i<4;i++) {
            if (device.getSort().equals(sortSpinner.getItemAtPosition(i))) {
                sortSpinner.setSelection(i, true);
                break;
            }
        }
            for (int i=0;i<4;i++){
            if (device.getRegion().equals(regionSpinner.getItemAtPosition(i))) {
                regionSpinner.setSelection(i, true);
                break;
            }
            }
            for (int i=0;i<4;i++) {
                if (device.getDefend().equals(defendSpinner.getItemAtPosition(i))) {
                    defendSpinner.setSelection(i, true);
                    break;
                }
            }
        aboutEditText.setText(device.getAbout());
        ipEditText.setText(device.getIpaddress());

        //提交按钮事件
        Button submit=(Button)findViewById(R.id.submit_Btn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DeviceSituationActivity.this,"提交成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
