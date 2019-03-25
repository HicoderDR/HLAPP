package com.example.jsrgjhl.hlapp.PersonalSetting;

import android.app.Person;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.jsrgjhl.hlapp.Activity.LoginActivity;
import com.example.jsrgjhl.hlapp.R;
import com.example.jsrgjhl.hlapp.Utils.jsonstr2map;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.xiasuhuei321.loadingdialog.view.LoadingDialog.Speed.SPEED_TWO;

public class PersonalAccount extends AppCompatActivity {

    private TextView changePasswordTv;
    private TextView areaDefenseTv;
    private ArrayList<String> areas = new ArrayList<>();
    private ArrayList<String> defenses = new ArrayList<>();
    private OptionsPickerView pvNoLinkOptions;
    private LoginActivity login=new LoginActivity();
    private final static String Tag=PersonalAccount.class.getSimpleName();
    private String getuserInfo="http://47.100.107.158:8080/api/user/getuserInfo";
    private String updateuserInfo="http://47.100.107.158:8080/api/user/updateuserInfo";
    private static int flag;
    private LoadingDialog mLoadingDialog;

    private TextView personid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_account);
        getOptionData();
        initNoLinkOptionsPicker();
        Toolbar toolbar = (Toolbar) findViewById(R.id.personalAcountBar);
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
        areaDefenseTv=(TextView)findViewById(R.id.areaDefense);
        areaDefenseTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pvNoLinkOptions!=null)
                    pvNoLinkOptions.show();
            }
        });
        personid=(TextView) findViewById(R.id.personId);
        getuserInfo();

        //修改密码点击事件
        changePasswordTv=(TextView)findViewById(R.id.changepassword);
        changePasswordTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PersonalAccount.this,ChangePassword.class);
                startActivity(intent);
            }
        });
        //

    }

    private void getuserInfo() {
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
                        if(map.get("message").equals("SUCCESS")){
                            personid.setText(login.userName);
                            areaDefenseTv.setText(m.get("regionID")+"/"+m.get("defposID"));
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
                Log.i(Tag,"获取成功");

            }else {
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initNoLinkOptionsPicker() {// 不联动的多级选项
        pvNoLinkOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                if(updateuserInfo(areas.get(options1),defenses.get(options2))) {
                    String str = areas.get(options1)
                            + "/" + defenses.get(options2);
                    areaDefenseTv.setText(str);
                }
            }
        })
                .setTitleText("区域/防区选择")
                .setLabels("区域", "防区", null)//设置选择的三级单位
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        String str = "options1: " + options1 + "\noptions2: " + options2 ;
                    }
                })
                // .setSelectOptions(0, 1, 1)
                .build();
        pvNoLinkOptions.setNPicker(areas, defenses, null);
        pvNoLinkOptions.setSelectOptions(0, 0);

    }

    private boolean updateuserInfo(String area,String defpos) {
        final String regionID=area;
        final String defposID=defpos;
        flag=0;
        showLoading();
        try{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    OkHttpClient client=new OkHttpClient();
                    RequestBody requestBody=new FormBody.Builder().add("username",login.userName).add("regionID",regionID).add("defposID",defposID).build();
                    Request request = new Request.Builder().url(updateuserInfo).post(requestBody).build();
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
            setLoaded(flag);
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

    private void setLoaded(int result) {
        if (result==1) mLoadingDialog.loadSuccess(); else if(result==2) mLoadingDialog.loadFailed();
    }

    /**
     * 显示加载的进度款
     */
    public void showLoading() {
        mLoadingDialog = new LoadingDialog(this);
        mLoadingDialog.setLoadingText("加载中")
                .setSuccessText("更新成功")//显示加载成功时的文字
                .setFailedText("更新失败")
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
    private void getOptionData() {
        /**
         * 注意：如果是添加JavaBean实体数据，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        getNoLinkData();
    }

    private void getNoLinkData() {
        areas.add("1");
        areas.add("2");
        areas.add("3");

        defenses.add("1");
        defenses.add("2");
        defenses.add("3");
    }

}
