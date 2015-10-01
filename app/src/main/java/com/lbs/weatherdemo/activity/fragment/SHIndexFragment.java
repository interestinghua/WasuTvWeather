package com.lbs.weatherdemo.activity.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lbs.weatherdemo.R;
import com.lbs.weatherdemo.activity.MainActivity;
import com.lbs.weatherdemo.activity.adapter.SHIndexAdapter;
import com.lbs.weatherdemo.activity.adapter.SHIndexFocusedAdapter;
import com.lbs.weatherdemo.activity.entity.IndexEntity;
import com.lbs.weatherdemo.activity.util.AppGlobal;

import java.util.List;

public class SHIndexFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

//    private TextView chuanyizhishu_txtview=null;
//    private TextView ganmaozhishu_txtview=null;
//    private TextView huazhuangzhishu_txtview=null;
//    private TextView tyjzhishu_txtview=null;
//    private TextView ziwaixianzhishu_txtview=null;
//    private TextView shushiduzhishu_txtview=null;
//    private TextView lvyouzhishu_txtview=null;
//    private TextView yundongzhishu_txtview=null;
//    private TextView xichezhishu_txtview=null;

    private LinearLayout chuanyilayout=null;
    private LinearLayout ganmaolayout=null;
    private LinearLayout huazhuanglayout=null;
    private LinearLayout taiyangjinglayout=null;
    private LinearLayout ziwaixianlayout=null;
    private LinearLayout shushidulayout=null;
    private LinearLayout lvyoulayout=null;
    private LinearLayout yundonglayout=null;
    private LinearLayout xichelayout=null;

    private ImageView zhishuimg;
    private TextView zhishuTxtview;
    private TextView zhishuContentTxtview;
    LinearLayout.LayoutParams gridLayoutparam;

    private HorizontalScrollView horizontalScrollView=null;

    private LinearLayout shindex_container_layout;
    private List<IndexEntity> indexEntityList=null;
    private IndexEntity[] indexEntities=new IndexEntity[9];
    private  View convertView;
    private  View convertViewFocused;
    private MainActivity context=null;

    public static final int SHOW=0;
    public static final int HIDE=1;

    FragmentManager fragmentManager = null;
    FragmentTransaction fragmentTransaction = null;

    FragmentTransaction ftaFocus = null;
    FragmentTransaction ftaunFocus = null;

    private LinearLayout unfocuslayout;
    private LinearLayout focusedlayout;

    private RecyclerView mRecyclerView;
    private SHIndexAdapter mAdapter;
    private SHIndexFocusedAdapter mSHIndexFocusedAdapter;

    public static SHIndexFragment newInstance(String param1, String param2) {
        SHIndexFragment fragment = new SHIndexFragment();
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
        fragmentManager = context.getFragmentManager();

        indexEntityList= AppGlobal.getInstance().getIndexEntityList();

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

        mSHIndexFocusedAdapter=new SHIndexFocusedAdapter(indexEntities,activity);
    }

    public SHIndexFragment() {
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

//    String jianyiStr1=null;
//    String jianyiStr2=null;
//    String jianyiStr3=null;
//    String jianyiStr4=null;
//    String jianyiStr5=null;
//    String jianyiStr6=null;
//    String jianyiStr7=null;
//    String jianyiStr8=null;
//    String jianyiStr9=null;


    private int calculateDpToPx(int size_in_dp){
        final float scale = getResources().getDisplayMetrics().density;
        return  (int) (size_in_dp * scale + 0.5f);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_shindex_new_01, container, false);
        mRecyclerView= (RecyclerView) view.findViewById(R.id.id_recyclerview_horizontal);
        mRecyclerView.setNextFocusUpId(R.id.shenhuozhishu_textview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mSHIndexFocusedAdapter);



        //设置适配器
//        mAdapter = new SHIndexAdapter(indexEntities,context);
//        mRecyclerView.setAdapter(mAdapter);

//        chuanyizhishu_txtview= (TextView) view.findViewById(R.id.chuanyizhishu_txtview);
//        ganmaozhishu_txtview= (TextView) view.findViewById(R.id.ganmaozhishu_txtview);
//        huazhuangzhishu_txtview= (TextView) view.findViewById(R.id.huazhuangzhishu_txtview);
//        tyjzhishu_txtview= (TextView) view.findViewById(R.id.tyjzhishu_txtview);
//        ziwaixianzhishu_txtview= (TextView) view.findViewById(R.id.ziwaixianzhishu_txtview);
//        shushiduzhishu_txtview= (TextView) view.findViewById(R.id.shushiduzhishu_txtview);
//        lvyouzhishu_txtview= (TextView) view.findViewById(R.id.lvyouzhishu_txtview);
//        yundongzhishu_txtview= (TextView) view.findViewById(R.id.yundongzhishu_txtview);
//        xichezhishu_txtview= (TextView) view.findViewById(R.id.xichezhishu_txtview);

//        chuanyilayout= (LinearLayout) view.findViewById(R.id.chuanyilayout);
//        ganmaolayout= (LinearLayout) view.findViewById(R.id.ganmaolayout);
//        huazhuanglayout= (LinearLayout) view.findViewById(R.id.huazhuanglayout);
//        taiyangjinglayout= (LinearLayout) view.findViewById(R.id.taiyangjinglayout);
//        ziwaixianlayout= (LinearLayout) view.findViewById(R.id.ziwaixianlayout);
//        shushidulayout= (LinearLayout) view.findViewById(R.id.shushidulayout);
//        lvyoulayout= (LinearLayout) view.findViewById(R.id.lvyoulayout);
//        yundonglayout= (LinearLayout) view.findViewById(R.id.yundonglayout);
//        xichelayout= (LinearLayout) view.findViewById(R.id.xichelayout);


//        ftaFocus = fragmentManager.beginTransaction();
//        ftaunFocus = fragmentManager.beginTransaction();
//        fragmentTransaction = fragmentManager.beginTransaction();

//        final UnfocusedSHIndexFragment unfocusedSHIndexFragment=UnfocusedSHIndexFragment.newInstance("chuanyilayout");
//        final FocusedSHIndexFragment focusedSHIndexFragment=FocusedSHIndexFragment.newInstance("chuanyilayout");
//
//        fragmentTransaction.replace(R.id.chuanyilayout,unfocusedSHIndexFragment);
//        fragmentTransaction.commit();
//
//        chuanyilayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(hasFocus){
//                    ftaFocus.replace(R.id.chuanyilayout,focusedSHIndexFragment);
//                    ftaFocus.commit();
//                }else{
//                    ftaunFocus.replace(R.id.chuanyilayout,unfocusedSHIndexFragment);
//                    //java.lang.IllegalStateException: commit already called
//                    ftaunFocus.commit();
//                }
//            }
//        });


//        shindex_container_layout= (LinearLayout) view.findViewById(R.id.shindex_container_layout);
//
//        horizontalScrollView= (HorizontalScrollView) view.findViewById(R.id.scrollView);
//        horizontalScrollView.setHorizontalScrollBarEnabled(false);// 隐藏滚动条
//        horizontalScrollView.setSmoothScrollingEnabled(true);
//        horizontalScrollView.setHorizontalFadingEdgeEnabled(true);
//        horizontalScrollView.setHorizontalScrollBarEnabled(true);

//        if(indexEntityList!=null && indexEntityList.size()>0){
//
//            shindex_container_layout.removeAllViews();
//
//            for(IndexEntity ie:indexEntityList){
//
//                if(ie.getName().equals("穿衣指数")){
//                    indexEntities[0]=ie;
//                }
//                if(ie.getName().equals("感冒指数")){
//                    indexEntities[1]=ie;
//                }
//                if(ie.getName().equals("化妆指数")){
//                    indexEntities[2]=ie;
//                }
//                if(ie.getName().equals("太阳镜指数")){
//                    indexEntities[3]=ie;
//                }
//                if(ie.getName().equals("紫外线指数")){
//                    indexEntities[4]=ie;
//                }
//                if(ie.getName().equals("舒适度指数")){
//                    indexEntities[5]=ie;
//                }
//                if(ie.getName().contains("旅游指数")){
//                    indexEntities[6]=ie;
//                }
//                if(ie.getName().equals("运动指数")){
//                    indexEntities[7]=ie;
//                }
//                if(ie.getName().equals("洗车指数")){
//                    indexEntities[8]=ie;
//                }
//            }
//
//
//            for(int i=0;i<indexEntities.length;i++){
//
//                convertView=inflater.inflate(R.layout.shindex_item, container,false);
//                convertView.setFocusable(true);
//
//                convertViewFocused=inflater.inflate(R.layout.zhishu_focused_item, container,false);
//                convertViewFocused.setFocusable(true);
//
//                zhishuimg= (ImageView) convertView.findViewById(R.id.zhishuimg);
//                zhishuTxtview= (TextView) convertView.findViewById(R.id.zhishuTxtview);
//                zhishuContentTxtview= (TextView) convertView.findViewById(R.id.zhishuContentTxtview);
//
//                switch(i){
//                    case 0:
//                        zhishuimg.setBackground(getResources().getDrawable(R.drawable.chuanyi));
//                        zhishuTxtview.setText(getString(R.string.index01));
//
//                        unfocuslayout= (LinearLayout) convertView.findViewById(R.id.unfocuslayout);
//                        focusedlayout= (LinearLayout) convertView.findViewById(R.id.focusedlayout);
//
//                        if(indexEntities[i]!=null && indexEntities[i] instanceof IndexEntity){
//                            zhishuContentTxtview.setText(indexEntities[i].getState());
//                            jianyiStr=indexEntities[i].getJy();
//                        }
//
//                        convertView.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                ToastUtil.show(context, jianyiStr);
//                                unfocuslayout.setVisibility(View.GONE);
//                                unfocuslayout.invalidate();
//                                focusedlayout.setVisibility(View.VISIBLE);
//                                focusedlayout.invalidate();
//                            }
//                        });
//
////                        convertView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
////                            @Override
////                            public void onFocusChange(View v, boolean hasFocus) {
////                                if(hasFocus){
////                                    ToastUtil.show(context, jianyiStr);
////                                    unfocuslayout.setVisibility(View.GONE);
////                                    unfocuslayout.invalidate();
////                                    focusedlayout.setVisibility(View.VISIBLE);
////                                    focusedlayout.invalidate();
////
////                                }else{
////                                    unfocuslayout.setVisibility(View.VISIBLE);
////                                    unfocuslayout.invalidate();
////                                    focusedlayout.setVisibility(View.GONE);
////                                    focusedlayout.invalidate();
////                                }
////                            }
////                        });
//
//                        shindex_container_layout.addView(convertView,0);
//
//                        break;
//                    case 1:
//                        zhishuimg.setBackground(getResources().getDrawable(R.drawable.ganmao));
//                        zhishuTxtview.setText(getString(R.string.index02));
//
//                        if(indexEntities[i]!=null && indexEntities[i] instanceof IndexEntity){
//                            zhishuContentTxtview.setText(indexEntities[i].getState());
//                            jianyiStr2=indexEntities[i].getJy();
//                        }
//
//                        convertView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                            @Override
//                            public void onFocusChange(View v, boolean hasFocus) {
//                                if(hasFocus){
//                                    unfocuslayout.setVisibility(View.GONE);
//                                    focusedlayout.setVisibility(View.VISIBLE);
//                                    ToastUtil.show(context,jianyiStr2);
//                                }else{
//                                    unfocuslayout.setVisibility(View.VISIBLE);
//                                    focusedlayout.setVisibility(View.GONE);
//                                }
//                            }
//                        });
//
//                        shindex_container_layout.addView(convertView,1);
//                        break;
//
//                    case 2:
//                        zhishuimg.setBackground(getResources().getDrawable(R.drawable.huazhuang));
//                        zhishuTxtview.setText(getString(R.string.index03));
//
//                        if(indexEntities[i]!=null && indexEntities[i] instanceof IndexEntity){
//                            zhishuContentTxtview.setText(indexEntities[i].getState());
//                            jianyiStr3=indexEntities[i].getJy();
//                        }
//
//                        convertView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                            @Override
//                            public void onFocusChange(View v, boolean hasFocus) {
//                                if(hasFocus){
//                                    unfocuslayout.setVisibility(View.GONE);
//                                    focusedlayout.setVisibility(View.VISIBLE);
//                                    ToastUtil.show(context,jianyiStr3);
//                                }else{
//                                    unfocuslayout.setVisibility(View.VISIBLE);
//                                    focusedlayout.setVisibility(View.GONE);
//                                }
//                            }
//                        });
//
//                        shindex_container_layout.addView(convertView,2);
//                        break;
//
//                    case 3:
//                        zhishuimg.setBackground(getResources().getDrawable(R.drawable.taiyangjing));
//                        zhishuTxtview.setText(getString(R.string.index04));
//                        if(indexEntities[i]!=null && indexEntities[i] instanceof IndexEntity){
//                            zhishuContentTxtview.setText(indexEntities[i].getState());
//                            jianyiStr4=indexEntities[i].getJy();
//                        }
//
//                        convertView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                            @Override
//                            public void onFocusChange(View v, boolean hasFocus) {
//                                if(hasFocus){
////                                    unfocuslayout.setVisibility(View.GONE);
////                                    focusedlayout.setVisibility(View.VISIBLE);
//                                    ToastUtil.show(context,jianyiStr4);
//                                }else{
////                                    unfocuslayout.setVisibility(View.VISIBLE);
////                                    focusedlayout.setVisibility(View.GONE);
//                                }
//                            }
//                        });
//
//                        shindex_container_layout.addView(convertView,3);
//                        break;
//
//                    case 4:
//                        zhishuimg.setBackground(getResources().getDrawable(R.drawable.ziwaixian));
//                        zhishuTxtview.setText(getString(R.string.index05));
//                        if(indexEntities[i]!=null && indexEntities[i] instanceof IndexEntity){
//                            zhishuContentTxtview.setText(indexEntities[i].getState());
//                            jianyiStr5=indexEntities[i].getJy();
//                        }
//
//                        convertView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                            @Override
//                            public void onFocusChange(View v, boolean hasFocus) {
//                                if(hasFocus){
////                                    unfocuslayout.setVisibility(View.GONE);
////                                    focusedlayout.setVisibility(View.VISIBLE);
//                                    ToastUtil.show(context,jianyiStr5);
//                                }else{
////                                    unfocuslayout.setVisibility(View.VISIBLE);
////                                    focusedlayout.setVisibility(View.GONE);
//                                }
//                            }
//                        });
//                        shindex_container_layout.addView(convertView,4);
//                        break;
//
//                    case 5:
//                        zhishuimg.setBackground(getResources().getDrawable(R.drawable.shushi));
//                        zhishuTxtview.setText(getString(R.string.index06));
//                        if(indexEntities[i]!=null && indexEntities[i] instanceof IndexEntity){
//                            zhishuContentTxtview.setText(indexEntities[i].getState());
//                            jianyiStr6=indexEntities[i].getJy();
//                        }
//
//                        convertView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                            @Override
//                            public void onFocusChange(View v, boolean hasFocus) {
//                                if(hasFocus){
//                                    ToastUtil.show(context,jianyiStr6);
//                                }
//                            }
//                        });
//                        shindex_container_layout.addView(convertView,5);
//                        break;
//
//                    case 6:
//                        zhishuimg.setBackground(getResources().getDrawable(R.drawable.lvyou));
//                        zhishuTxtview.setText(getString(R.string.index07));
//                        if(indexEntities[i]!=null && indexEntities[i] instanceof IndexEntity){
//                            zhishuContentTxtview.setText(indexEntities[i].getState());
//                            jianyiStr7=indexEntities[i].getJy();
//                        }
//
//                        convertView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                            @Override
//                            public void onFocusChange(View v, boolean hasFocus) {
//                                if(hasFocus){
//                                    ToastUtil.show(context,jianyiStr7);
//                                }
//                            }
//                        });
//                        shindex_container_layout.addView(convertView,6);
//                        break;
//
//                    case 7:
//                        zhishuimg.setBackground(getResources().getDrawable(R.drawable.yundong));
//                        zhishuTxtview.setText(getString(R.string.index08));
//                        if(indexEntities[i]!=null && indexEntities[i] instanceof IndexEntity){
//                            zhishuContentTxtview.setText(indexEntities[i].getState());
//                            jianyiStr8=indexEntities[i].getJy();
//                        }
//
//                        convertView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                            @Override
//                            public void onFocusChange(View v, boolean hasFocus) {
//                                if(hasFocus){
//                                    ToastUtil.show(context,jianyiStr8);
//                                }
//                            }
//                        });
//                        shindex_container_layout.addView(convertView,7);
//                        break;
//
//                    case 8:
//                        zhishuimg.setBackground(getResources().getDrawable(R.drawable.xiche));
//                        zhishuTxtview.setText(getString(R.string.index09));
//                        if(indexEntities[i]!=null && indexEntities[i] instanceof IndexEntity){
//                            zhishuContentTxtview.setText(indexEntities[i].getState());
//                            jianyiStr9=indexEntities[i].getJy();
//                        }
//
//                        convertView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                            @Override
//                            public void onFocusChange(View v, boolean hasFocus) {
//                                if(hasFocus){
//                                    ToastUtil.show(context,jianyiStr9);
//                                }
//                            }
//                        });
//                        shindex_container_layout.addView(convertView,8);
//                        break;
//                }
//
//                shindex_container_layout.invalidate();
//            }
//        }

//        if(shindex_container_layout.getChildAt(0)!=null){
//
//            shindex_container_layout.getChildAt(0).setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View v, boolean hasFocus) {
//                    if(hasFocus){
//                        ToastUtil.show(context,jianyiStr);
//                        horizontalScrollView.removeViewAt(0);
//                        shindex_container_layout.removeViewAt(0);
//                        shindex_container_layout.addView(convertViewFocused,0);
//                        shindex_container_layout.invalidate();
//
//                    }else{
//                        horizontalScrollView.removeViewAt(0);
//                        shindex_container_layout.removeViewAt(0);
//                        shindex_container_layout.addView(convertView,0);
//                        shindex_container_layout.invalidate();
//                    }
//                }
//            });
//        }

        return view;
    }

    private View createView(){
        LinearLayout layout01=new LinearLayout(context);
        /**
         *   android:orientation="vertical"
         android:layout_gravity="center"
         android:layout_width="400dp"
         android:layout_height="305dp"
         android:paddingTop="40dp"
         android:paddingBottom="40dp"
         android:background="@drawable/index_03">
         */
        layout01.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params01=new LinearLayout.LayoutParams(calculateDpToPx(400), calculateDpToPx(305));
        //左上右下
        layout01.setPadding(0, 40, 0, 40);
        layout01.setBackground(getResources().getDrawable(R.drawable.index_03));
        layout01.setGravity(Gravity.CENTER);
        layout01.setLayoutParams(params01);

        /**
         * <LinearLayout
         android:layout_width="match_parent"
         android:layout_height="0dp"
         android:layout_weight="0.5"
         android:layout_marginLeft="30dp"
         android:layout_marginRight="30dp"
         android:orientation="horizontal">
         */
        LinearLayout layout02=new LinearLayout(context);
        layout01.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params02=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
        params02.weight=0.5f;
        //左上右下
        params02.setMargins(30,0,30,0);
        layout02.setLayoutParams(params02);

        /**
         * <ImageView
         android:id="@+id/logo_img"
         android:layout_width="108dp"
         android:layout_height="84dp"
         android:layout_gravity="bottom"
         android:background="@drawable/shindex_15"/>
         */

        ImageView imgView=new ImageView(context);
        imgView.setTag("logo_img");
        LinearLayout.LayoutParams params03=new LinearLayout.LayoutParams(calculateDpToPx(108), calculateDpToPx(84));
        params03.gravity=Gravity.BOTTOM;
        imgView.setBackground(getResources().getDrawable(R.drawable.shindex_15));
        imgView.setLayoutParams(params03);
        layout02.addView(imgView);

        /**
         *  <TextView
         android:id="@+id/index01_Textview"
         android:layout_width="wrap_content"
         android:layout_height="wrap_content"
         android:gravity="center"
         android:layout_marginLeft="5dp"
         android:layout_gravity="bottom"
         android:textSize="65sp"
         android:textStyle="bold"
         android:textColor="@color/indexziti"
         android:text="冷"/>
         */

        TextView textView01=new TextView(context);
        textView01.setTag("index01_Textview");
        LinearLayout.LayoutParams params04=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params04.gravity=Gravity.BOTTOM;
        params04.setMargins(5,0,0,0);
        textView01.setLayoutParams(params04);
        textView01.setText("冷");
        textView01.setTextColor(getResources().getColor(R.color.indexziti));
        textView01.setTextSize(TypedValue.COMPLEX_UNIT_SP,65);
        textView01.setTypeface(Typeface.DEFAULT_BOLD);
        layout02.addView(textView01);

        /**
         *   <TextView
         android:id="@+id/index02_Textview"
         android:layout_width="wrap_content"
         android:layout_height="wrap_content"
         android:layout_marginTop="5dp"
         android:layout_gravity="bottom"
         android:layout_marginLeft="15dp"
         android:gravity="center"
         android:text="穿衣指数"
         android:textSize="35sp"
         android:textColor="@color/indexziti01"/>
         */

        TextView textView02=new TextView(context);
        textView01.setTag("index02_Textview");
        LinearLayout.LayoutParams params05=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params04.gravity=Gravity.BOTTOM;
        //左上右下
        params04.setMargins(15, 5, 0, 0);
        textView01.setLayoutParams(params05);
        textView01.setText("穿衣指数");
        textView01.setTextColor(getResources().getColor(R.color.indexziti01));
        textView01.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
        layout02.addView(textView02);

        layout01.addView(layout02);

        return layout01;

    }
}
