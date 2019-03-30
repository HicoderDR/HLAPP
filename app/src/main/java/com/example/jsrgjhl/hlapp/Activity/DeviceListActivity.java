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
import android.widget.Toast;

import com.example.jsrgjhl.hlapp.Adapter.Device;
import com.example.jsrgjhl.hlapp.Adapter.DeviceAdapter;
import com.example.jsrgjhl.hlapp.PersonalSetting.ChangePassword;
import com.example.jsrgjhl.hlapp.R;
import com.example.jsrgjhl.hlapp.Sample.DeviceList;
import com.example.jsrgjhl.hlapp.Utils.jsonstr2map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

    private final static String Tag = MainActivity.class.getSimpleName();
    private String getdevicepath="http://47.100.107.158:8080/api/device/getdevicelist";
    private static int flag;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        mdeviceList.clear();
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
                    mdeviceList.clear();
                    initDeviceList();
                    mnowdeviceList=mdeviceList;
                    adapter.notifyDataSetChanged();
                }
                else {
                    mdeviceList.clear();
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
                    mdeviceList.clear();
                    initDeviceList();
                    mnowdeviceList=mdeviceList;
                    adapter.notifyDataSetChanged();
                }
                else {
                    mdeviceList.clear();
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
                    mdeviceList.clear();
                    initDeviceList();
                    mnowdeviceList=mdeviceList;
                    adapter.notifyDataSetChanged();
                }
                else {
                    mdeviceList.clear();
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
                Device device=new Device(null,"振动传感","区域一","防区一");
                intent.putExtra("device", (Serializable) device);
                startActivity(intent);
            }
        });
    }

    //向服务器请求列表
    private void initDeviceList() {
        flag=0;
        showLoading();//显示加载框
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(getdevicepath).build();
                try {
                    Response response = client.newCall(request).execute();//发送请求
                    String result = response.body().string();
                    Map<String, Object> map= jsonstr2map.jsonstr2map(result);
                    //List<Map<String, Object>> map2=jsonstr2map.jsonstr2list(map.get("data").toString());
                    /**
                     * 将 string 转为json格式
                     */
                    String temp = map.get("data").toString();
                    if(temp.equals("null")){
                        flag=1;
                        return;
                    }
                    temp = temp.substring(1, temp.length() - 1).replace(" ", "").replace("{", "").replace("}", "").replace("\"","").replace("\"","");
                    Log.i(Tag,temp);
                    String[] strs = temp.split(",");
                    Map<String, String> map2 = new HashMap<String, String>();
                    for (String s : strs) {
                        String sss=s.replace(" ","");
                        String[] ms = sss.split("=");

                        if (ms.length==1) {
                           continue;
                        }
                        if (ms.length==2&&ms[1].equals("null")){
                            ms[1]="";
                        }
                        if (map2.containsKey(ms[0])) {
                            Device device1 = new Device(Integer.parseInt((String) map2.get("deviceID")), (String) map2.get("devicenum"), Double.parseDouble((String) map2.get("devicelat")), Double.parseDouble((String) map2.get("devicelng")), (String) map2.get("deviceaddress"), (String) map2.get("devicestatus"), (String) map2.get("devicetype"), (String) map2.get("regionID"), (String) map2.get("defposID"), (String) map2.get("ip"));
                            mdeviceList.add(device1);
                            map2.clear();
                            map2.put(ms[0], ms[1]);
                        }
                        else{
                            map2.put(ms[0], ms[1]);
                        }
                    }
                    Device device1 = new Device(Integer.parseInt((String) map2.get("deviceID")), (String) map2.get("devicenum"), Double.parseDouble((String) map2.get("devicelat")), Double.parseDouble((String) map2.get("devicelng")), (String) map2.get("deviceaddress"), (String) map2.get("devicestatus"), (String) map2.get("devicetype"), (String) map2.get("regionID"), (String) map2.get("defposID"), (String) map2.get("ip"));
                    mdeviceList.add(device1);
                    if(mdeviceList.size()!=0){
                        deletzero();
                        flag=1;
                    }
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
            Log.i(Tag,"drresult"+"成功");
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        mdeviceList.clear();
        initDeviceList();
        adapter.notifyDataSetChanged();
    }

    private void showLoading() {
    }

    private void initNowDeviceList(String sort,String defend,String region){
        //完成设备类型的选择
        for(int i=0;i<mnowdeviceList.size();i++){
            if (sort.equals("类型")){
                break;
            }
            else if(mnowdeviceList.get(i).getDevicetype().equals(sort)){
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
            else if(mnowdeviceList.get(j).getDefposID().equals(defend)){
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
            else if(mnowdeviceList.get(k).getRegionID().equals(region)){
                continue;
            }else{
                mnowdeviceList.remove(k);
                k--;
            }
        }
    }
    //删除0
    public void deletzero(){
        for (int i=0;i<mdeviceList.size();i++){
            if(mdeviceList.get(i).getDevicenum().equals("0")){
                mdeviceList.remove(i);
                i--;
            }
        }
    }
}
