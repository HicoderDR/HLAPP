package com.example.jsrgjhl.hlapp.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.example.jsrgjhl.hlapp.Adapter.Device;
import com.example.jsrgjhl.hlapp.R;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.example.jsrgjhl.hlapp.Utils.OkManager;
import com.example.jsrgjhl.hlapp.Utils.jsonstr2map;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.xiasuhuei321.loadingdialog.view.LoadingDialog.Speed.SPEED_TWO;

public class DeviceSituationActivity extends AppCompatActivity implements OnGeocodeSearchListener{
    private GeocodeSearch geocodeSearch;
    private final static int REQUEST_CODE=1;
    private EditText idEditText;
    private EditText addressEditText;
    private Spinner sortSpinner;
    private Spinner regionSpinner;
    private Spinner defendSpinner;
    private EditText ipEditText;
    private ImageView chooseAddressImg;
    private int createormodify=0;
    private final static String Tag = MainActivity.class.getSimpleName();
    private Device getDevice;
    private OkManager manager;
    private OkHttpClient clients;
    private LoadingDialog mLoadingDialog;
    private String createDevicepath="http://47.100.107.158:8080/api/device/createdevice";
    private String modifyDevicepath="http://47.100.107.158:8080/api/device/modifydevice";

    private static int flag;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //得到设备列表的数据，并填充
        Intent intent = getIntent();
        getDevice = (Device) intent.getSerializableExtra("device");
        if(String.valueOf(getDevice.getDefposID())!="null") {
            /**
             createormodify=1表示点击提交的时候为更新设备
             createormodify=0表示点击提交的时候为新建设备
             */
            createormodify=1;
        }
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

        //设备详情中各个部分
        idEditText=findViewById(R.id.idEditText);
        addressEditText=findViewById(R.id.deviece_address_EditText);
        sortSpinner=findViewById(R.id.sort_de_sp);
        regionSpinner=findViewById(R.id.region_de_sp);
        defendSpinner=findViewById(R.id.defend_de_sp);
        ipEditText=findViewById(R.id.ip_EditText);
        chooseAddressImg=findViewById(R.id.choose_img);

        //设置三个下拉框下拉的位置
        sortSpinner.setDropDownVerticalOffset(140);
        regionSpinner.setDropDownVerticalOffset(140);
        defendSpinner.setDropDownVerticalOffset(140);

