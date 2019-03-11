package com.example.jsrgjhl.hlapp.PersonalSetting;

import android.app.Person;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.jsrgjhl.hlapp.R;

import java.util.ArrayList;

public class PersonalAccount extends AppCompatActivity {

    private TextView changePasswordTv;
    private TextView areaDefenseTv;
    private ArrayList<String> areas = new ArrayList<>();
    private ArrayList<String> defenses = new ArrayList<>();
    private OptionsPickerView pvNoLinkOptions;

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

    private void initNoLinkOptionsPicker() {// 不联动的多级选项
        pvNoLinkOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                String str =  areas.get(options1)
                        + "/" + defenses.get(options2);
                areaDefenseTv.setText(str);
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
