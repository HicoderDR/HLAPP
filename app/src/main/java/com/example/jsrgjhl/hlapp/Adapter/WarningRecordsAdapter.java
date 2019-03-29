package com.example.jsrgjhl.hlapp.Adapter;

import android.content.Context;
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

public class WarningRecordsAdapter extends ArrayAdapter<Records>{

    private int resourceId;
    public WarningRecordsAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<Records> objects) {
        super(context, textViewResourceId, objects);
        resourceId=textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Records records=getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.timeTextview=(TextView)view.findViewById(R.id.timetv);
            viewHolder.addressTextview=(TextView)view.findViewById(R.id.addresstv);
            viewHolder.idTextview=(TextView)view.findViewById(R.id.idtv);
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            Date dt=df.parse(records.getTime());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date=format.format(dt);
            viewHolder.timeTextview.setText(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        viewHolder.idTextview.setText(records.getId());
        viewHolder.addressTextview.setText(records.getAddress());

        return view;
    }
    class ViewHolder{
        TextView timeTextview;
        TextView addressTextview;
        TextView idTextview;
    }

}
