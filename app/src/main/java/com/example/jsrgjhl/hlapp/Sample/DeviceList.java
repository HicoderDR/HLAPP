package com.example.jsrgjhl.hlapp.Sample;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DeviceList {
    private Devicepoint[] devicelist=new Devicepoint[100];
    private int num = 0;
    public DeviceList(){
        Devicepoint a= new Devicepoint(1,"rader",30.833825,121.506413);
        this.append(a);
        Devicepoint b= new Devicepoint(2,"rader",30.833825,121.505513);
        this.append(b);
        Devicepoint c= new Devicepoint(3,"rader",30.832825,121.505513);
        this.append(c);
    }
    public Devicepoint getbyId(int x){
        return devicelist[x];
    }
    private void append(Devicepoint x){
        this.devicelist[num++]=x;
    }
    public int size(){
        return num;
    }
}
