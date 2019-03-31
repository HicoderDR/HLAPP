package com.example.jsrgjhl.hlapp.PersonalSetting;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jsrgjhl.hlapp.Activity.LoginActivity;
import com.example.jsrgjhl.hlapp.Adapter.Solution;
import com.example.jsrgjhl.hlapp.Adapter.SolutionsAdapter;
import com.example.jsrgjhl.hlapp.R;
import com.example.jsrgjhl.hlapp.Utils.jsonstr2map;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OperateRecord extends AppCompatActivity {
    private List<Solution> msolutionList=new ArrayList<>();
    private String getsolutionpath="http://47.100.107.158:8080/api/solution/getsolutionlist";
    private int flag;
    private final static String Tag= OperateRecord.class.getSimpleName();
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operate_record);
        //toolbar部分
        Toolbar toolbar = (Toolbar) findViewById(R.id.operaterecordtoolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        }
        //统计按钮事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        sp=getSharedPreferences("userinfo", MODE_PRIVATE);
        editor=sp.edit();
        userName = sp.getString("username","");
        /**
         * 初始化用户操作列表
         */
        msolutionList.clear();
        initSolutions();
        SolutionsAdapter adapter=new SolutionsAdapter(OperateRecord.this, R.layout.operate_record_list,msolutionList);
        ListView solutionslistView=(ListView)findViewById(R.id.operaterecord_listview);
        solutionslistView.setAdapter(adapter);

    }

    private void initSolutions() {
        msolutionList.clear();
        flag=0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(getsolutionpath+"?username="+userName).build();
                try {
                    Response response = client.newCall(request).execute();//发送请求
                    String result = response.body().string();
                    Map<String, Object> map= jsonstr2map.jsonstr2map(result);
                    /**
                     * 将 string 转为json格式
                     */

                    String temp = map.get("data").toString();
                    if(temp.equals("null")){
                        flag=1;
                       // Toast.makeText(OperateRecord.this,"查不到信息",Toast.LENGTH_SHORT).show();
                        Log.i(Tag,"查不到记录");
                        return;
                    }
                    Log.i(Tag, temp);
                    temp = temp.substring(1, temp.length() - 1).replace(" ", "").replace("{", "").replace("}", "").replace("\"","").replace("\"","");
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                    String[] strs = temp.split(",");
                    Map<String, String> map2 = new HashMap<String, String>();
                    for (String s : strs) {
                        String sss=s.replace(" ","");
                        String[] ms = sss.split("=");

                        if (ms[1].equals("null")&&ms.length==2) {
                            ms[1] = "";
                        }
                        if (map2.containsKey(ms[0])) {
                            Solution solution = new Solution(map2.get("deltime"),map2.get("title"),map2.get("context"),map2.get("devicenum"));
                            msolutionList.add(solution);
                            map2.clear();
                            if (ms.length==2)
                            map2.put(ms[0],ms[1]);
                        }else{
                            if (ms.length==2)
                            map2.put(ms[0], ms[1]);
                        }
                    }
                    Solution solution = new Solution(map2.get("deltime"),map2.get("title"),map2.get("context"),map2.get("devicenum"));
                    msolutionList.add(solution);
                        Log.i(Tag, String.valueOf(msolutionList));
                    if(msolutionList.size()!=0){
                        Log.i(Tag, String.valueOf(msolutionList.size()));
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
}
