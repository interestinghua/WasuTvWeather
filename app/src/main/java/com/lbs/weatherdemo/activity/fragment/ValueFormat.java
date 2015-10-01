package com.lbs.weatherdemo.activity.fragment;

import com.github.mikephil.charting.utils.ValueFormatter;

/**
 * Created by mars on 2015/4/22.
 */
public class ValueFormat implements ValueFormatter {


    @Override
    public String getFormattedValue(float v) {
        String rslt=v+"°C";
        return rslt;
    }
}
