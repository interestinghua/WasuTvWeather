package com.lbs.weatherdemo.activity.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.lbs.weatherdemo.R;
import com.lbs.weatherdemo.activity.entity.WeatherEntity;
import com.lbs.weatherdemo.activity.util.AppGlobal;

import java.util.ArrayList;
import java.util.List;

public class YuBaoFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private YAxis leftAxis=null;
    private YAxis rightAxis=null;

    private LineChart mChart;

    private ImageView img01;
    private ImageView img02;
    private ImageView img03;
    private ImageView img04;
    private ImageView img05;
    private ImageView img06;
    private ImageView img07;

    public static YuBaoFragment newInstance(String param1, String param2) {
        YuBaoFragment fragment = new YuBaoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public YuBaoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_yu_bao, container, false);

        img01= (ImageView) view.findViewById(R.id.img01);
        img02= (ImageView) view.findViewById(R.id.img02);
        img03= (ImageView) view.findViewById(R.id.img03);
        img04= (ImageView) view.findViewById(R.id.img04);
        img05= (ImageView) view.findViewById(R.id.img05);
        img06= (ImageView) view.findViewById(R.id.img06);
        img07= (ImageView) view.findViewById(R.id.img07);

        mChart = (LineChart) view.findViewById(R.id.linechart);
        mChart.setDrawGridBackground(false);
        mChart.setBackground(null);
        mChart.setDescription("");
        mChart.setNoDataTextDescription("没有可以展示的数据");
        mChart.setHighlightEnabled(true);
        mChart.animateX(1000, Easing.EasingOption.EaseInOutQuart);
        mChart.animateY(1500, Easing.EasingOption.EaseInCubic);

        //左上右下 160 160
        mChart.setViewPortOffsets(130f, 2f, 130f, 50f);

        Legend mLegend = mChart.getLegend();
        mLegend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        mLegend.setForm(Legend.LegendForm.LINE);
        mLegend.setFormSize(0f);

        leftAxis = mChart.getAxisLeft();
        leftAxis.setStartAtZero(false);
        leftAxis.setDrawLabels(false);
        leftAxis.enableGridDashedLine(10f, 0f, 0f);
        leftAxis.setTextSize(11f);
        leftAxis.setTextColor(getResources().getColor(R.color.wasu02));
        leftAxis.setAxisLineColor(getResources().getColor(R.color.wasu03));

        rightAxis = mChart.getAxisRight();
        rightAxis.setStartAtZero(false);
        rightAxis.setDrawLabels(false);
        rightAxis.enableGridDashedLine(10f, 0f, 0f);
        rightAxis.setTextSize(11f);
        rightAxis.setTextColor(getResources().getColor(R.color.wasu02));
        rightAxis.setAxisLineColor(getResources().getColor(R.color.wasu03));

        XAxis xAxis = mChart.getXAxis();
        xAxis.setAdjustXLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(22f);
        xAxis.setTextColor(getResources().getColor(R.color.wasu01));
        xAxis.setSpaceBetweenLabels(5);

        setData();
        return view;
    }

    private void setData() {

        ArrayList<String> xVals = new ArrayList<>();
        ArrayList<Entry> yValsMin = new ArrayList<>();
        ArrayList<Entry> yValsMax = new ArrayList<>();

        ArrayList<Float> ValsMin = new ArrayList<>();
        ArrayList<Float> ValsMax = new ArrayList<>();


        List<WeatherEntity> weatherEntityList=AppGlobal.getInstance().getWeatherEntityList();

        if(weatherEntityList!=null && weatherEntityList.size()>0){

            ValsMin.clear();
            ValsMax.clear();

            img01.setBackground(getImage(weatherEntityList.get(0).getImg1().trim()));
            img02.setBackground(getImage(weatherEntityList.get(1).getImg1().trim()));
            img03.setBackground(getImage(weatherEntityList.get(2).getImg1().trim()));
            img04.setBackground(getImage(weatherEntityList.get(3).getImg1().trim()));
            img05.setBackground(getImage(weatherEntityList.get(4).getImg1().trim()));
            img06.setBackground(getImage(weatherEntityList.get(5).getImg1().trim()));
            img07.setBackground(getImage(weatherEntityList.get(6).getImg1().trim()));

            for(int i = 0; i < weatherEntityList.size(); i++){

//                String imgNo=weatherEntityList.get(i).getImg1();

                String maxTemp=weatherEntityList.get(i).getTem1();
                String mMaxTemp=maxTemp.substring(0,maxTemp.indexOf("°C"));
                if(mMaxTemp!=null && !mMaxTemp.equals("")){
                    ValsMax.add(Float.valueOf(mMaxTemp));
                }else{
                    ValsMax.add(0.0f);
                    mMaxTemp=0+"";
                }

                String minTemp=weatherEntityList.get(i).getTem2();
                String mMinTemp=minTemp.substring(0,minTemp.indexOf("°C"));
                if(mMinTemp!=null && !mMinTemp.equals("")){
                    ValsMin.add(Float.valueOf(mMinTemp));
                }else{
                    ValsMin.add(0.0f);
                    mMinTemp=0+"";
                }

                xVals.add(weatherEntityList.get(i).getSj());
                yValsMin.add(new Entry(Float.valueOf(mMinTemp), i));
                yValsMax.add(new Entry(Float.valueOf(mMaxTemp), i));
            }

            float maxTemp=0.0f;
            float minTemp=100f;

            for(int i=0;i<ValsMax.size();i++){
                if(ValsMax.get(i)>maxTemp){
                    maxTemp=ValsMax.get(i);
                }
            }

            for(int i=0;i<ValsMin.size();i++){
                if(ValsMin.get(i)<minTemp){
                    minTemp=ValsMin.get(i);
                }
            }

            leftAxis.setAxisMaxValue(maxTemp+5);
            leftAxis.setAxisMinValue(minTemp-1);

            rightAxis.setAxisMaxValue(maxTemp+5);
            rightAxis.setAxisMinValue(minTemp-1);
        }

        LineDataSet set1 = new LineDataSet(yValsMin, "");

        set1.setColor(getResources().getColor(R.color.wasu01));
        set1.setValueFormatter(new ValueFormat());
        set1.setCircleColor(getResources().getColor(R.color.wasu01));
        set1.setLineWidth(3f);
        set1.setCircleSize(5f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(12f);
        set1.setValueTextColor(getResources().getColor(R.color.wasu01));
        set1.setFillAlpha(65);
        set1.setFillColor(getResources().getColor(R.color.wasu01));

        LineDataSet set2 = new LineDataSet(yValsMax, "");

        set2.setColor(getResources().getColor(R.color.wasu01));
        set2.setValueFormatter(new ValueFormat());
        set2.setCircleColor(getResources().getColor(R.color.wasu01));
        set2.setLineWidth(3f);
        set2.setCircleSize(5f);
        set2.setDrawCircleHole(false);
        set2.setValueTextSize(12f);
        set2.setValueTextColor(getResources().getColor(R.color.wasu01));
        set2.setFillAlpha(65);
        set2.setFillColor(getResources().getColor(R.color.wasu01));

        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);
        LineData data = new LineData(xVals, dataSets);

        data.setValueTextColor(getResources().getColor(R.color.wasu01));
        //设置温度数据字体大小
        data.setValueTextSize(15f);
        mChart.setData(data);
        mChart.invalidate();
    }

    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public Drawable getImage(String id){

        Drawable drawable=null;

        if(id.equals("d00")){
            drawable= getResources().getDrawable(R.drawable.d00);

        }else if(id.equals("d01")){
            drawable= getResources().getDrawable(R.drawable.d01);

        }else if(id.equals("d02")){
            drawable= getResources().getDrawable(R.drawable.d02);

        }else if(id.equals("d03")){
            drawable= getResources().getDrawable(R.drawable.d03);

        }else if(id.equals("d04")){
            drawable= getResources().getDrawable(R.drawable.d04);

        }else if(id.equals("d05")){
            drawable= getResources().getDrawable(R.drawable.d05);

        }else if(id.equals("d06")){
            drawable= getResources().getDrawable(R.drawable.d06);

        }else if(id.equals("d07")){
            drawable= getResources().getDrawable(R.drawable.d07);

        }else if(id.equals("d08")){
            drawable= getResources().getDrawable(R.drawable.d08);

        }else if(id.equals("d09")){
            drawable= getResources().getDrawable(R.drawable.d09);

        }else if(id.equals("d10")){
            drawable= getResources().getDrawable(R.drawable.d10);

        }else if(id.equals("d11")){
            drawable= getResources().getDrawable(R.drawable.d11);

        }else if(id.equals("d12")){
            drawable= getResources().getDrawable(R.drawable.d12);

        }else if(id.equals("d13")){
            drawable= getResources().getDrawable(R.drawable.d13);

        }else if(id.equals("d14")){
            drawable= getResources().getDrawable(R.drawable.d14);

        }else if(id.equals("d15")){
            drawable= getResources().getDrawable(R.drawable.d15);

        }else if(id.equals("d16")){
            drawable= getResources().getDrawable(R.drawable.d16);

        }else if(id.equals("d17")){
            drawable= getResources().getDrawable(R.drawable.d17);

        }else if(id.equals("d18")){
            drawable= getResources().getDrawable(R.drawable.d18);

        }else if(id.equals("d19")){
            drawable= getResources().getDrawable(R.drawable.d19);

        }else if(id.equals("d20")){
            drawable= getResources().getDrawable(R.drawable.d20);

        }else if(id.equals("d21")){
            drawable= getResources().getDrawable(R.drawable.d21);

        }else if(id.equals("d22")){
            drawable= getResources().getDrawable(R.drawable.d22);

        }else if(id.equals("d23")){
            drawable= getResources().getDrawable(R.drawable.d23);

        }else if(id.equals("d24")){
            drawable= getResources().getDrawable(R.drawable.d24);

        }else if(id.equals("d25")){
            drawable= getResources().getDrawable(R.drawable.d25);

        }else if(id.equals("d26")){
            drawable= getResources().getDrawable(R.drawable.d26);

        }else if(id.equals("d27")){
            drawable= getResources().getDrawable(R.drawable.d27);

        }else if(id.equals("d28")){
            drawable= getResources().getDrawable(R.drawable.d28);

        }else if(id.equals("d29")){
            drawable= getResources().getDrawable(R.drawable.d29);

        }else if(id.equals("d30")){
            drawable= getResources().getDrawable(R.drawable.d30);

        }else if(id.equals("d31")){
            drawable= getResources().getDrawable(R.drawable.d31);

        }else{
            drawable= getResources().getDrawable(R.drawable.d02);
        }

        return drawable;
    }
}
