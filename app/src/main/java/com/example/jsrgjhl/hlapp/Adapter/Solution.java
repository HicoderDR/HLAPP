package com.example.jsrgjhl.hlapp.Adapter;

import java.util.Date;

/**
 * Created by xuxiaojin on 2019/3/29.
 */

public class Solution {
    private String solutionID;
    private String recordID;
    private String recordnum;
    private String deltime;
    private String userID;
    private String username;
    private String title;
    private String context;
    private String devicenum;

    public Solution(){}

    public Solution(String solutionID, String recordID, String recordnum, String deltime, String userID, String username, String title, String context, String devicenum) {
        this.solutionID = solutionID;
        this.recordID = recordID;
        this.recordnum = recordnum;
        this.deltime = deltime;
        this.userID = userID;
        this.username = username;
        this.title = title;
        this.context = context;
        this.devicenum = devicenum;
    }

    public Solution(String deltime, String title, String context, String devicenum) {
        this.deltime = deltime;
        this.title = title;
        this.context = context;
        this.devicenum = devicenum;
    }

    public String getSolutionID() {
        return solutionID;
    }

    public void setSolutionID(String solutionID) {
        this.solutionID = solutionID;
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

    public String getDeltime() {
        return deltime;
    }

    public void setDeltime(String deltime) {
        this.deltime = deltime;
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

    public String getDevicenum() {
        return devicenum;
    }

    public void setDevicenum(String devicenum) {
        this.devicenum = devicenum;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "solutionID='" + solutionID + '\'' +
                ", recordID='" + recordID + '\'' +
                ", recordnum='" + recordnum + '\'' +
                ", deltime='" + deltime + '\'' +
                ", userID='" + userID + '\'' +
                ", username='" + username + '\'' +
                ", title='" + title + '\'' +
                ", context='" + context + '\'' +
                ", devicenum='" + devicenum + '\'' +
                '}';
    }
}
