package com.lbs.weatherdemo.activity.entity;

/**
 * Created by mars on 2015/4/14.
 */
public class CenterPoint {

    private String id;
    private double centerx;
    private double centery;
    private String type;
    private double radious7=0.0f;
    private double radious10=0.0f;
    private double fengsu=0.0f;
    private double qiya=0.0f;
    private double movespeed=0.0f;
    private String movedirection;
    private double fengli=0.0f;
    private String rqsj;
    private String name;
    private String ename;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getCenterx() {
        return centerx;
    }

    public void setCenterx(double centerx) {
        this.centerx = centerx;
    }

    public double getCentery() {
        return centery;
    }

    public void setCentery(double centery) {
        this.centery = centery;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getRadious7() {
        return radious7;
    }

    public void setRadious7(double radious7) {
        this.radious7 = radious7;
    }

    public double getRadious10() {
        return radious10;
    }

    public void setRadious10(double radious10) {
        this.radious10 = radious10;
    }

    public double getFengsu() {
        return fengsu;
    }

    public void setFengsu(double fengsu) {
        this.fengsu = fengsu;
    }

    public double getQiya() {
        return qiya;
    }

    public void setQiya(double qiya) {
        this.qiya = qiya;
    }

    public double getMovespeed() {
        return movespeed;
    }

    public void setMovespeed(double movespeed) {
        this.movespeed = movespeed;
    }

    public String getMovedirection() {
        return movedirection;
    }

    public void setMovedirection(String movedirection) {
        this.movedirection = movedirection;
    }

    public double getFengli() {
        return fengli;
    }

    public void setFengli(double fengli) {
        this.fengli = fengli;
    }

    public String getRqsj() {
        return rqsj;
    }

    public void setRqsj(String rqsj) {
        this.rqsj = rqsj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }
}
