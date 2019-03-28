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

import java.util.List;

/**
 * Created by xuxiaojin on 2018/12/7.
 */

public class DeviceAdapter extends ArrayAdapter<Device> {

    private int resourceId;
    public DeviceAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<Device> objects) {
        super(context, textViewResourceId, objects);
        resourceId=textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Device device=getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.idTextview=(TextView)view.findViewById(R.id.id_tv);
            viewHolder.situationTextview=(TextView)view.findViewById(R.id.situation_tv);
            viewHolder.addressTextview=(TextView)view.findViewById(R.id.address_tv);
            viewHolder.sortTextview=(TextView)view.findViewById(R.id.sort_tv);
            viewHolder.otherTextview=(TextView)view.findViewById(R.id.device_setting_tv);
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.idTextview.setText(device.getDevicenum());
        viewHolder.situationTextview.setText(device.getDevicestatus());
        viewHolder.addressTextview.setText(device.getDeviceaddress());
        viewHolder.sortTextview.setText(device.getDevicetype());
        viewHolder.otherTextview.setText(device.getIP());

        return view;
    }
    class ViewHolder{
        TextView idTextview;
        TextView sortTextview;
        TextView situationTextview;
        TextView addressTextview;
        TextView otherTextview;
    }

}
