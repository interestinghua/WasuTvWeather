package com.lbs.weatherdemo.activity.entity;

/**
 * Created by mars on 2015/4/21.
 */
public class HighWayEntity {
    /**
     * "twoAverWs":2
     "temper":14.1
     "relHumi":77
     "instantVis":7177
     "twoAverWd":290
     "date":"2015-04-20 15:03:00"
     "roadId":"G15"
     */

    //风速
    private String twoAverWs;
    //温度
    private String temper;
    //相对湿度
    private String relHumi;
    //能见度（米）
    private String instantVis;
    //风向
    private String twoAverWd;
    //日期
    private String date;
    //道路ID
    private String roadId;

    private String roadName;
    private String sectionname;

    public String getTwoAverWs() {
        return twoAverWs;
    }

    public void setTwoAverWs(String twoAverWs) {
        this.twoAverWs = twoAverWs;
    }

    public String getTemper() {
        return temper;
    }

    public void setTemper(String temper) {
        this.temper = temper;
    }

    public String getRelHumi() {
        return relHumi;
    }

    public void setRelHumi(String relHumi) {
        this.relHumi = relHumi;
    }

    public String getInstantVis() {
        return instantVis;
    }

    public void setInstantVis(String instantVis) {
        this.instantVis = instantVis;
    }

    public String getTwoAverWd() {
        return twoAverWd;
    }

    public void setTwoAverWd(String twoAverWd) {
        this.twoAverWd = twoAverWd;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRoadId() {
        return roadId;
    }

    public void setRoadId(String roadId) {
        this.roadId = roadId;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public String getSectionname() {
        return sectionname;
    }

    public void setSectionname(String sectionname) {
        this.sectionname = sectionname;
    }
}
