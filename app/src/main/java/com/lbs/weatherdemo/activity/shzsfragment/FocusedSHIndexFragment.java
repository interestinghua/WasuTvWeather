package com.lbs.weatherdemo.activity.shzsfragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lbs.weatherdemo.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FocusedSHIndexFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FocusedSHIndexFragment extends Fragment {

    private static final String ARG_PARAM2 = "type";

    private String type=null;
    private int state=0;

    public static FocusedSHIndexFragment newInstance(String type) {
        FocusedSHIndexFragment fragment = new FocusedSHIndexFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM2, type);
        fragment.setArguments(args);
        return fragment;
    }

    public FocusedSHIndexFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_focused_shindex, container, false);
        return view;
    }


}
