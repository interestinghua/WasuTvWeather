package com.lbs.weatherdemo.activity.fragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.lbs.weatherdemo.R;
import com.lbs.weatherdemo.activity.MainActivity;
import com.lbs.weatherdemo.activity.util.AppGlobal;

import java.util.ArrayList;
import java.util.List;

public class SelectCityNewFragment extends DialogFragment{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Spinner spinnerTopCityZone;
    private Spinner spinnerTwoCityZone;
    private ArrayAdapter<CharSequence> topCityArrayAdapter=null;
    private ArrayAdapter<CharSequence> secondCityArrayAdapter=null;

    private TextView citytext01;
    private TextView citytext02;
    private TextView citytext03;
    private TextView citytext04;
    private TextView citytext05;
    private TextView citytext06;
    private TextView citytext07;
    private TextView citytext08;
    private TextView citytext09;
    private TextView citytext10;
    private TextView citytext11;

    private LinearLayout cityrightlayout=null;
    private LinearLayout containerlayout=null;

    private List<TextView> textViewList=null;
    private List<TextView> textViewRightList=null;


    private OnFragmentInteractionListener mListener;

    private MainActivity context;

    public static SelectCityNewFragment newInstance(String param1, String param2) {
        SelectCityNewFragment fragment = new SelectCityNewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SelectCityNewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context= (MainActivity) activity;

        textViewList=new ArrayList<>();
        textViewRightList=new ArrayList<>();
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }

        topCityArrayAdapter = ArrayAdapter.createFromResource(activity, R.array.cityArray, R.layout.spinner_style);
        topCityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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
        View view=inflater.inflate(R.layout.fragment_new_select_city, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));

        cityrightlayout= (LinearLayout) view.findViewById(R.id.cityrightlayout);
        containerlayout= (LinearLayout) view.findViewById(R.id.containerlayout);

        citytext01= (TextView) view.findViewById(R.id.citytext01);
        citytext01.setFocusable(true);
        citytext01.requestFocus();

        citytext02= (TextView) view.findViewById(R.id.citytext02);
        citytext03= (TextView) view.findViewById(R.id.citytext03);
        citytext04= (TextView) view.findViewById(R.id.citytext04);
        citytext05= (TextView) view.findViewById(R.id.citytext05);
        citytext06= (TextView) view.findViewById(R.id.citytext06);
        citytext07= (TextView) view.findViewById(R.id.citytext07);
        citytext08= (TextView) view.findViewById(R.id.citytext08);
        citytext09= (TextView) view.findViewById(R.id.citytext09);
        citytext10= (TextView) view.findViewById(R.id.citytext10);
        citytext11= (TextView) view.findViewById(R.id.citytext11);

        textViewList.add(citytext01);
        textViewList.add(citytext02);
        textViewList.add(citytext03);
        textViewList.add(citytext04);
        textViewList.add(citytext05);
        textViewList.add(citytext06);
        textViewList.add(citytext07);
        textViewList.add(citytext08);
        textViewList.add(citytext09);
        textViewList.add(citytext10);
        textViewList.add(citytext11);

        cityrightlayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (cityrightlayout.getChildAt(0) != null) {
                        cityrightlayout.getChildAt(0).requestFocus();
                    }
                }
            }
        });

        citytext01.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    List<String> hangzhou_List= AppGlobal.getInstance().getCityMap().get(getResources().getString(R.string.str14));

                    if(hangzhou_List!=null && hangzhou_List.size()>0) {

                        cityrightlayout.removeAllViews();

                        int citySize = AppGlobal.getInstance().getCityMap().get(getResources().getString(R.string.str14)).size();

                        for (int i = 0; i < citySize; i++) {

                            TextView cityTextView = new TextView(context);
                            cityTextView.setId(i);
                            cityTextView.setFocusable(true);
                            cityTextView.setClickable(true);
                            cityTextView.setText(hangzhou_List.get(i));
                            cityTextView.setTextSize(25);
                            cityTextView.setTextColor(getResources().getColor(R.color.wasuselectziti02));
                            cityTextView.setGravity(Gravity.CENTER);
                            cityTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                                                                       LinearLayout.LayoutParams.WRAP_CONTENT));
                            cityrightlayout.addView(cityTextView, i);
                        }
                    }

                }

            }
        });

        citytext01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> hangzhou_List=AppGlobal.getInstance().getCityMap().get(getResources().getString(R.string.str14));

                if(hangzhou_List!=null && hangzhou_List.size()>0){

                    cityrightlayout.removeAllViews();

                    int citySize= AppGlobal.getInstance().getCityMap().get(getResources().getString(R.string.str14)).size();

                    for(int i=0;i<citySize;i++){

                        TextView cityTextView = new TextView(context);
                        cityTextView.setId(i);
                        cityTextView.setFocusable(true);
                        cityTextView.setClickable(true);
                        cityTextView.setText(hangzhou_List.get(i));
                        cityTextView.setTextSize(25);
                        cityTextView.setTextColor(getResources().getColor(R.color.wasuselectziti02));
                        cityTextView.setGravity(Gravity.CENTER);
                        cityTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                                                                   LinearLayout.LayoutParams.WRAP_CONTENT));
                        cityrightlayout.addView(cityTextView, i);
                    }


                    cityrightlayout.invalidate();

                    cityrightlayout.getChildAt(0).requestFocus();

                    int childcount= cityrightlayout.getChildCount();

                    for(int i=0;i<childcount;i++){

                        final TextView tv= (TextView) cityrightlayout.getChildAt(i);

                        tv.setBackground(getResources().getDrawable(R.drawable.righttxtview));
                        tv.setNextFocusLeftId(R.id.citytext01);

                        if(childcount-1==i){
                            tv.setNextFocusDownId(R.id.citytext01);
                        }

                        tv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View v, boolean hasFocus) {
                                if(hasFocus){
                                    tv.setTextColor(getResources().getColor(R.color.selectbkg));
                                }else{
                                    tv.setTextColor(getResources().getColor(R.color.wasuselectziti02));
                                }
                            }
                        });
                    }
                }

            }
        });

