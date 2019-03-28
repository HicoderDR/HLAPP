package com.example.jsrgjhl.hlapp.Adapter;

/**
 * Created by xuxiaojin on 2019/3/24.
 */

public class Solutions {
    private String recordnum;
    private String solutionTitle;
    private String solutionContext;
    private String delTime;

    public Solutions(String recordnum, String solutionTitle, String solutionContext, String delTime) {
        this.recordnum = recordnum;
        this.solutionTitle = solutionTitle;
        this.solutionContext = solutionContext;
        this.delTime = delTime;
    }

    public String getRecordnum() {
        return recordnum;
    }

    public void setRecordnum(String recordnum) {
        this.recordnum = recordnum;
    }

    public String getSolutionTitle() {
        return solutionTitle;
    }

    public void setSolutionTitle(String solutionTitle) {
        this.solutionTitle = solutionTitle;
    }

    public String getSolutionContext() {
        return solutionContext;
    }

    public void setSolutionContext(String solutionContext) {
        this.solutionContext = solutionContext;
    }

    public String getDelTime() {
        return delTime;
    }

    public void setDelTime(String delTime) {
        this.delTime = delTime;
    }
}
