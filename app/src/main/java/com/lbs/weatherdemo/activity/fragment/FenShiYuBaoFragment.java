package com.lbs.weatherdemo.activity.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.lbs.weatherdemo.R;
import com.lbs.weatherdemo.activity.entity.YBFSEntity;
import com.lbs.weatherdemo.activity.util.AppGlobal;

import java.util.ArrayList;
import java.util.List;

public class FenShiYuBaoFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private YAxis leftAxis=null;
    private YAxis rightAxis=null;
    private LineChart mChart=null;
    private List<YBFSEntity> ybfsEntityList=null;


    public static FenShiYuBaoFragment newInstance(String param1, String param2) {
        FenShiYuBaoFragment fragment = new FenShiYuBaoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FenShiYuBaoFragment() {
        // Required empty public constructor
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

        View view=inflater.inflate(R.layout.fragment_fen_shi_yu_bao, container, false);

        mChart = (LineChart) view.findViewById(R.id.lineFSYBchart);
        mChart.setDrawGridBackground(false);
        mChart.setBackground(null);
        mChart.setDescription("");
        mChart.setNoDataTextDescription("没有可以展示的数据");
        mChart.setHighlightEnabled(true);
        mChart.animateX(1000, Easing.EasingOption.EaseInOutQuart);
        mChart.animateY(1500, Easing.EasingOption.EaseInCubic);

        //左上右下 160 160
        mChart.setViewPortOffsets(80f, 10f, 80f, 60f);

        Legend mLegend = mChart.getLegend();
        mLegend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        mLegend.setForm(Legend.LegendForm.LINE);
        mLegend.setFormSize(0f);

        leftAxis = mChart.getAxisLeft();
        leftAxis.setStartAtZero(false);
        leftAxis.setDrawLabels(false);
        leftAxis.enableGridDashedLine(10f, 0f, 0f);
        leftAxis.setTextSize(5f);
        leftAxis.setTextColor(getResources().getColor(R.color.wasu02));
        leftAxis.setAxisLineColor(getResources().getColor(R.color.wasu03));

        rightAxis = mChart.getAxisRight();
        rightAxis.setStartAtZero(false);
        rightAxis.setDrawLabels(false);
        rightAxis.enableGridDashedLine(10f, 0f, 0f);
        rightAxis.setTextSize(5f);
        rightAxis.setTextColor(getResources().getColor(R.color.wasu02));
        rightAxis.setAxisLineColor(getResources().getColor(R.color.wasu03));

        XAxis xAxis = mChart.getXAxis();
        xAxis.setAdjustXLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(20f);
        xAxis.setTextColor(getResources().getColor(R.color.wasu01));
        xAxis.setSpaceBetweenLabels(1);

        setData();

        return view;
    }

    private void setData() {

        ArrayList<String> xVals = new ArrayList<>();
        ArrayList<Entry> yValsMin = new ArrayList<>();
        ArrayList<Entry> yValsMax = new ArrayList<>();

        ArrayList<Float> ValsMin = new ArrayList<>();
        ArrayList<Float> ValsMax = new ArrayList<>();

        ybfsEntityList= AppGlobal.getInstance().getYbfsEntityList();

        if(ybfsEntityList!=null && ybfsEntityList.size()>0){

            ValsMin.clear();
            ValsMax.clear();

            for(int i = 0; i < ybfsEntityList.size(); i++){

                xVals.add(ybfsEntityList.get(i).getTime().trim().replace("-","/"));

                String temper=ybfsEntityList.get(i).getTemp().trim();

                String maxTemp=temper.split("/")[0];
                int index1=maxTemp.indexOf("℃");
                String mMaxTemp=maxTemp.substring(0,index1);
                if(mMaxTemp!=null && !mMaxTemp.equals("")){
                    ValsMax.add(Float.valueOf(mMaxTemp));
                }else{
                    ValsMax.add(0.0f);
                    mMaxTemp=0+"";
                }

                String minTemp=temper.split("/")[1];
                int index2=minTemp.indexOf("℃");
                String mMinTemp=minTemp.substring(0,index2);
                if(mMinTemp!=null && !mMinTemp.equals("")){
                    ValsMin.add(Float.valueOf(mMinTemp));
                }else{
                    ValsMin.add(0.0f);
                    mMinTemp=0+"";
                }

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
        set1.setValueTextSize(9f);
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
        //设置温度字体大小
        data.setValueTextSize(13f);
        mChart.setData(data);
        mChart.invalidate();
    }



//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        try {
//            mListener = (OnFenShiYuBaoFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFenShiYuBaoFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }


//    public interface OnFenShiYuBaoFragmentInteractionListener {
//        public void onFragmentInteraction(Uri uri);
//    }

}
