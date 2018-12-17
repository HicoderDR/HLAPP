package com.example.jsrgjhl.hlapp.Adapter;

/**
 * Created by xuxiaojin on 2018/12/7.
 */

public class Records {
    private String warn_status;
    private String solve_status;
    private String time;
    private String address;
    private String id;

    public Records(String warn_status, String solve_status, String time, String address, String id) {
        this.warn_status = warn_status;
        this.solve_status = solve_status;
        this.time = time;
        this.address = address;
        this.id = id;
    }

    public String getWarn_status() {
        return warn_status;
    }

    public void setWarn_status(String warn_status) {
        this.warn_status = warn_status;
    }

    public String getSolve_status() {
        return solve_status;
    }

    public void setSolve_status(String solve_status) {
        this.solve_status = solve_status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
