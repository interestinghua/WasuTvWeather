package com.lbs.weatherdemo.activity.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.lbs.weatherdemo.R;
import com.lbs.weatherdemo.activity.MainActivity;
import com.lbs.weatherdemo.activity.adapter.GaosuAdapter;
import com.lbs.weatherdemo.activity.entity.HighWayEntity;
import com.lbs.weatherdemo.activity.util.AppGlobal;

import java.util.List;

public class GaosuFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private GridView highwayGridview;
    private HorizontalScrollView horizontalScrollView;

    private GaosuAdapter gaosuAdapter;
    private List<HighWayEntity> highWayEntityList;
    private LayoutParams params;
    private LinearLayout linearLayout1;

    private int LIEWIDTH;//每列宽度
    private MainActivity context;
    private  View convertView;

    public static GaosuFragment newInstance(String param1, String param2) {
        GaosuFragment fragment = new GaosuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public GaosuFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        /**
         * 第一步
         */
        super.onAttach(activity);
        context= (MainActivity) activity;
        highWayEntityList= AppGlobal.getInstance().getHighWayEntityList();
        gaosuAdapter=new GaosuAdapter(highWayEntityList,activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        /**
         * 第二步
         */
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /**
         * 第三步
         */
        // Inflate the layout for this fragment
        LIEWIDTH = 465;
        View view=inflater.inflate(R.layout.fragment_gaosu, container, false);

        linearLayout1= (LinearLayout) view.findViewById(R.id.linearLayout1);

        horizontalScrollView= (HorizontalScrollView) view.findViewById(R.id.scrollView);
        horizontalScrollView.setHorizontalScrollBarEnabled(false);// 隐藏滚动条
        horizontalScrollView.setSmoothScrollingEnabled(true);
        horizontalScrollView.setHorizontalFadingEdgeEnabled(true);
        horizontalScrollView.setHorizontalScrollBarEnabled(true);

        int screenWidth=AppGlobal.getInstance().getWidth();

        highwayGridview=new GridView(context);

        LayoutParams gridLayoutparam=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        highwayGridview.setLayoutParams(gridLayoutparam);
        //左上右下
        highwayGridview.setPadding(10,0,10,0);

        highwayGridview.setSelector(getResources().getDrawable(R.drawable.gaosu_03));

        highwayGridview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                horizontalScrollView.setScrollX(position * LIEWIDTH);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        highwayGridview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    highwayGridview.setNextFocusUpId(R.id.gaosuqixiang_textview);
                    highwayGridview.setNextFocusRightId(R.id.wumaiditu_textview);
                }
            }
        });

        highwayGridview.setVerticalSpacing(0);
        highwayGridview.setNumColumns(highWayEntityList.size());
        highwayGridview.setHorizontalSpacing(20);
        params = new LayoutParams(gaosuAdapter.getCount() * LIEWIDTH+20*14,280);
        highwayGridview.setLayoutParams(params);

        highwayGridview.setColumnWidth(LIEWIDTH);
        highwayGridview.setStretchMode(GridView.NO_STRETCH);

        highwayGridview.setAdapter(gaosuAdapter);
        linearLayout1.addView(highwayGridview);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        /**
         * 第四步
         */
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        /**
         * 第五步
         */
        super.onStart();
    }

    @Override
    public void onResume() {
        /**
         * 第六步
         */
        super.onResume();
    }

    @Override
    public void onPause() {
        /**
         * 第七步
         */
        super.onPause();
    }

    @Override
    public void onStop() {
        /**
         * 第八步
         */
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        /**
         * 第九步
         */
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        /**
         * 第十步
         */
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        /**
         * 第十一步
         */
        super.onDetach();
    }
}