//        citytext02.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                List<String> ningbo_List=AppGlobal.getInstance().getCityMap().get(getResources().getString(R.string.str15));
//
//                if(ningbo_List!=null && ningbo_List.size()>0){
//                    cityrightlayout.removeAllViews();
//                    int citySize= AppGlobal.getInstance().getCityMap().get(getResources().getString(R.string.str15)).size();
//
//                    for(TextView tv:textViewList){
//                        if(tv.equals(citytext02)){
//                            tv.setBackgroundColor(getResources().getColor(R.color.topbkg));
//                        }else{
//                            tv.setBackgroundColor(getResources().getColor(R.color.selectbkg));
//                        }
//                    }
//
//                    for(int i=0;i<citySize;i++){
//
//                        final TextView cityTextView = new TextView(context);
//                        cityTextView.setFocusable(true);
//                        cityTextView.setClickable(true);
//                        cityTextView.setText(ningbo_List.get(i));
//                        cityTextView.setTextSize(24);
//                        cityTextView.setTextColor(getResources().getColor(R.color.wasuselectziti02));
//                        cityTextView.setGravity(Gravity.CENTER);
//                        cityTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                                LinearLayout.LayoutParams.WRAP_CONTENT));
//                        cityTextView.setBackgroundColor(getResources().getColor(R.color.topbkg));
//                        cityTextView.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                cityTextView.setTextColor(getResources().getColor(R.color.selectbkg));
////                                dismiss();
//                            }
//                        });
//                        cityrightlayout.addView(cityTextView, i);
//                    }
//                }
//            }
//        });



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String topCity, String secondCity);
    }


