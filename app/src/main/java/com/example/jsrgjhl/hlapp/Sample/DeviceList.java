package com.example.jsrgjhl.hlapp.Sample;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DeviceList {
    private Devicepoint[] devicelist=new Devicepoint[100];
    private int num = 0;
    public DeviceList(String type){
        switch (type){
            case "radar":
                Devicepoint a= new Devicepoint(1,"radar",30.833825,121.506413);
                this.append(a);
                a= new Devicepoint(2,"radar",30.833825,121.505513);
                this.append(a);
                a= new Devicepoint(3,"radar",30.832825,121.505513);
                this.append(a);
                break;
            case "camera":
                Devicepoint b= new Devicepoint(1,"camera",30.833825,121.499413);
                this.append(b);
                b= new Devicepoint(2,"camera",30.831825,121.505513);
                this.append(b);
                b= new Devicepoint(3,"camera",30.837825,121.502513);
                this.append(b);
                break;
        }
    }
    public Devicepoint getbyId(int x){
        return this.devicelist[x];
    }
    private void append(Devicepoint x){
        this.devicelist[num++]=x;
    }
    public int size(){
        return this.num;
    }
}
