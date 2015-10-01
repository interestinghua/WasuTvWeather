package com.lbs.weatherdemo.activity.util;

import android.content.Context;

import com.lbs.weatherdemo.activity.entity.ScreenPoint;

/**
 * Created by mars on 2015/4/7.
 */
public class LatLon2ScreenUtil {
    //137.724609,21.861499

    private Context context;

    private double maxLon=137.724609;
    private double minLon=103.093792;
    private double maxLat=38.62435;
    private double minLat=21.861499;

    private int screenX=0;
    private int screenY=0;

    private double scaleX=0.0;
    private double scaleY=0.0;
    private int width=0;
    private int height=0;


    public LatLon2ScreenUtil(Context context) {

        this.context = context;
//        WindowManager wm = (WindowManager)this.context.getSystemService(Context.WINDOW_SERVICE);
//        width = wm.getDefaultDisplay().getWidth();
//        height = wm.getDefaultDisplay().getHeight();

        width=AppGlobal.getInstance().getWidth();
        height=AppGlobal.getInstance().getHeight();
        scaleX = ((maxLon-minLon)*3600)/width;
        scaleY = ((maxLat-minLat)*3600)/height;
    }

    public ScreenPoint latLon2Screen(double lon,double lat){
        screenX = (int) ((lon - minLon)*3600/scaleX);
        screenY = (int) ((maxLat - lat)*3600/scaleY);
        
        ScreenPoint sp=new ScreenPoint(screenX,screenY);
        return sp;
    }
}
