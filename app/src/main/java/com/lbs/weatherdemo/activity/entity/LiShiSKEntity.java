package com.lbs.weatherdemo.activity.entity;

/**
 * Created by mars on 2015/4/21.
 */
public class LiShiSKEntity {
    /**
     * {"time":"11","temp":"18","wd":"东南风","ws":"2","sd":"41","rain":"0"}
     */

    private String time;
    private String temp;
    private String wd;
    private String ws;
    private String sd;
    private String rain;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getWd() {
        return wd;
    }

    public void setWd(String wd) {
        this.wd = wd;
    }

    public String getWs() {
        return ws;
    }

    public void setWs(String ws) {
        this.ws = ws;
    }

    public String getSd() {
        return sd;
    }

    public void setSd(String sd) {
        this.sd = sd;
    }

    public String getRain() {
        return rain;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }
}
