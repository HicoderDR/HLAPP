package com.example.jsrgjhl.hlapp.Adapter;
import java.io.Serializable;
/**
 * Created by xuxiaojin on 2018/12/7.
 */

public class Records implements Serializable {
    private String warn_status;
    private String solve_status;
    private String time;
    private String address;
    private String id;
    private String solve_title;
    private String solve_context;

    public Records(String warn_status, String solve_status, String time, String address, String id, String solve_title, String solve_context) {
        this.warn_status = warn_status;
        this.solve_status = solve_status;
        this.time = time;
        this.address = address;
        this.id = id;
        this.solve_title = solve_title;
        this.solve_context = solve_context;
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

    public String getSolve_title() {
        return solve_title;
    }

    public void setSolve_title(String solve_title) {
        this.solve_title = solve_title;
    }

    public String getSolve_context() {
        return solve_context;
    }

    public void setSolve_context(String solve_context) {
        this.solve_context = solve_context;
    }
}