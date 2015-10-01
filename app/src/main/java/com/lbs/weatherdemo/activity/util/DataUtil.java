package com.lbs.weatherdemo.activity.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mars on 2015/4/7.
 */
public class DataUtil{

    public static String getDate(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return formatter.format(date);
    }

}