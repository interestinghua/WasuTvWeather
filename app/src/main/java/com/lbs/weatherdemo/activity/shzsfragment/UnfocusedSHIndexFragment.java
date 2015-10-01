package com.lbs.weatherdemo.activity.shzsfragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lbs.weatherdemo.R;

public class UnfocusedSHIndexFragment extends Fragment {

    private static final String ARG_PARAM1 = "type";
    private String type=null;
    private int state=0;

    public static UnfocusedSHIndexFragment newInstance(String type) {
        UnfocusedSHIndexFragment fragment = new UnfocusedSHIndexFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, type);
        fragment.setArguments(args);
        return fragment;
    }

    public UnfocusedSHIndexFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_unfocused_shindex, container, false);
        return view;
    }


}
