package com.example.jsrgjhl.hlapp.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jsrgjhl.hlapp.Adapter.Device;
import com.example.jsrgjhl.hlapp.Adapter.DeviceAdapter;
import com.example.jsrgjhl.hlapp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DeviceListActivity extends AppCompatActivity {

    private List<Device> mdeviceList=new ArrayList<>();
    private List<Device> mnowdeviceList=new ArrayList<>();
    private ImageView addDevice;
    private Spinner sortSpinner;
    private Spinner regionSpinner;
    private Spinner defendSpinner;
    private String[] sorttype={"类型","振动传感","监控","雷达"};
    private String[] regiontype={"区域","区域一","区域二","区域三"};
    private String[] defendtype={"防区","防区一","防区二","防区三"};
    private DeviceAdapter adapter;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        initDeviceList();
        Toolbar toolbar = (Toolbar) findViewById(R.id.deviece_list_toolbar);
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

        //
        //初始化设备列表
        mnowdeviceList=mdeviceList;
        adapter = new DeviceAdapter(DeviceListActivity.this, R.layout.device_list,mnowdeviceList);
        final ListView devicelistView=(ListView)findViewById(R.id.device_listview);
        devicelistView.setAdapter(adapter);
        //下拉框控制及其样式
        sortSpinner=findViewById(R.id.sort_sp);
        regionSpinner=findViewById(R.id.region_sp);
        defendSpinner=findViewById(R.id.defend_sp);
        //类型的下拉框
        final ArrayAdapter sortadapter = new ArrayAdapter(this,R.layout.spinner_item,sorttype);
        sortadapter.setDropDownViewResource(R.layout.dropdown_stytle);
        sortSpinner.setAdapter(sortadapter);
        sortSpinner.setDropDownVerticalOffset(140);
        //区域的下拉框
        ArrayAdapter regionadapter = new ArrayAdapter(this,R.layout.spinner_item,regiontype);
        regionadapter.setDropDownViewResource(R.layout.dropdown_stytle);
        regionSpinner.setAdapter(regionadapter);
        regionSpinner.setDropDownVerticalOffset(140);
        //防区下拉框
        final ArrayAdapter defendadapter = new ArrayAdapter(this,R.layout.spinner_item,defendtype);
        defendadapter.setDropDownViewResource(R.layout.dropdown_stytle);
        defendSpinner.setAdapter(defendadapter);
        defendSpinner.setDropDownVerticalOffset(140);

        //类型下拉框点击事件
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (sortSpinner.getSelectedItemPosition()==0&&defendSpinner.getSelectedItemPosition()==0&&regionSpinner.getSelectedItemPosition()==0){
                    initDeviceList();
                    mnowdeviceList=mdeviceList;
                    adapter.notifyDataSetChanged();
                }
                else {
                    initDeviceList();
                    mnowdeviceList=mdeviceList;
                    initNowDeviceList(sorttype[sortSpinner.getSelectedItemPosition()],defendtype[defendSpinner.getSelectedItemPosition()],regiontype[regionSpinner.getSelectedItemPosition()]);
                    adapter.notifyDataSetChanged();}
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        //区域下拉框点击事件
        regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (sortSpinner.getSelectedItemPosition()==0&&defendSpinner.getSelectedItemPosition()==0&&regionSpinner.getSelectedItemPosition()==0){
                    initDeviceList();
                    mnowdeviceList=mdeviceList;
                    adapter.notifyDataSetChanged();
                }
                else {
                    initDeviceList();
                    mnowdeviceList=mdeviceList;
                    initNowDeviceList(sorttype[sortSpinner.getSelectedItemPosition()],defendtype[defendSpinner.getSelectedItemPosition()],regiontype[regionSpinner.getSelectedItemPosition()]);
                    adapter.notifyDataSetChanged();}
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        //防区下拉框点击事件
        defendSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (sortSpinner.getSelectedItemPosition()==0&&defendSpinner.getSelectedItemPosition()==0&&regionSpinner.getSelectedItemPosition()==0){
                    initDeviceList();
                    mnowdeviceList=mdeviceList;
                    adapter.notifyDataSetChanged();
                }
                else {
                    initDeviceList();
                    mnowdeviceList=mdeviceList;
                    initNowDeviceList(sorttype[sortSpinner.getSelectedItemPosition()],defendtype[defendSpinner.getSelectedItemPosition()],regiontype[regionSpinner.getSelectedItemPosition()]);
                    adapter.notifyDataSetChanged();}
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });


        //listview_item点击事件
        devicelistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(DeviceListActivity.this, DeviceSituationActivity.class);
                Device device=mdeviceList.get(i);
                intent.putExtra("device", (Serializable) device);
                startActivity(intent);
            }
        });


        //添加新设备的点击事件
        addDevice=(ImageView)findViewById(R.id.add_newdevice_img);
        addDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(DeviceListActivity.this, DeviceSituationActivity.class);
                Device device=new Device(" ","振动传感"," "," "," "," ","防区一","区域一");
                intent.putExtra("device", (Serializable) device);
                startActivity(intent);
            }
        });
    }

    private void initDeviceList() {
        mdeviceList.clear();
        Device device1=new Device("MAC1000","振动传感","正常运转","理工大学","5555","运行很好","防区二","区域一");
        mdeviceList.add(device1);
        Device device2=new Device("MAC1000","振动传感","正常运转","理工大学","5555","运行很好","防区二","区域二");
        mdeviceList.add(device2);
        Device device3=new Device("MAC1000","振动传感","正常运转","理工大学","5555","运行很好","防区二","区域三");
        mdeviceList.add(device3);
        Device device4=new Device("MAC1000","监控","正常运转","理工大学","5555","运行很好","防区一","区域一");
        mdeviceList.add(device4);
        Device device5=new Device("MAC1000","监控","正常运转","理工大学","5555","运行很好","防区二","区域一");
        mdeviceList.add(device5);
        Device device6=new Device("MAC1000","监控","正常运转","理工大学","5555","运行很好","防区二","区域一");
        mdeviceList.add(device6);
        Device device7=new Device("MAC1000","雷达","正常运转","理工大学","5555","运行很好","防区二","区域一");
        mdeviceList.add(device7);
        Device device8=new Device("MAC1000","雷达","正常运转","理工大学","5555","运行很好","防区二","区域一");
        mdeviceList.add(device8);
        Device device9=new Device("MAC1000","雷达","正常运转","理工大学","5555","运行很好","防区二","区域一");
        mdeviceList.add(device9);
    }
    private void initNowDeviceList(String sort,String defend,String region){
        //完成设备类型的选择
        for(int i=0;i<mnowdeviceList.size();i++){
            if (sort.equals("类型")){
                break;
            }
           else if(mnowdeviceList.get(i).getSort().equals(sort)){
                continue;
            }
            else {
                mnowdeviceList.remove(i);
                i--;
            }
        }
       //完成防区的选择
        for (int j=0;j<mnowdeviceList.size();j++){
            if(defend.equals("防区")){
                break;
            }
            else if(mnowdeviceList.get(j).getDefend().equals(defend)){
                continue;
            }else{
                mnowdeviceList.remove(j);
                j--;
            }
        }
        //完成区域的选择
        for (int k=0;k<mnowdeviceList.size();k++){
            if(region.equals("区域")){
                break;
            }
            else if(mnowdeviceList.get(k).getRegion().equals(region)){
                continue;
            }else{
                mnowdeviceList.remove(k);
                k--;
            }
        }
    }
}
