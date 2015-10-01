package com.lbs.weatherdemo.activity.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lbs.weatherdemo.R;
import com.lbs.weatherdemo.activity.MainActivity;
import com.lbs.weatherdemo.activity.adapter.GaosuRecycleViewAdapter;
import com.lbs.weatherdemo.activity.entity.HighWayEntity;
import com.lbs.weatherdemo.activity.util.AppGlobal;

import java.util.List;

public class GaosuFragmentNew extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private List<HighWayEntity> highWayEntityList;

    private MainActivity context;

    private GaosuRecycleViewAdapter gaosuRecycleViewAdapter=null;
    private RecyclerView mRecyclerView=null;
    private LinearLayoutManager linearLayoutManager=null;

    public static GaosuFragmentNew newInstance(String param1, String param2) {
        GaosuFragmentNew fragment = new GaosuFragmentNew();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public GaosuFragmentNew() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        /**
         * 第一步
         */
        super.onAttach(activity);
        context= (MainActivity) activity;
        highWayEntityList= AppGlobal.getInstance().getHighWayEntityList();
        linearLayoutManager = new LinearLayoutManager(activity);
        gaosuRecycleViewAdapter=new GaosuRecycleViewAdapter(highWayEntityList,activity);
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

        View view=inflater.inflate(R.layout.fragment_gaosu_new_01, container, false);
        mRecyclerView= (RecyclerView) view.findViewById(R.id.id_recyclerview_horizontal);
        mRecyclerView.setNextFocusUpId(R.id.gaosuqixiang_textview);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(gaosuRecycleViewAdapter);
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
