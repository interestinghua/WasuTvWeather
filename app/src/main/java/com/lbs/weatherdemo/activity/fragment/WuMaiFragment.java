package com.lbs.weatherdemo.activity.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.lbs.weatherdemo.R;
import com.lbs.weatherdemo.activity.adapter.WumaiAdapter;
import com.lbs.weatherdemo.activity.entity.AQIEntity;
import com.lbs.weatherdemo.activity.util.AppGlobal;

import java.util.ArrayList;
import java.util.List;

public class WuMaiFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private GridView wumaiGridview;
    private WumaiAdapter wumaiAdapter;

    private List<AQIEntity> aqiEntityList;
    private List<AQIEntity> aqiCurrentEntityList;

    private int LIEWIDTH;//每列宽度
    private int HorizontalSpace;

    public static WuMaiFragment newInstance(String param1, String param2) {
        WuMaiFragment fragment = new WuMaiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public WuMaiFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Activity activity) {
        /**
         * 第一步
         */
        super.onAttach(activity);
        aqiCurrentEntityList=new ArrayList<>();
        aqiEntityList= AppGlobal.getInstance().getAqiEntityList();

        String cityCode=AppGlobal.getInstance().getCitycode().trim();
        String cityCodeSub=null;
        if(cityCode!=null){
            cityCodeSub=cityCode.substring(0,7);
        }
        if(aqiEntityList!=null && aqiEntityList.size()>0){
            aqiCurrentEntityList.clear();
            for(AQIEntity aqiEntity:aqiEntityList){
                if(aqiEntity.getCode().trim().startsWith(cityCodeSub)
                   && !aqiEntity.getCode().trim().endsWith("A")){
                    aqiCurrentEntityList.add(aqiEntity);
                }
            }
        }

        wumaiAdapter=new WumaiAdapter(aqiCurrentEntityList,activity);
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
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_wu_mai, container, false);
        wumaiGridview=(GridView)view.findViewById(R.id.wumaiGridview);
        wumaiGridview.setNextFocusUpId(R.id.wumaiditu_textview);

        int screenWidth=AppGlobal.getInstance().getWidth();
        LIEWIDTH = 565;
        HorizontalSpace=40;

        wumaiGridview.setNumColumns(2);
        int paddingleftOrRight=(screenWidth-LIEWIDTH*2-HorizontalSpace)/2;
        //左上右下
        wumaiGridview.setPadding(paddingleftOrRight,10,paddingleftOrRight,10);
        wumaiGridview.setHorizontalSpacing(HorizontalSpace);
        wumaiGridview.setVerticalSpacing(20);

        wumaiGridview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    wumaiGridview.setNextFocusUpId(R.id.wumaiditu_textview);
                    wumaiGridview.setNextFocusRightId(R.id.taifengzhuanti_textview);
                    wumaiGridview.setNextFocusLeftId(R.id.gaosuqixiang_textview);
                }
            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
        wumaiGridview.setLayoutParams(params);

        wumaiGridview.setColumnWidth(LIEWIDTH);
//        wumaiGridview
        wumaiGridview.setStretchMode(GridView.NO_STRETCH);

        wumaiGridview.setAdapter(wumaiAdapter);
        return view;
    }

}
