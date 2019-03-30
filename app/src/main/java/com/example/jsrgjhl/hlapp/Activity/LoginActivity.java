package com.example.jsrgjhl.hlapp.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jsrgjhl.hlapp.R;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    //布局内的控件
    private EditText et_name;
    private EditText et_password;
    private Button mLoginBtn;
    private CheckBox checkBox_password;
    private CheckBox checkBox_login;
    private ImageView iv_see_password;
    private TextView Apptitle;
    private  LoadingDialog mLoadingDialog;
    SharedPreferences sp;

    private final static String Tag = MainActivity.class.getSimpleName();
    private OkManager manager;
    private OkHttpClient clients;
    private String loginpath="http://47.100.107.158:8080/api/user/login";
    public static String userName;

    private static int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS| WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION| View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_login);
        if(ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED||ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){//未开启定位权限
            //开启定位权限,200是标识码
            ActivityCompat.requestPermissions(LoginActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE},200);
        }
        sp=getSharedPreferences("userinfo", MODE_PRIVATE);
        manager = OkManager.getInstance();
        clients = new OkHttpClient();
        initViews();
        setupEvents();
        initData();
        userName=getAccount();
        Log.i(Tag,"name"+"loginusernam"+userName);
    }

    private void initData() {
        //判断用户第一次登陆
        if (firstLogin()) {
            checkBox_password.setChecked(false);//取消记住密码的复选框
            checkBox_login.setChecked(false);//取消自动登录的复选框
        }
        //判断是否记住密码
        if (rememberPassword()) {
            checkBox_password.setChecked(true);//勾选记住密码
            setTextNameAndPassword();//把密码和账号输入到输入框中
        } else {
            setTextName();//把用户账号放到输入账号的输入框中
        }

        //判断是否自动登录
        if (autoLogin()) {
            checkBox_login.setChecked(true);
            login();//去登录就可以

        }
    }

    /**
     * 把本地保存的数据设置数据到输入框中
     */
    public void setTextNameAndPassword() {
        et_name.setText("" + getLocalName());
        et_password.setText("" + getLocalPassword());
    }

    /**
     * 设置数据到输入框中
     */
    public void setTextName() {
        et_name.setText("" + getLocalName());
    }


    /**
     * 获得保存在本地的用户名
     */
    public String getLocalName() {
        String name = sp.getString("username","");
        return name;
    }


    /**
     * 获得保存在本地的密码
     */
    public String getLocalPassword() {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        /*
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        String password = helper.getString("password");
        return Base64Utils.decryptBASE64(password);   //解码一下
//       return password;   //解码一下
        */
        String password = sp.getString("password","");
        return password;
    }

    /**
     * 判断是否自动登录
     */
    private boolean autoLogin() {
        boolean autoLogin = sp.getBoolean("autologin", false);
        return autoLogin;
    }

    /**
     * 判断是否记住密码
     */
    private boolean rememberPassword() {

        boolean rememberPassword = sp.getBoolean("rememberpassword", false);
        return rememberPassword;
    }


    private void initViews() {
        Apptitle =(TextView) findViewById(R.id.app_title);
        mLoginBtn = (Button) findViewById(R.id.btn_login);
        et_name = (EditText) findViewById(R.id.et_account);
        et_password = (EditText) findViewById(R.id.et_password);
        checkBox_password = (CheckBox) findViewById(R.id.checkBox_password);
        checkBox_login = (CheckBox) findViewById(R.id.checkBox_login);
        iv_see_password = (ImageView) findViewById(R.id.iv_see_password);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"micro.ttf");
        mLoginBtn.setTypeface(typeface);
        et_name.setTypeface(typeface);
        et_password.setTypeface(typeface);
        checkBox_password.setTypeface(typeface);
        checkBox_login.setTypeface(typeface);
        Apptitle.setTypeface(typeface);
    }

    private void setupEvents() {
        mLoginBtn.setOnClickListener(this);
        checkBox_password.setOnCheckedChangeListener(this);
        checkBox_login.setOnCheckedChangeListener(this);
        iv_see_password.setOnClickListener(this);
    }

    /**
     * 判断是否是第一次登陆
     */
    private boolean firstLogin() {
        boolean first = sp.getBoolean("first", true);
        if (first) {
            //创建一个ContentVa对象（自定义的）设置不是第一次登录，,并创建记住密码和自动登录是默认不选，创建账号和密码为空
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("username","");
            editor.putString("password","");
            editor.putBoolean("first",false);
            editor.putBoolean("rememberpassword",false);
            editor.putBoolean("autologin",false);
            editor.commit();
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btn_login:
                loadUserName();    //无论如何保存一下用户名
                login(); //登陆
                break;
            case R.id.iv_see_password:
                setPasswordVisibility();    //改变图片并设置输入框的文本可见或不可见
                break;
        }
    }

    public void setLoaded(int result) {
        if (result==1) mLoadingDialog.loadSuccess(); else if(result==2) mLoadingDialog.loadFailed();
    }

    /**
     * 模拟登录情况
     * 用户名csdn，密码123456，就能登录成功，否则登录失败
     */
    private void login() {
        Log.i(Tag,"drresult1 "+flag);
        //先做一些基本的判断，比如输入的用户命为空，密码为空，网络不可用多大情况，都不需要去链接服务器了，而是直接返回提示错误
        if (getAccount().isEmpty()){
            showToast("你输入的账号为空！");
            return;
        }
        if (getPassword().isEmpty()){
            showToast("你输入的密码为空！");
            return;
        }
        //登录一般都是请求服务器来判断密码是否正确，要请求网络，要子线程
        flag=0;
        Log.i(Tag,"drresult2 "+flag);
        showLoading();//显示加载框
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(Tag,"drresult3 "+flag);
                RequestBody requestBody = new FormBody.Builder().add("username", getAccount()).add("password", getPassword()).build();
                Request request = new Request.Builder().url(loginpath).post(requestBody).build();
                try {
                    Response response = clients.newCall(request).execute();//发送请求
                    String result = response.body().string();

                    Map<String, Object> map=jsonstr2map.jsonstr2map(result);
                    String x=map.get("data").toString();
                    if(x=="true"){
                        flag=1;
                        loadCheckBoxState();//记录下当前用户记住密码和自动登录的状态;
                    }else{
                        flag=2;
                    }
                    Log.i(Tag,"drresult4 "+flag);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Log.i(Tag,"drresult5 "+flag);
        while(flag==0){
            try{
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Log.i(Tag,"drresult6 "+flag);
        setLoaded(flag);
        if(flag==1){
            Intent mainintent = new Intent(LoginActivity.this, MainActivity.class);
            LoginActivity.this.startActivity(mainintent);
            LoginActivity.this.finish();
        }
    }

    /**
     * 保存用户账号
     */
    public void loadUserName() {
        if (!getAccount().equals("") || !getAccount().equals("请输入登录账号")) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("username",getAccount());
            editor.commit();
        }
    }

    /**
     * 设置密码可见和不可见的相互转换
     */
    private void setPasswordVisibility() {
        if (iv_see_password.isSelected()) {
            iv_see_password.setSelected(false);
            //密码不可见
            et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        } else {
            iv_see_password.setSelected(true);
            //密码可见
            et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }

    }

    /**
     * 获取账号
     */
    public String getAccount() {
        return et_name.getText().toString().trim();//去掉空格
    }

    /**
     * 获取密码
     */
    public String getPassword() {
        return et_password.getText().toString().trim();//去掉空格
    }


    /**
     * 保存用户选择“记住密码”和“自动登陆”的状态
     */
    private void loadCheckBoxState() {
        loadCheckBoxState(checkBox_password, checkBox_login);
    }

    /**
     * 保存按钮的状态值
     */
    public void loadCheckBoxState(CheckBox checkBox_password, CheckBox checkBox_login) {

        //如果设置自动登录
        SharedPreferences.Editor editor = sp.edit();
        if (checkBox_login.isChecked()) {
            //创建记住密码和自动登录是都选择,保存密码数据
            editor.putString("password",getPassword());
            editor.putBoolean("rememberpassword",true);
            editor.putBoolean("autologin",true);
        } else if (!checkBox_password.isChecked()) { //如果没有保存密码，那么自动登录也是不选的
            //创建记住密码和自动登录是默认不选,密码为空
            editor.putString("password","");
            editor.putBoolean("rememberpassword",false);
            editor.putBoolean("autologin",false);
        } else if (checkBox_password.isChecked()) {   //如果保存密码，没有自动登录
            //创建记住密码为选中和自动登录是默认不选,保存密码数据
            editor.putString("password",getPassword());
            editor.putBoolean("rememberpassword",true);
            editor.putBoolean("autologin",false);
        }
        editor.commit();
    }

    /**
     * 是否可以点击登录按钮
     *
     * @param clickable
     */
    public void setLoginBtnClickable(boolean clickable) {
        mLoginBtn.setClickable(clickable);
    }


    /**
     * 显示加载的进度款
     */
    public void showLoading() {
        mLoadingDialog = new LoadingDialog(this);
        mLoadingDialog.setLoadingText("加载中")
              .setSuccessText("登陆成功")//显示加载成功时的文字
              .setFailedText("登陆失败")
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


    /**
     * CheckBox点击时的回调方法 ,不管是勾选还是取消勾选都会得到回调
     *
     * @param buttonView 按钮对象
     * @param isChecked  按钮的状态
     */
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == checkBox_password) {  //记住密码选框发生改变时
            if (!isChecked) {   //如果取消“记住密码”，那么同样取消自动登陆
                checkBox_login.setChecked(false);
            }
        } else if (buttonView == checkBox_login) {   //自动登陆选框发生改变时
            if (isChecked) {   //如果选择“自动登录”，那么同样选中“记住密码”
                checkBox_password.setChecked(true);
            }
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

    /**
     * 页面销毁前回调的方法
     */
    protected void onDestroy() {

        if (mLoadingDialog != null) {
            mLoadingDialog.close();
            mLoadingDialog = null;
        }
        super.onDestroy();

    }


    public void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this,msg, Toast.LENGTH_SHORT).show();
            }
        });

    }

}



