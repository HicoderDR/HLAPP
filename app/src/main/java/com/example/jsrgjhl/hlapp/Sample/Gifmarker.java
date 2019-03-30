package com.example.jsrgjhl.hlapp.Sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.example.jsrgjhl.hlapp.R;

import java.util.ArrayList;

public class Gifmarker {
    public ArrayList<BitmapDescriptor> iconList = new ArrayList<>();
    public Gifmarker(String type, Context mcontext){
        View gif1 = LayoutInflater.from(mcontext).inflate(R.layout.marker_camera_r,null);
        View gif2 = LayoutInflater.from(mcontext).inflate(R.layout.marker_camera_r,null);
        View gif3= LayoutInflater.from(mcontext).inflate(R.layout.marker_radar_r,null);
        View gif4 = LayoutInflater.from(mcontext).inflate(R.layout.marker_radar_r,null);
        //View gif5 = LayoutInflater.from(mcontext).inflate(R.layout.marker_radar_r1,null);
        //View gif6 = LayoutInflater.from(mcontext).inflate(R.layout.marker_radar_r2,null);
        if(type.equals("camera")){
            iconList.add(BitmapDescriptorFactory.fromView(gif1));
            iconList.add(BitmapDescriptorFactory.fromView(gif2));
        }
        else if (type.equals("radar")){
            iconList.add(BitmapDescriptorFactory.fromView(gif3));
            iconList.add(BitmapDescriptorFactory.fromView(gif4));
        }
        else if(type.equals("vibration")) {
            /*
            iconList.add(BitmapDescriptorFactory.fromResource(R.drawable.gif1));
            iconList.add(BitmapDescriptorFactory.fromResource(R.drawable.gif2));
            iconList.add(BitmapDescriptorFactory.fromResource(R.drawable.gif3));
            iconList.add(BitmapDescriptorFactory.fromResource(R.drawable.gif4));
            iconList.add(BitmapDescriptorFactory.fromResource(R.drawable.gif5));
            iconList.add(BitmapDescriptorFactory.fromResource(R.drawable.gif6));
             */
        }
    }
}
