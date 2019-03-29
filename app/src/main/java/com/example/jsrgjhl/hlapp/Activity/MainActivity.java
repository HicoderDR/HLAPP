package com.example.jsrgjhl.hlapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.jsrgjhl.hlapp.Adapter.Device;
import com.example.jsrgjhl.hlapp.Adapter.Records;
import com.example.jsrgjhl.hlapp.Adapter.RecordsAdapter;
import com.example.jsrgjhl.hlapp.PersonalSetting.OperateRecord;
import com.example.jsrgjhl.hlapp.R;
import com.example.jsrgjhl.hlapp.Utils.ScreenUtils;
import com.example.jsrgjhl.hlapp.Utils.jsonstr2map;
import com.example.jsrgjhl.hlapp.View.SegmentView;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.xiasuhuei321.loadingdialog.view.LoadingDialog.Speed.SPEED_TWO;


public class MainActivity extends AppCompatActivity implements LocationSource, AMapLocationListener,OnItemClickListener, AMap.InfoWindowAdapter {

    private static final String TAG = "TestLocation";
    private Context mContext = null;
    private TextView location = null;
    private RelativeLayout Back = null;
    private RelativeLayout warn=null;
    private MapView mapView;
    private AMap aMap;
    private AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption mLocationOption = null;
    private MyLocationStyle mLocationStyle = null;
    private OnLocationChangedListener mListener = null;
    //menu
    private String[] menunames = {"报表记录", "警报记录", "设备列表", "个人设置"};
    private int[] menuimgs = {R.mipmap.btn_map, R.mipmap.btn_record, R.mipmap.btn_equip, R.mipmap.btn_file};
    private ListView menulistview,recordlistView;
    //segment
    private SegmentView segmentView;
    private LoadingDialog mLoadingDialog;
    //marker
    ArrayList<Device> mdevicelist=new ArrayList<Device>();
    ArrayList<Device> cameraList=new ArrayList<Device>();
    ArrayList<Device> radarList=new ArrayList<Device>();
    ArrayList<Device> sensorList=new ArrayList<Device>();
    ArrayList<Marker> cameramarkers = new ArrayList ();
    ArrayList<Marker> radarmarkers = new ArrayList ();
    ArrayList<Marker> sensormarkers = new ArrayList();


    private String getdevicepath="http://47.100.107.158:8080/api/device/getdevicelist";
    private String getunsolvedpath="http://47.100.107.158:8080/api/record/searchRecordbyrecordstatus";
    private String getrecordpath="http://47.100.107.158:8080/api/record/getrecordlist";
    private List<Records> mrecordsList=new ArrayList<>();
    private HashMap<String,Device> deviceHashMap=new HashMap<>();

    private int flag;
    private final static String Tag= OperateRecord.class.getSimpleName();

