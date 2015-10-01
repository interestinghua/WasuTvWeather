package com.lbs.weatherdemo.activity.entity;

/**
 * Created by mars on 2015/4/3.
 */
public class ShiKuangEntity {
    /**
     * "time":"16:15","temp":"22","sd":"75%","cityname":"温州",
     * "nameen":"wenzhou","city":"101210701","aqi":"72","qy":"1004"
     */

    private String time;
    private String temp;
    private String sd;
    private String cityname;
    private String nameen;
    private String city;
    private String aqi;
    private String qy;

    public ShiKuangEntity() {
    }

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

    public String getSd() {
        return sd;
    }

    public void setSd(String sd) {
        this.sd = sd;
    }

    public String getNameen() {
        return nameen;
    }

    public void setNameen(String nameen) {
        this.nameen = nameen;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getQy() {
        return qy;
    }

    public void setQy(String qy) {
        this.qy = qy;
    }
}
