package com.example.jsrgjhl.hlapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jsrgjhl.hlapp.Adapter.Device;
import com.example.jsrgjhl.hlapp.Adapter.Records;
import com.example.jsrgjhl.hlapp.Adapter.RecordsAdapter;
import com.example.jsrgjhl.hlapp.R;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SolveActivity extends AppCompatActivity implements Serializable {
    private EditText tabletitle,tablecontent;
    private TextView Warn_status;
    private TextView Slove_status,Timetv,Addresstv,Idtv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve);



        Intent intent = getIntent();
        Records record = (Records)intent.getSerializableExtra("record");

        tablecontent=findViewById(R.id.tablecontent);
        tabletitle=findViewById(R.id.tabletitle);
        Warn_status=findViewById(R.id.warn_status);
        Slove_status=findViewById(R.id.slove_status);
        Timetv=findViewById(R.id.timetv);
        Addresstv=findViewById(R.id.addresstv);
        Idtv=findViewById(R.id.idtv);
        Warn_status.setText(record.getWarn_status());
        Slove_status.setText(record.getSolve_status());
        Timetv.setText(record.getTime());
        Idtv.setText(record.getId());
        Addresstv.setText(record.getAddress());


    }
}