        if(String.valueOf(getDevice.getDevicenum())!="null") {
            idEditText.setText(String.valueOf(getDevice.getDevicenum()));}
        else{
            getDevice.setDevicestatus("");
        }
        addressEditText.setText((CharSequence) getDevice.getDeviceaddress());
        for(int i=0;i<4;i++) {
            if (getDevice.getDevicetype().equals(sortSpinner.getItemAtPosition(i))) {
                sortSpinner.setSelection(i, true);
                break;
            }
        }
            for (int i=0;i<4;i++){
            if (getDevice.getRegionID().equals(regionSpinner.getItemAtPosition(i))) {
                regionSpinner.setSelection(i, true);
                break;
            }
            }
            for (int i=0;i<4;i++) {
                if (getDevice.getDefposID().equals(defendSpinner.getItemAtPosition(i))) {
                    defendSpinner.setSelection(i, true);
                    break;
                }
            }
        ipEditText.setText(String.valueOf(getDevice.getIP()));
        //提交按钮事件
        final Button submit=(Button)findViewById(R.id.submit_Btn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
        chooseAddressImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooselocation();
            }
        });
        geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(this);
    }

    private void chooselocation(){
        Intent intent=new Intent(DeviceSituationActivity.this,MapActivity.class);
        this.startActivityForResult(intent, REQUEST_CODE);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode==REQUEST_CODE)
        {
            if (resultCode==MapActivity.RESULT_CODE)
            {
                Bundle bundle=data.getExtras();
                double lat=bundle.getDouble("backlat");
                double lng=bundle.getDouble("backlng");
                String str="lat:"+lat+" lng:"+lng;
                LatLonPoint latLonPoint=new LatLonPoint(lat,lng);
                RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 30,GeocodeSearch.AMAP);
                geocodeSearch.getFromLocationAsyn(query);

                Toast.makeText(DeviceSituationActivity.this,str,Toast.LENGTH_LONG).show();
            }
        }

    }
    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        RegeocodeAddress now=result.getRegeocodeAddress();
        String ans=now.getFormatAddress();
        addressEditText.setText(ans);
    }
    @Override
    public void onGeocodeSearched(GeocodeResult result, int rCode) {
    }

    //得到设备Id
    public String getDevicenum(){
        return idEditText.getText().toString().trim();
    }
    //得到设备类型
    public String getDeviceType(){
        return  sortSpinner.getSelectedItem().toString().trim();
    }
    //得到设备区域
    public String getDeviceRegion(){
        return regionSpinner.getSelectedItem().toString().trim();
    }
    //得到设备防区
    public String getDeviceDefend(){
        return defendSpinner.getSelectedItem().toString().trim();
    }
    //得到设备地址
    public String getDeviceAddress(){
        return addressEditText.getText().toString().trim();
    }
    //得到设备ip地址
    public String getDeviceIp(){
        return ipEditText.getText().toString().trim();
    }

    /**
     * 得到设备的Lat
     * 但是还没有连接上
     * @return
     */
    public Double getDevicelat(){
        return 12.0;
    }

    /**
     * 得到设备的lng
     * 但是还没有连接上
     * @return
     */
    public Double getDevicelng(){
        return 12.0;
    }
    //按钮提交事件
    private void submit(){
        //new一个device对象
        //String devicenum, Double devicelat, Double devicelng, String deviceaddress, String devicestatus, String devicetype, String regionID, String defposID, String IP
        final Device device=new Device(getDevicenum(),getDevicelat(),getDevicelng(),getDeviceAddress(),getDevice.getDevicestatus(),getDeviceType(),getDeviceRegion(),getDeviceDefend(),getDeviceIp());
        //先做一些基本的判断
        if (getDevicenum().isEmpty()){
            showToast("你输入的设备号为空！");
            return;
        }
        if (getDeviceAddress().isEmpty()){
            showToast("你输入的设备地址为空！");
            return;
        }
        if (getDeviceIp().isEmpty()){
            showToast("你输入的设备IP为空！");
            return;
        }
        //要请求网络，要子线程
         flag = 0;
        showLoading();//显示加载框
        if(createormodify==0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    clients=new OkHttpClient();
                    //这里的地址还未填写好
                    RequestBody requestBody = new FormBody.Builder().add("devicenum", device.getDevicenum()).add("devicetype",device.getDevicetype()).add("devicestatus",device.getDevicestatus()).add("devicelat", String.valueOf(device.getDevicelat())).add("devicelng", String.valueOf(device.getDevicelng())).add("regionID",device.getRegionID()).add("defposID",device.getDefposID()).add("deviceaddress",device.getDeviceaddress()).add("IP",device.getIP()).build();
                    Request request = new Request.Builder().url(createDevicepath).post(requestBody).build();
                    try {
                        Response response = clients.newCall(request).execute();//发送请求
                        String result = response.body().string();
                        Map<String, Object> map = jsonstr2map.jsonstr2map(result);
                        Log.i(Tag,"device"+map.toString());
                        String x = map.get("data").toString();
                        Log.i(Tag,"device"+map.get("data"));
                        if (x == "true") {
                            flag = 1;
                        }else {
                            flag=2;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            while(flag==0){
                try{
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            setLoaded(flag);
        }
        /*
        修改指定设备参数
         */
//        if(createormodify==1){
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    RequestBody requestBody = new FormBody.Builder().add("username", getAccount()).add("password", getPassword()).build();
//                    Request request = new Request.Builder().url(loginpath).post(requestBody).build();
//                    try {
//                        Response response = clients.newCall(request).execute();//发送请求
//                        String result = response.body().string();
//
//                        Map<String, Object> map = jsonstr2map.jsonstr2map(result);
//                        String x = map.get("data").toString();
//                        if (x == "true") {
//                            flag = 1;
//                            loadCheckBoxState();//记录下当前用户记住密码和自动登录的状态;
//                        } else {
//                            flag = 2;
//                        }
//                        Log.i(Tag, "drresult4 " + flag);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//        }
        Log.i(Tag,"flag"+flag);
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
                .setSuccessText("创建成功")//显示加载成功时的文字
                .setFailedText("创建失败")
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
    //showToast提示窗
    public void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(DeviceSituationActivity.this,msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
