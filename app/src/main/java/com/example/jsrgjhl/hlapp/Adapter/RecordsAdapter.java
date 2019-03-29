package com.example.jsrgjhl.hlapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jsrgjhl.hlapp.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by xuxiaojin on 2018/12/7.
 */

public class RecordsAdapter extends ArrayAdapter<Record>{

    private int resourceId;
    public RecordsAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<Record> objects) {
        super(context, textViewResourceId, objects);
        resourceId=textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Record record=getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.warnStatusTextview=(TextView)view.findViewById(R.id.warn_status);
            viewHolder.solveStatusTextview=(TextView)view.findViewById(R.id.slove_status);
            viewHolder.timeTextview=(TextView)view.findViewById(R.id.timetv);
            viewHolder.addressTextview=(TextView)view.findViewById(R.id.addresstv);
            viewHolder.idTextview=(TextView)view.findViewById(R.id.idtv);
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.warnStatusTextview.setText(record.getDevicestatus());
        viewHolder.solveStatusTextview.setText(record.getRecordstatus());
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            Date dt=df.parse(String.valueOf(record.getRecordtime()));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date=format.format(dt);
            viewHolder.timeTextview.setText(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        viewHolder.idTextview.setText(String.valueOf(record.getDevicenum()));
        viewHolder.addressTextview.setText(String.valueOf(record.getDeviceaddress()));

        //由不同的处理状态显示不同的数据
        if(record.getRecordstatus().equals("已处理")){
            viewHolder.solveStatusTextview.setTextColor(Color.rgb(00, 00, 00));
        }
        if(record.getRecordstatus().equals("未处理")){
            viewHolder.solveStatusTextview.setTextColor(Color.rgb(255,0,0));
        }
        return view;
    }
    class ViewHolder{
        TextView warnStatusTextview;
        TextView solveStatusTextview;
        TextView timeTextview;
        TextView addressTextview;
        TextView idTextview;
    }

}