//        @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View view = inflater.inflate(R.layout.fragment_select_city, null);
//
//        //初始化下拉框
//        spinnerTopCityZone= (Spinner) view.findViewById(R.id.spinnerTopCityZone);
//        spinnerTopCityZone.setFocusable(true);
//        spinnerTopCityZone.setFocusableInTouchMode(true);
//        spinnerTopCityZone.setAdapter(topCityArrayAdapter);
//
//        spinnerTopCityZone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if(spinnerTopCityZone.getSelectedItem().toString().equals(getResources().getString(R.string.str14))){
//                    secondCityArrayAdapter = ArrayAdapter.createFromResource(context, R.array.cityZoneHZArray, R.layout.spinner_style);
//                    secondCityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinnerTwoCityZone.setAdapter(secondCityArrayAdapter);
//                }
//                if(spinnerTopCityZone.getSelectedItem().toString().equals(getResources().getString(R.string.str15))){
//                    secondCityArrayAdapter = ArrayAdapter.createFromResource(context, R.array.cityZoneNBArray, R.layout.spinner_style);
//                    secondCityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinnerTwoCityZone.setAdapter(secondCityArrayAdapter);
//                }
//                if(spinnerTopCityZone.getSelectedItem().toString().equals(getResources().getString(R.string.str16))){
//                    secondCityArrayAdapter = ArrayAdapter.createFromResource(context, R.array.cityZoneWZArray, R.layout.spinner_style);
//                    secondCityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinnerTwoCityZone.setAdapter(secondCityArrayAdapter);
//                }
//                if(spinnerTopCityZone.getSelectedItem().toString().equals(getResources().getString(R.string.str17))){
//                    secondCityArrayAdapter = ArrayAdapter.createFromResource(context, R.array.cityZoneSXArray, R.layout.spinner_style);
//                    secondCityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinnerTwoCityZone.setAdapter(secondCityArrayAdapter);
//                }
//                if(spinnerTopCityZone.getSelectedItem().toString().equals(getResources().getString(R.string.str18))){
//                    secondCityArrayAdapter = ArrayAdapter.createFromResource(context, R.array.cityZoneHuZhouArray, R.layout.spinner_style);
//                    secondCityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinnerTwoCityZone.setAdapter(secondCityArrayAdapter);
//                }
//                if(spinnerTopCityZone.getSelectedItem().toString().equals(getResources().getString(R.string.str19))){
//                    secondCityArrayAdapter = ArrayAdapter.createFromResource(context, R.array.cityZoneJXArray, R.layout.spinner_style);
//                    secondCityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinnerTwoCityZone.setAdapter(secondCityArrayAdapter);
//                }
//                if(spinnerTopCityZone.getSelectedItem().toString().equals(getResources().getString(R.string.str20))){
//                    secondCityArrayAdapter = ArrayAdapter.createFromResource(context, R.array.cityZoneJHArray, R.layout.spinner_style);
//                    secondCityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinnerTwoCityZone.setAdapter(secondCityArrayAdapter);
//                }
//                if(spinnerTopCityZone.getSelectedItem().toString().equals(getResources().getString(R.string.str21))){
//                    secondCityArrayAdapter = ArrayAdapter.createFromResource(context, R.array.cityZoneJZArray, R.layout.spinner_style);
//                    secondCityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinnerTwoCityZone.setAdapter(secondCityArrayAdapter);
//                }
//                if(spinnerTopCityZone.getSelectedItem().toString().equals(getResources().getString(R.string.str22))){
//                    secondCityArrayAdapter = ArrayAdapter.createFromResource(context, R.array.cityZoneZSArray, R.layout.spinner_style);
//                    secondCityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinnerTwoCityZone.setAdapter(secondCityArrayAdapter);
//                }
//                if(spinnerTopCityZone.getSelectedItem().toString().equals(getResources().getString(R.string.str23))){
//                    secondCityArrayAdapter = ArrayAdapter.createFromResource(context, R.array.cityZoneTZArray, R.layout.spinner_style);
//                    secondCityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinnerTwoCityZone.setAdapter(secondCityArrayAdapter);
//                }
//                if(spinnerTopCityZone.getSelectedItem().toString().equals(getResources().getString(R.string.str24))){
//                    secondCityArrayAdapter = ArrayAdapter.createFromResource(context, R.array.cityZoneLSArray, R.layout.spinner_style);
//                    secondCityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinnerTwoCityZone.setAdapter(secondCityArrayAdapter);
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        spinnerTwoCityZone= (Spinner) view.findViewById(R.id.spinnerTwoCityZone);
//        spinnerTwoCityZone.setFocusable(true);
//        spinnerTwoCityZone.setFocusableInTouchMode(true);
//        spinnerTwoCityZone.setAdapter(secondCityArrayAdapter);
//
//        builder.setView(view);
//        builder.setPositiveButton("确定",new DialogInterface.OnClickListener(){
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                mListener.onFragmentInteraction(spinnerTopCityZone.getSelectedItem().toString(),
//                                                spinnerTwoCityZone.getSelectedItem().toString());
//            }
//        });
//        builder.setNegativeButton("返回",new DialogInterface.OnClickListener(){
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//
//
//        return builder.create();
//    }

}
