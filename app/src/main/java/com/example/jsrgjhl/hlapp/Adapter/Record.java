package com.example.jsrgjhl.hlapp.Adapter;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by xuxiaojin on 2018/12/7.
 */

public class Record implements Serializable {
    private String recordID;
    private String recordnum;
    private String recordtime;
    private String recordstatus;
    private String solutionID;
    private String userID;
    private String username;
    private String title;
    private String context;
    private String deviceID;
    private String devicenum;
    private String deviceaddress;
    private String regionID;
    private String defposID;
    private String devicelat;
    private String devicelng;
    private String devicetype;
    private String devicestatus;
    private String deltime;

    public Record(){}

    public Record(String recordID, String recordnum, String recordtime, String recordstatus, String solutionID, String userID, String title, String context, String deviceID, String devicenum, String deviceaddress, String regionID, String defposID, String devicelat, String devicelng, String devicetype, String devicestatus, String deltime) {
        this.recordID = recordID;
        this.recordnum = recordnum;
        this.recordtime = recordtime;
        this.recordstatus = recordstatus;
        this.solutionID = solutionID;
        this.userID = userID;
        this.title = title;
        this.context = context;
        this.deviceID = deviceID;
        this.devicenum = devicenum;
        this.deviceaddress = deviceaddress;
        this.regionID = regionID;
        this.defposID = defposID;
        this.devicelat = devicelat;
        this.devicelng = devicelng;
        this.devicetype = devicetype;
        this.devicestatus = devicestatus;
        this.deltime = deltime;
    }

    public String getRecordID() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

    public String getRecordnum() {
        return recordnum;
    }

    public void setRecordnum(String recordnum) {
        this.recordnum = recordnum;
    }

    public String getRecordtime() {
        return recordtime;
    }

    public void setRecordtime(String recordtime) {
        this.recordtime = recordtime;
    }

    public String getRecordstatus() {
        return recordstatus;
    }

    public void setRecordstatus(String recordstatus) {
        this.recordstatus = recordstatus;
    }

    public String getSolutionID() {
        return solutionID;
    }

    public void setSolutionID(String solutionID) {
        this.solutionID = solutionID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDevicenum() {
        return devicenum;
    }

    public void setDevicenum(String devicenum) {
        this.devicenum = devicenum;
    }

    public String getDeviceaddress() {
        return deviceaddress;
    }

    public void setDeviceaddress(String deviceaddress) {
        this.deviceaddress = deviceaddress;
    }

    public String getRegionID() {
        return regionID;
    }

    public void setRegionID(String regionID) {
        this.regionID = regionID;
    }

    public String getDefposID() {
        return defposID;
    }

    public void setDefposID(String defposID) {
        this.defposID = defposID;
    }

    public String getDevicelat() {
        return devicelat;
    }

    public void setDevicelat(String devicelat) {
        this.devicelat = devicelat;
    }

    public String getDevicelng() {
        return devicelng;
    }

    public void setDevicelng(String devicelng) {
        this.devicelng = devicelng;
    }

    public String getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype;
    }

    public String getDevicestatus() {
        return devicestatus;
    }

    public void setDevicestatus(String devicestatus) {
        this.devicestatus = devicestatus;
    }

    public String getDeltime() {
        return deltime;
    }

    public void setDeltime(String deltime) {
        this.deltime = deltime;
    }

    @Override
    public String toString() {
        return "Record{" +
                "recordID='" + recordID + '\'' +
                ", recordnum='" + recordnum + '\'' +
                ", recordtime='" + recordtime + '\'' +
                ", recordstatus='" + recordstatus + '\'' +
                ", solutionID='" + solutionID + '\'' +
                ", userID='" + userID + '\'' +
                ", username='" + username + '\'' +
                ", title='" + title + '\'' +
                ", context='" + context + '\'' +
                ", deviceID='" + deviceID + '\'' +
                ", devicenum='" + devicenum + '\'' +
                ", deviceaddress='" + deviceaddress + '\'' +
                ", regionID='" + regionID + '\'' +
                ", defposID='" + defposID + '\'' +
                ", devicelat='" + devicelat + '\'' +
                ", devicelng='" + devicelng + '\'' +
                ", devicetype='" + devicetype + '\'' +
                ", devicestatus='" + devicestatus + '\'' +
                ", deltime='" + deltime + '\'' +
                '}';
    }
}