    private View marker_camera_g;
    private View marker_camera_y;
    private View marker_camera_r;
    private View marker_camera_gray;
    private View marker_radar_g;
    private View marker_radar_y;
    private View marker_radar_r;
    private View marker_radar_gray;
    private View marker_sensor_gray;
    private View marker_sensor_r;
    private View marker_sensor_y;
    private View marker_sensor_g;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_main);
        init();
        initDeviceList();
        initmarkers();
        initsegment();
        // 创建地图
        mapView.onCreate(savedInstanceState);

        //部分高德UI样式改动
        aMap.getUiSettings().setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);//高德logo位置的移动
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        //myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_move_map_path_dot));
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
    }

    private void init() {
        mContext = this;
        Log.i(TAG, "init()");
        mapView = (MapView) findViewById(R.id.mapView);
        //menu按钮初始化
        Back = (RelativeLayout) findViewById(R.id.back);
        Back.getBackground().setAlpha(125);//0~255透明度值
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(v);
            }
        });
        warn=(RelativeLayout) findViewById(R.id.warningback);
        warn.getBackground().setAlpha(125);
        warn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopWindow(v);
            }
        });
        if (aMap == null) {
            // 显示地图
            aMap = mapView.getMap();
        }
        aMap.showIndoorMap(true);
        aMap.getUiSettings().setZoomControlsEnabled(false);
        // 设置地图默认的指南针是否显示
        aMap.getUiSettings().setCompassEnabled(false);
        // 设置定位监听
        aMap.setLocationSource(this);
        // 设置默认定位按钮是否显示
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        aMap.setInfoWindowAdapter(this);
        // 初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        // 设置高德地图定位回调监听
        mLocationClient.setLocationListener(this);
        // 初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        // 高精度定位模式：会同时使用网络定位和GPS定位，优先返回最高精度的定位结果，以及对应的地址描述信息
        // 设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // 低功耗定位模式：不会使用GPS和其他传感器，只会使用网络定位（Wi-Fi和基站定位）；
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        //mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        // 仅用设备定位模式：不需要连接网络，只使用GPS进行定位，这种模式下不支持室内环境的定位，自 v2.9.0 版本支持返回地址描述信息。
        // 设置定位模式为AMapLocationMode.Device_Sensors，仅设备模式。
        //mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);
        // SDK默认采用连续定位模式，时间间隔2000ms
        // 设置定位间隔，单位毫秒，默认为2000ms，最低1000ms。
        mLocationOption.setInterval(600000);
        // 设置定位同时是否需要返回地址描述
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        // 设置是否强制刷新WIFI，默认为强制刷新。每次定位主动刷新WIFI模块会提升WIFI定位精度，但相应的会多付出一些电量消耗。
        // 设置是否强制刷新WIFI，默认为true，强制刷新。
        mLocationOption.setWifiActiveScan(true);
        // 设置是否允许模拟软件Mock位置结果，多为模拟GPS定位结果，默认为false，不允许模拟位置。
        // 设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        // 设置定位请求超时时间，默认为30秒
        // 单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(50000);
        // 设置是否开启定位缓存机制
        // 缓存机制默认开启，可以通过以下接口进行关闭。
        // 当开启定位缓存功能，在高精度模式和低功耗模式下进行的网络定位结果均会生成本地缓存，不区分单次定位还是连续定位。GPS定位结果不会被缓存。
        // 关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        // 设置是否只定位一次，默认为false
        mLocationOption.setOnceLocation(false);
        // 给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        // 启动高德地图定位
        mLocationClient.startLocation();
        //设置marker点击事件
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
               //CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(marker.getPosition(),17,0,0));
                //aMap.moveCamera(mCameraUpdate);
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker.getPosition()));
                marker.showInfoWindow();
                /*String type=marker.getTitle();
                Gifmarker cameragif=new Gifmarker(type,mContext);
                marker.setIcons(cameragif.iconList);
                */
                String number = marker.getId().substring(6);
                if (!number.equals("")) {
                    Log.i(TAG, "position" + number);
                }
                return true;
            }
        });
    }

    private void initmarkers(){
        initpic();
        cameramarkers.clear();
        radarmarkers.clear();
        sensormarkers.clear();
        deviceHashMap.clear();
        for(int i=0;i<cameraList.size();i++){
            cameramarkers.add(drawMarkerOnMap(cameraList.get(i)));
        }
        for(int i=0;i<radarList.size();i++){
            radarmarkers.add(drawMarkerOnMap(radarList.get(i)));
        }
        for(int i=0;i<sensorList.size();i++){
            sensormarkers.add(drawMarkerOnMap(sensorList.get(i)));
        }
    }

    private Marker drawMarkerOnMap(Device info) {
        LatLng latLng = new LatLng(info.getDevicelat(),info.getDevicelng());
        String devicetype= info.getDevicetype();
        String devicestatus= info.getDevicestatus();
        deviceHashMap.put(info.getDevicenum(),info);
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title(info.getDevicenum())
                .snippet(info.getDeviceaddress())
                .draggable(false);
        switch(devicestatus){
            case "正常运转":
                switch(devicetype){
                    case "监控":
                        options.icon(BitmapDescriptorFactory.fromView(marker_camera_g));
                        break;
                    case "雷达":
                        options.icon(BitmapDescriptorFactory.fromView(marker_radar_g));
                        break;
                    case "振动传感":
                        options.icon(BitmapDescriptorFactory.fromView(marker_sensor_g));
                        break;
                }break;
            case "预警状态":
                switch(devicetype){
                    case "监控":
                        options.icon(BitmapDescriptorFactory.fromView(marker_camera_y));
                        break;
                    case "雷达":
                        options.icon(BitmapDescriptorFactory.fromView(marker_radar_y));
                        break;
                    case "振动传感":
                        options.icon(BitmapDescriptorFactory.fromView(marker_sensor_y));
                        break;
                }break;
            case "报警状态":
                switch(devicetype){
                    case "监控":
                        options.icon(BitmapDescriptorFactory.fromView(marker_camera_r));
                        break;
                    case "雷达":
                        options.icon(BitmapDescriptorFactory.fromView(marker_radar_r));
                        break;
                    case "振动传感":
                        options.icon(BitmapDescriptorFactory.fromView(marker_sensor_r));
                        break;
                }break;
            case "停机状态":
                switch(devicetype){
                    case "监控":
                        options.icon(BitmapDescriptorFactory.fromView(marker_camera_gray));
                        break;
                    case "雷达":
                        options.icon(BitmapDescriptorFactory.fromView(marker_radar_gray));
                        break;
                    case "振动传感":
                        options.icon(BitmapDescriptorFactory.fromView(marker_sensor_gray));
                        break;
                }break;
        }
        Marker marker = aMap.addMarker(options);
        marker.setObject(info);//传入数据bean
        return marker;
    }

    private void setvisible(ArrayList<Marker> list,boolean x){
        for(int i=0;i<list.size();i++){
            list.get(i).setVisible(x);
        }
    }

    private void showPopupWindow(View v) {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(mContext).inflate(
                R.layout.pop_menu, null);
        // 设置按钮的点击事件
        final PopupWindow popupWindow = new PopupWindow(contentView,
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
        int windowPos[] = calculatePopWindowPos(v, contentView);
        int xOff =800;// 可以自己调整偏移
        //windowPos[0] +=500;
        //windowPos[1] -= 520;
        popupWindow.setOutsideTouchable(true);
        //    mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        contentView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                int height = v.findViewById(R.id.popback).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        popupWindow.dismiss();
                    }
                }
                return true;
            }
        });
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        popupWindow.setBackgroundDrawable(dw);
        // 设置好参数之后再show

        //menu的list设置
        ArrayList<HashMap<String, Object>> menulist = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < 4; i++) {
            HashMap<String, Object> unit = new HashMap<String, Object>();
            unit.put("img", menuimgs[i]);
            unit.put("name", menunames[i]);
            menulist.add(unit);
        }
        SimpleAdapter saImageItems = new SimpleAdapter(this,
                menulist,//数据来源
                R.layout.menu_unit,//每一个用户xml相当ListView的一个组件
                new String[]{"img", "name"},
                //分别对应view view的ids
                new int[]{R.id.img, R.id.name});
        //获取listview
        menulistview = (ListView) contentView.findViewById(R.id.list_menu);
        menulistview.setAdapter(saImageItems);
        menulistview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1:
                        Intent intentRecord=new Intent(MainActivity.this,RecordActivity.class);
                        startActivity(intentRecord);
                        break;
                    case 2:
                        Intent intentDeviceList=new Intent(MainActivity.this,DeviceListActivity.class);
                        startActivity(intentDeviceList);
                        break;
                    case 3:
                        Intent intentPerson=new Intent(MainActivity.this,PersonActivity.class);
                        startActivity(intentPerson);
                        break;
                    default:
                        Toast.makeText(MainActivity.this,"功能还未实现",Toast.LENGTH_SHORT).show();
                }
            }
        });

        popupWindow.setAnimationStyle(R.style.pop_anim);
        popupWindow.showAtLocation(v, Gravity.TOP | Gravity.START, windowPos[0], windowPos[1]);
    }

    private void showPopWindow(View v) {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(mContext).inflate(
                R.layout.warninglist, null);
        // 设置按钮的点击事件
        final PopupWindow popWindow = new PopupWindow(contentView,
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
        int windowPos[] = calculatePopWindowPos(v, contentView);
        int xOff =800;// 可以自己调整偏移
        //windowPos[0] +=500;
        //windowPos[1] -= 520;
        popWindow.setOutsideTouchable(true);
        //    mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        contentView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = v.findViewById(R.id.popwarning).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        popWindow.dismiss();
                    }
                }
                return true;
            }
        });
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        popWindow.setBackgroundDrawable(dw);
        // 设置好参数之后再show
        initRecords();
        RecordsAdapter adapter=new RecordsAdapter(this, R.layout.record_list,mrecordsList);
        recordlistView=(ListView)contentView.findViewById(R.id.warninglist);
        recordlistView.setAdapter(adapter);
        recordlistView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Records x=(Records)recordlistView.getItemAtPosition(position);
                String title=x.getTime();
                Toast.makeText(MainActivity.this,"你点击了" + title ,Toast.LENGTH_SHORT).show();
            }
        });
        //获取listvie
        popWindow.setAnimationStyle(R.style.pop_anim);
        popWindow.showAtLocation(v, Gravity.TOP | Gravity.START, windowPos[0], windowPos[1]);
    }
    //计算menu弹窗位置

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

    }
    /**
     * 监听自定义infowindow窗口的infocontents事件回调
     */
    @Override
    public View getInfoContents(Marker marker) {

        View infoContent = getLayoutInflater().inflate(
                R.layout.info_window, null);
        render(marker, infoContent);
        return infoContent;
    }

    /**
     * 监听自定义infowindow窗口的infowindow事件回调
     */
    @Override
    public View getInfoWindow(Marker marker) {
        View infoWindow = getLayoutInflater().inflate(
                R.layout.info_window, null);
        render(marker, infoWindow);
        return infoWindow;
    }

    /**
     * 自定义infowinfow窗口
     */
    public void render(Marker marker, View view) {
        String devicenum=marker.getTitle();
        String devicestatus=marker.getSnippet();
        TextView UInum=(TextView) view.findViewById(R.id.devicenum);
        TextView UIstatus=(TextView) view.findViewById((R.id.devicestatus));
        UInum.setText(devicenum);
        UIstatus.setText(devicestatus);
        /*String title = marker.getTitle();
        TextView titleUi = ((TextView) view.findViewById(R.id.title));
        if (title != null) {
            SpannableString titleText = new SpannableString(title);
            titleText.setSpan(new ForegroundColorSpan(Color.RED), 0,
                    titleText.length(), 0);
            titleUi.setTextSize(15);
            titleUi.setText(titleText);

        } else {
            titleUi.setText("");
        }
        String snippet = marker.getSnippet();
        TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
        if (snippet != null) {
            SpannableString snippetText = new SpannableString(snippet);
            snippetText.setSpan(new ForegroundColorSpan(Color.GREEN), 0,
                    snippetText.length(), 0);
            snippetUi.setTextSize(20);
            snippetUi.setText(snippetText);
        } else {
            snippetUi.setText("");
        }*/
    }

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
                    temp = temp.substring(1, temp.length() - 1).replace(" ", "").replace("{", "").replace("}", "").replace("\"","").replace("\"","");
                    ;
                    String[] strs = temp.split(",");
                    Map<String, String> map2 = new HashMap<String, String>();
                    mrecordsList.clear();
                    for (String s : strs) {
                        String sss=s.replace(" ","");
                        String[] ms = sss.split("=");

                        if (ms[1].equals("null")) {
                            ms[1] = "";
                        }
                        map2.put(ms[0], ms[1]);

                        if (ms[0].equals("username")) {
                            Records record1 = new Records((String) map2.get("devicestatus"), map2.get("recordstatus"), (String) map2.get("recordtime"), (String) map2.get("deviceaddress"), (String) map2.get("devicenum"), (String) map2.get("title"), (String) map2.get("context"),(String)map2.get("recordID"));
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
                    List<Map<String, Object>> map2=jsonstr2map.jsonstr2list(map.get("data").toString());
                    mdevicelist.clear();
                    cameraList.clear();
                    radarList.clear();
                    sensorList.clear();
                    for (int i=0;i<map2.size();i++){
                        Device device1=new Device(Integer.parseInt((String) map2.get(i).get("deviceID")),(String) map2.get(i).get("devicenum"), Double.parseDouble((String) map2.get(i).get("devicelat")) , Double.parseDouble((String) map2.get(i).get("devicelng")) , (String) map2.get(i).get("deviceaddress"), (String) map2.get(i).get("devicestatus"),(String)map2.get(i).get("devicetype"), (String) map2.get(i).get("regionID"), (String) map2.get(i).get("defposID"),(String) map2.get(i).get("IP"));
                        mdevicelist.add(device1);
                        switch (device1.getDevicetype()){
                            case "监控":
                                cameraList.add(device1);
                                break;
                            case "雷达":
                                radarList.add(device1);
                                break;
                            case "振动传感":
                                sensorList.add(device1);
                                break;
                        }
                    }
                    if(mdevicelist.size()!=0){
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
        setLoaded(flag);
        if(flag==1){
            Log.i(Tag,"drresult"+"成功");
        }
    }
    //segmental
    private void initsegment(){
        segmentView = (SegmentView) findViewById(R.id.segmentview);
        segmentView.setOnSegmentViewClickListener(new SegmentView.onSegmentViewClickListener() {
            @Override
            public void onSegmentViewClick(View view, int position) {
                switch (position) {
                    case 0:
                        setvisible(cameramarkers,true);
                        setvisible(radarmarkers,true);
                        setvisible(sensormarkers,true);
                        break;
                    case 1:
                        setvisible(cameramarkers,true);
                        setvisible(radarmarkers,false);
                        setvisible(sensormarkers,false);
                        break;
                    case 2:
                        setvisible(cameramarkers,false);
                        setvisible(radarmarkers,true);
                        setvisible(sensormarkers,false);
                        break;
                    case 3:
                        setvisible(cameramarkers,false);
                        setvisible(radarmarkers,false);
                        setvisible(sensormarkers,true);
                        break;
                    default:
                        break;
                }
            }
        });
    }
    public void setLoaded(int result) {
        if (result==1) mLoadingDialog.loadSuccess(); else if(result==2) mLoadingDialog.loadFailed();
    }
    //定位
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        Log.i(TAG, "onLocationChanged()");
        // 解析AMapLocation对象
        // 判断AMapLocation对象不为空，当定位错误码类型为0时定位成功
        if (aMapLocation != null) {
            Log.i(TAG, "onLocationChanged()--aMapLocation.getErrorCode():" + aMapLocation.getErrorCode());
            if (aMapLocation.getErrorCode() == 0) {
                int locationType = aMapLocation.getLocationType(); // 获取当前定位结果来源，如网络定位结果，详见定位类型表
                double latitude = aMapLocation.getLatitude(); // 获取纬度
                double longitude = aMapLocation.getLongitude(); // 获取经度
                float accuracy = aMapLocation.getAccuracy(); // 获取精度信息
                String address = aMapLocation.getAddress(); // 地址，如果option中设置isNeedAddress为false，则没有此结果，
                // 网络定位结果中会有地址信息，GPS定位不返回地址信息。
                String country = aMapLocation.getCountry(); // 国家信息
                String province = aMapLocation.getProvince(); // 省信息
                String city = aMapLocation.getCity(); // 城市信息
                String district = aMapLocation.getDistrict(); // 城区信息
                String street = aMapLocation.getStreet(); // 街道信息
                String streetNum = aMapLocation.getStreetNum(); // 街道门牌号信息
                String cityCode = aMapLocation.getCityCode(); // 城市编码
                String adCode = aMapLocation.getAdCode(); // 地区编码
                String aoiName = aMapLocation.getAoiName(); // 获取当前定位点的AOI信息
                String buildingId = aMapLocation.getBuildingId(); // 获取当前室内定位的建筑物Id
                String floor = aMapLocation.getFloor(); // 获取当前室内定位的楼层
                int gpsAccuracyStatus = aMapLocation.getGpsAccuracyStatus(); //获取GPS的当前状态
                // 获取定位时间
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);

                Log.i(TAG, "onLocationChanged()---" + "\n"
                        + "--locationType:" + locationType + "\n"
                        + "--latitude:" + latitude + "\n"
                        + "--longitude:" + longitude + "\n"
                        + "--accuracy:" + accuracy + "\n"
                        + "--address:" + address + "\n"
                        + "--country:" + country + "\n"
                        + "--province:" + province + "\n"
                        + "--city:" + city + "\n"
                        + "--district:" + district + "\n"
                        + "--street:" + street + "\n"
                        + "--streetNum:" + streetNum + "\n"
                        + "--cityCode:" + cityCode + "\n"
                        + "--adCode:" + adCode + "\n"
                        + "--aoiName:" + aoiName + "\n"
                        + "--buildingId:" + buildingId + "\n"
                        + "--floor:" + floor + "\n"
                        + "--gpsAccuracyStatus:" + gpsAccuracyStatus + "\n"
                        + "--date:" + date);

                // 设置缩放级别
                aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                // 将地图移动到定位点
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(
                        new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
                // 点击定位按钮 能够将地图的中心移动到定位点
                mListener.onLocationChanged(aMapLocation);

                location.setText("1231312");
            } else {
                // 定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("HLQ_Struggle", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        Log.i(TAG, "onPointerCaptureChanged----hasCapture:" + hasCapture);
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        Log.i(TAG, "activate()");
        mListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        Log.i(TAG, "deactivate()");
        mListener = null;
    }


    @Override
    protected void onResume() {
        Log.i(TAG, "onResume()");
        super.onResume();
        // 重新绘制加载地图
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause()");
        super.onPause();
        // 暂停地图的绘制
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy()");
        super.onDestroy();
        // 销毁地图
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i(TAG, "onSaveInstanceState()");
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static int[] calculatePopWindowPos(final View anchorView, final View contentView) {
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];
        // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationOnScreen(anchorLoc);
        final int anchorHeight = anchorView.getHeight();
        // 获取屏幕的高宽
        final int screenHeight = ScreenUtils.getScreenHeight(anchorView.getContext());
        final int screenWidth = ScreenUtils.getScreenWidth(anchorView.getContext());
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算contentView的高宽
        final int windowHeight = 400;
        final int windowWidth = contentView.getMeasuredWidth();

        // 判断需要向上弹出还是向下弹出显示
        final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < windowHeight);
        if (isNeedShowUp) {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] - windowHeight+40;
        } else {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] + anchorHeight-40;
        }
        Log.i(TAG, "anchorHeight---"+anchorHeight+"\n"+
                "screenHeight---"+screenHeight+"\n"+
                "screenWidth---"+screenWidth+"\n"+
                "windowHeight---"+windowHeight+"\n"+
                "windowWidth---"+windowWidth+"\n"+
                "isNeedShowUp---"+isNeedShowUp+"\n"+
                "windowPos[0]---"+windowPos[0]+"\n"+
                "windowPos[1]---"+windowPos[1]);
        return windowPos;
    }
    public void showLoading() {
        mLoadingDialog = new LoadingDialog(this);
        mLoadingDialog.setLoadingText("正在加载设备列表")
                .setSuccessText("加载成功")//显示加载成功时的文字
                .setFailedText("加载失败")
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

    public void initpic(){
        marker_camera_g = LayoutInflater.from(this).inflate(R.layout.marker_camera_g,null);
        marker_camera_y = LayoutInflater.from(this).inflate(R.layout.marker_camera_y,null);
        marker_camera_r = LayoutInflater.from(this).inflate(R.layout.marker_camera_r,null);
        marker_camera_gray = LayoutInflater.from(this).inflate(R.layout.marker_camera_gray,null);
        marker_radar_g = LayoutInflater.from(this).inflate(R.layout.marker_radar_g,null);
        marker_radar_y = LayoutInflater.from(this).inflate(R.layout.marker_radar_y,null);
        marker_radar_r = LayoutInflater.from(this).inflate(R.layout.marker_radar_r,null);
        marker_radar_gray = LayoutInflater.from(this).inflate(R.layout.marker_radar_gray,null);
        marker_sensor_r = LayoutInflater.from(this).inflate(R.layout.marker_senser_r,null);
        marker_sensor_y = LayoutInflater.from(this).inflate(R.layout.marker_senser_y,null);
        marker_sensor_g = LayoutInflater.from(this).inflate(R.layout.marker_senser_g,null);
        marker_sensor_gray = LayoutInflater.from(this).inflate(R.layout.marker_senser_gray,null);
    }
}