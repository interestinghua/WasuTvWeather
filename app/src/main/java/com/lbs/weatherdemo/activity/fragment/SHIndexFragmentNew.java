package com.lbs.weatherdemo.activity.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.lbs.weatherdemo.R;
import com.lbs.weatherdemo.activity.MainActivity;
import com.lbs.weatherdemo.activity.adapter.SHIndexAdapter;
import com.lbs.weatherdemo.activity.adapter.SHIndexAdapterNew;
import com.lbs.weatherdemo.activity.entity.IndexEntity;
import com.lbs.weatherdemo.activity.util.AppGlobal;

import java.util.List;

public class SHIndexFragmentNew extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private List<IndexEntity> indexEntityList=null;
    private IndexEntity[] indexEntities=new IndexEntity[9];

    private MainActivity context=null;

    private RecyclerView mRecyclerView;
    private SHIndexAdapter mAdapter;

    private SHIndexAdapterNew shIndexAdapterNew;

    private int LIEWIDTH;//每列宽度
    private LinearLayout linearLayout1;
    private GridView shindexGridview;
    private HorizontalScrollView horizontalScrollView=null;
    private LinearLayout.LayoutParams params;

    public static SHIndexFragmentNew newInstance(String param1, String param2) {
        SHIndexFragmentNew fragment = new SHIndexFragmentNew();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context= (MainActivity) activity;

        indexEntityList= AppGlobal.getInstance().getIndexEntityList();
        shIndexAdapterNew=new SHIndexAdapterNew(indexEntities,activity);
    }

    public SHIndexFragmentNew() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_shindex_new, container, false);

        if(indexEntityList!=null && indexEntityList.size()>0) {

            for (IndexEntity ie : indexEntityList) {

                if (ie.getName().equals("穿衣指数")) {
                    indexEntities[0] = ie;
                }
                if (ie.getName().equals("感冒指数")) {
                    indexEntities[1] = ie;
                }
                if (ie.getName().equals("化妆指数")) {
                    indexEntities[2] = ie;
                }
                if (ie.getName().equals("太阳镜指数")) {
                    indexEntities[3] = ie;
                }
                if (ie.getName().equals("紫外线指数")) {
                    indexEntities[4] = ie;
                }
                if (ie.getName().equals("舒适度指数")) {
                    indexEntities[5] = ie;
                }
                if (ie.getName().contains("旅游指数")) {
                    indexEntities[6] = ie;
                }
                if (ie.getName().equals("运动指数")) {
                    indexEntities[7] = ie;
                }
                if (ie.getName().equals("洗车指数")) {
                    indexEntities[8] = ie;
                }
            }
        }

        LIEWIDTH = 405;

        linearLayout1= (LinearLayout) view.findViewById(R.id.linearLayout1);

        horizontalScrollView= (HorizontalScrollView) view.findViewById(R.id.scrollView);
        horizontalScrollView.setHorizontalScrollBarEnabled(false);// 隐藏滚动条
        horizontalScrollView.setSmoothScrollingEnabled(true);
        horizontalScrollView.setHorizontalFadingEdgeEnabled(true);
        horizontalScrollView.setHorizontalScrollBarEnabled(true);

        int screenWidth=AppGlobal.getInstance().getWidth();

        shindexGridview=new GridView(context);

        LinearLayout.LayoutParams gridLayoutparam=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                                                                LinearLayout.LayoutParams.MATCH_PARENT);
        shindexGridview.setLayoutParams(gridLayoutparam);
        //左上右下
        shindexGridview.setPadding(10,0,10,0);

        shindexGridview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                horizontalScrollView.setScrollX(position * LIEWIDTH);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        shindexGridview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    shindexGridview.setNextFocusUpId(R.id.shenhuozhishu_textview);
                    shindexGridview.setNextFocusRightId(R.id.gaosuqixiang_textview);
                }
            }
        });

        shindexGridview.setVerticalSpacing(0);
        shindexGridview.setNumColumns(indexEntityList.size());
        shindexGridview.setHorizontalSpacing(20);
        params = new LinearLayout.LayoutParams(shIndexAdapterNew.getCount() * LIEWIDTH+20*9,280);
        shindexGridview.setLayoutParams(params);

        shindexGridview.setColumnWidth(LIEWIDTH);
        shindexGridview.setStretchMode(GridView.NO_STRETCH);

        shindexGridview.setAdapter(shIndexAdapterNew);
        linearLayout1.addView(shindexGridview);
        return view;
    }
}
