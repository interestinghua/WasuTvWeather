package com.lbs.weatherdemo.activity.entity;

/**
 * Created by mars on 2015/4/3.
 */
public class WeatherEntity {
    /**
     * "sj":"今天","dn":"3日","wea":"阴","tem1":"°C","img1":"jpg50",
     * "img2":"n02","tem2":"14°C","win":"无持续风向微风","sunUp":"","sunDown":""
     */

    private String sj;
    private String dn;
    private String wea;
    private String tem1;
    private String img1;
    private String img2;
    private String tem2;
    private String win;
    private String sunUp;
    private String sunDown;

    public WeatherEntity() {
    }

    public String getSj() {
        return sj;
    }

    public void setSj(String sj) {
        this.sj = sj;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public String getWea() {
        return wea;
    }

    public void setWea(String wea) {
        this.wea = wea;
    }

    public String getTem1() {
        return tem1;
    }

    public void setTem1(String tem1) {
        this.tem1 = tem1;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getTem2() {
        return tem2;
    }

    public void setTem2(String tem2) {
        this.tem2 = tem2;
    }

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }

    public String getSunUp() {
        return sunUp;
    }

    public void setSunUp(String sunUp) {
        this.sunUp = sunUp;
    }

    public String getSunDown() {
        return sunDown;
    }

    public void setSunDown(String sunDown) {
        this.sunDown = sunDown;
    }
}
