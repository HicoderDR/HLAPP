package com.example.jsrgjhl.hlapp.Adapter;

import java.io.Serializable;

/**
 * Created by xuxiaojin on 2018/12/7.
 */

public class Device implements Serializable {
    private String id;
    private String sort;
    private String situation;
    private String address;
    private String ipaddress;
    private String about;
    private String defend;
    private String region;
    private String other; //参数设置

    public Device(String id, String sort, String situation, String address, String ipaddress, String about, String defend, String region, String other) {
        this.id = id;
        this.sort = sort;
        this.situation = situation;
        this.address = address;
        this.ipaddress = ipaddress;
        this.about = about;
        this.defend = defend;
        this.region = region;
        this.other = other;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getDefend() {
        return defend;
    }

    public void setDefend(String defend) {
        this.defend = defend;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
