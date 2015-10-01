package com.lbs.weatherdemo.activity.util;

import android.app.Application;

import com.lbs.weatherdemo.activity.entity.AQIEntity;
import com.lbs.weatherdemo.activity.entity.CenterPoint;
import com.lbs.weatherdemo.activity.entity.HighWayEntity;
import com.lbs.weatherdemo.activity.entity.IndexEntity;
import com.lbs.weatherdemo.activity.entity.ShiKuangEntity;
import com.lbs.weatherdemo.activity.entity.WeatherEntity;
import com.lbs.weatherdemo.activity.entity.YBFSEntity;
import com.lbs.weatherdemo.activity.entity.YBTodayEntity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppGlobal extends Application {

    private static AppGlobal instance;

	public AppGlobal() {}

    //http://115.29.199.117:8089/weather/json/20150407/101210501.json
    public static String SERVERIP="115.29.199.117";
    public static String SERVERPORT="8089";
    public static String URL="http://"+AppGlobal.SERVERIP+":"+AppGlobal.SERVERPORT+"/weather/json/"+DataUtil.getDate(new Date())+"/101210101.json";
    public static String AQIURL="http://"+AppGlobal.SERVERIP+":"+AppGlobal.SERVERPORT+"/weather/json/"+DataUtil.getDate(new Date())+"/aqi.json";
    public static String TYPHOONSERVER="http://www.lbschina.com.cn:8089/typhoonserver/DataCache/";
//    public static String CONTENT="[{\"ID\":\"201419\",\"NAME\":\"Vongfong\",\"CNAME\":\"黄蜂\",\"running\":\"true\",\"ZQ\":\"false\",\"warn_level\":\"0\",\"image\":\"\",\"Introduction\":\"\",\"start_time\":\"2014-10-03 20:00:00\",\"YQ\":\"false\"},{\"ID\":\"201418\",\"NAME\":\"Phanfone\",\"CNAME\":\"巴蓬\",\"running\":\"false\",\"ZQ\":\"false\",\"warn_level\":\"0\",\"image\":\"\",\"Introduction\":\"\",\"start_time\":\"2014-09-29 14:00:00\",\"YQ\":\"false\"},{\"ID\":\"201417\",\"NAME\":\"Kammuri\",\"CNAME\":\"北冕\",\"running\":\"false\",\"ZQ\":\"false\",\"warn_level\":\"0\",\"image\":\"\",\"Introduction\":\"\",\"start_time\":\"2014-09-24 20:00:00\",\"YQ\":\"false\"},{\"ID\":\"201416\",\"NAME\":\"Fung-wong\",\"CNAME\":\"凤凰\",\"running\":\"false\",\"ZQ\":\"false\",\"warn_level\":\"0\",\"image\":\"\",\"Introduction\":\"\",\"start_time\":\"2014-09-18 02:00:00\",\"YQ\":\"false\"}]";

    private ShiKuangEntity shiKuangEntity = new ShiKuangEntity();

    private List<IndexEntity> indexEntityList=new ArrayList<>();
    private List<WeatherEntity> weatherEntityList=new ArrayList<>();
    private List<YBTodayEntity> ybTodayEntityList=new ArrayList<>();
    private List<YBFSEntity> ybfsEntityList=new ArrayList<>();
    private List<HighWayEntity> highWayEntityList =new ArrayList<>();

    private List<AQIEntity> aqiEntityList =new ArrayList<>();

    private List<CenterPoint> cpList = new ArrayList<>();

    //台风路径最后一点
    CenterPoint cp=new CenterPoint();

    private String dataStr;
    private String oldDateStr;

    private int width=0;
    private int height=0;

    //当天天气
    private String weather;
    private String imgStr;
    private String aqiStr;
    private String citycode;

    //当前城市
    private String topCity="杭州";
    private String secondCity="市区";

    //城市级联hashmap
    private Map<String,List<String>> cityMap=new HashMap<>();

	public static void main(String[] args) {}

    public static AppGlobal getInstance(){
        if(null == instance){
            instance = new AppGlobal();
        }
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();

    }

    public String getTopCity() {
        return topCity;
    }

    public void setTopCity(String topCity) {
        this.topCity = topCity;
    }

    public String getSecondCity() {
        return secondCity;
    }

    public void setSecondCity(String secondCity) {
        this.secondCity = secondCity;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public void setCityMap(Map<String, List<String>> cityMap) {
        this.cityMap = cityMap;
    }

    public Map<String, List<String>> getCityMap() {
        return this.cityMap;
    }

    public String getURL(String roadname,String sectionname){
        try {
            return "http://202.96.202.172:8080/qixiangyewu/getRoadSectionLastData.action?roadName="+ URLEncoder.encode(roadname,"utf-8")+"&sectionname="+URLEncoder.encode(sectionname,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ShiKuangEntity getShiKuangEntity() {
        return shiKuangEntity;
    }

    public void setShiKuangEntity(ShiKuangEntity shiKuangEntity) {
        this.shiKuangEntity = shiKuangEntity;
    }

    public List<WeatherEntity> getWeatherEntityList() {
        return weatherEntityList;
    }

    public void setWeatherEntityList(List<WeatherEntity> weatherEntityList) {
        this.weatherEntityList = weatherEntityList;
    }

    public String getDataStr() {
        return dataStr;
    }

    public void setDataStr(String dataStr) {
        this.dataStr = dataStr;
    }

    public String getOldDateStr() {
        return oldDateStr;
    }

    public void setOldDateStr(String oldDateStr) {
        this.oldDateStr = oldDateStr;
    }

    public List<IndexEntity> getIndexEntityList() {
        return indexEntityList;
    }

    public void setIndexEntityList(List<IndexEntity> indexEntityList) {
        this.indexEntityList = indexEntityList;
    }

    public void setYbTodayEntityList(List<YBTodayEntity> ybTodayEntityList) {
        this.ybTodayEntityList = ybTodayEntityList;
    }

    public List<YBTodayEntity> getYbTodayEntityList() {
        return ybTodayEntityList;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public CenterPoint getCp() {
        return cp;
    }

    public void setCp(CenterPoint cp) {
        this.cp = cp;
    }

    public List<CenterPoint> getCpList() {
        return cpList;
    }

    public void setCpList(List<CenterPoint> cpList) {
        this.cpList = cpList;
    }

    public List<YBFSEntity> getYbfsEntityList() {
        return ybfsEntityList;
    }

    public void setYbfsEntityList(List<YBFSEntity> ybfsEntityList) {
        this.ybfsEntityList = ybfsEntityList;
    }

    public List<HighWayEntity> getHighWayEntityList() {
        return highWayEntityList;
    }

    public void setHighWayEntityList(List<HighWayEntity> highWayEntityList) {
        this.highWayEntityList = highWayEntityList;
    }

    public List<AQIEntity> getAqiEntityList() {
        return aqiEntityList;
    }

    public void setAqiEntityList(List<AQIEntity> aqiEntityList) {
        this.aqiEntityList = aqiEntityList;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getImgStr() {
        return imgStr;
    }

    public void setImgStr(String imgStr) {
        this.imgStr = imgStr;
    }

    public String getAqiStr() {
        return aqiStr;
    }

    public void setAqiStr(String aqiStr) {
        this.aqiStr = aqiStr;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }
}
