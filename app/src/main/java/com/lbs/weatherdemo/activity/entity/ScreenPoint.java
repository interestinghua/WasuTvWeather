package com.lbs.weatherdemo.activity.entity;

/**
 * Created by mars on 2015/4/8.
 */
public class ScreenPoint {
    private int screenX;
    private int screenY;

    public ScreenPoint(int screenX, int screenY) {
        this.screenX = screenX;
        this.screenY = screenY;
    }

    public int getScreenX() {
        return screenX;
    }

    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public void setScreenY(int screenY) {
        this.screenY = screenY;
    }
}
