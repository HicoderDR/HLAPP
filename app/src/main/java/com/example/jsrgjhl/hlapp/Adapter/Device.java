package com.example.jsrgjhl.hlapp.Adapter;

import java.io.Serializable;

/**
 * Created by xuxiaojin on 2018/12/7.
 */

public class Device implements Serializable {
    private int deviceID;

    private String devicenum;

    private Double devicelat;

    private Double devicelng;

    private String deviceaddress;

    private String devicestatus;

    private String devicetype;

    private String regionID;

    private String defposID;

    private String IP;

    public Device(String devicenum, Double devicelat, Double devicelng, String deviceaddress, String devicestatus, String devicetype, String regionID, String defposID, String IP){
        this.defposID=defposID;
        this.deviceaddress=deviceaddress;
        this.devicenum=devicenum;
        this.devicelat=devicelat;
        this.devicelng=devicelng;
        this.devicestatus=devicestatus;
        this.devicetype=devicetype;
        this.IP=IP;
        this.regionID=regionID;
    }
    public Device(int deviceID, String devicenum, Double devicelat, Double devicelng, String deviceaddress, String devicestatus, String devicetype, String regionID, String defposID, String IP){
        this.deviceID=deviceID;
        this.defposID=defposID;
        this.deviceaddress=deviceaddress;
        this.devicenum=devicenum;
        this.devicelat=devicelat;
        this.devicelng=devicelng;
        this.devicestatus=devicestatus;
        this.devicetype=devicetype;
        this.IP=IP;
        this.regionID=regionID;
    }
    public int getDeviceID() {
        return deviceID;
    }

    public Double getDevicelat() {
        return devicelat;
    }

    public Double getDevicelng() {
        return devicelng;
    }

    public String getDeviceaddress() {
        return deviceaddress;
    }

    public String getDevicenum() {
        return devicenum;
    }

    public String getDefposID() {
        return defposID;
    }

    public String getDevicestatus() {
        return devicestatus;
    }

    public String getDevicetype() {
        return devicetype;
    }

    public String getRegionID() {
        return regionID;
    }

    public void setDefposID(String defposID) {
        this.defposID = defposID;
    }

    public String getIP() {
        return IP;
    }

    public void setDeviceaddress(String deviceaddress) {
        this.deviceaddress = deviceaddress;
    }

    public void setDeviceID(int deviceID) {
        this.deviceID = deviceID;
    }

    public void setDevicelat(Double devicelat) {
        this.devicelat = devicelat;
    }

    public void setDevicelng(Double devicelng) {
        this.devicelng = devicelng;
    }

    public void setDevicenum(String devicenum) {
        this.devicenum = devicenum;
    }

    public void setDevicestatus(String devicestatus) {
        this.devicestatus = devicestatus;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype;
    }

    public void setRegionID(String regionID) {
        this.regionID = regionID;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

}