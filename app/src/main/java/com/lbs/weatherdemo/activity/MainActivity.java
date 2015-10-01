package com.lbs.weatherdemo.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lbs.weatherdemo.R;
import com.lbs.weatherdemo.activity.entity.AQIEntity;
import com.lbs.weatherdemo.activity.entity.HighWayEntity;
import com.lbs.weatherdemo.activity.entity.IndexEntity;
import com.lbs.weatherdemo.activity.entity.ShiKuangEntity;
import com.lbs.weatherdemo.activity.entity.WeatherEntity;
import com.lbs.weatherdemo.activity.entity.YBFSEntity;
import com.lbs.weatherdemo.activity.entity.YBTodayEntity;
import com.lbs.weatherdemo.activity.fragment.FenShiYuBaoFragment;
import com.lbs.weatherdemo.activity.fragment.GaosuFragment;
import com.lbs.weatherdemo.activity.fragment.SHIndexFragmentNew;
import com.lbs.weatherdemo.activity.fragment.SelectCityFragment;
import com.lbs.weatherdemo.activity.fragment.WuMaiFragment;
import com.lbs.weatherdemo.activity.fragment.YuBaoFragment;
import com.lbs.weatherdemo.activity.util.AppGlobal;
import com.lbs.weatherdemo.activity.util.DataUtil;
import com.lbs.weatherdemo.activity.util.DensityUtil;
import com.lbs.weatherdemo.activity.util.HttpConnUtils;
import com.lbs.weatherdemo.activity.util.NetUtils;
import com.lbs.weatherdemo.activity.util.ToastUtil;

import org.apache.commons.httpclient.util.TimeoutController;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity implements SelectCityFragment.OnFragmentInteractionListener {

    //定位
    ImageButton img_position=null;

    //湿度
    private TextView shidudata_text;
    //雾霾
    private TextView wumaidata_text;
    //气压
    private TextView qiyadata_text;

    //温度
    private TextView temp_txtview_left;
    //天气
    private TextView weather_txtview_left;

    //城市
    private TextView city_text;
    //时间
    private TextView time_text;

    private ImageView weather_img=null;

    //切换按钮
    private TextView qitianyubao_textview;
    private TextView fenshiyubao_textview;
    private TextView shenhuozhishu_textview;
    private TextView gaosuqixiang_textview;
    private TextView wumaiditu_textview;
    private TextView taifengzhuanti_textview;

    private FragmentManager fragmentManager;

    private static final int COMPLETE = 1;
    private static final int NONETWORK = 2;
    private static final int TIMEOUT = 3;
    private static final int ROADCOMPLETE = 4;
    private static final int AQICOMPLETE = 5;

    private static final String TAG="WEATHER";

    // 再按一次退出程序
    private boolean isExits = false;

    //预报
    List<WeatherEntity> weatherEntityList=null;
    //指数
    List<IndexEntity> indexEntityList=null;
    //分时预报
    List<YBFSEntity> ybfsEntityList=null;
    //日出日落
    List<YBTodayEntity> ybTodayEntityList=null;

    //高速气象
    List<HighWayEntity> highWayEntityList=null;

    //aqi列表
    private List<AQIEntity> aqiEntityList=null;

    private String dataStr;
    private String oldDateStr;

    private Timer timer;
    private TimerTask timerTask;

    private Handler mHandler=null;

    //城市级联hashmap
    private Map<String,List<String>> cityMap=new HashMap<>();

    ProgressDialog progressdialog;

    private LinearLayout bottom_relative=null;

    private ImageView shidu_imgview=null;
    private ImageView wumai_imgview=null;
    private ImageView qiya_imgview=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_wasu2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fragmentManager = getFragmentManager();

        shidu_imgview= (ImageView) findViewById(R.id.shidu_imgview);
        LinearLayout.LayoutParams params03=new LinearLayout.LayoutParams(DensityUtil.px2dip(this, 27), DensityUtil.px2dip(this,34));

        params03.gravity= Gravity.CENTER;
        shidu_imgview.setLayoutParams(params03);

        wumai_imgview= (ImageView) findViewById(R.id.wumai_imgview);
        LinearLayout.LayoutParams params04=new LinearLayout.LayoutParams(DensityUtil.px2dip(this, 37), DensityUtil.px2dip(this,32));
        //左上右下
        params04.setMargins(20,0,0,0);
        params04.gravity= Gravity.CENTER;
        wumai_imgview.setLayoutParams(params04);

        qiya_imgview= (ImageView) findViewById(R.id.qiya_imgview);
        LinearLayout.LayoutParams params05=new LinearLayout.LayoutParams(DensityUtil.px2dip(this, 37), DensityUtil.px2dip(this,36));
        params05.setMargins(20,0,0,0);
        params05.gravity= Gravity.CENTER;
        qiya_imgview.setLayoutParams(params05);


        city_text= (TextView) findViewById(R.id.city_text);
        city_text.setFocusable(true);
        city_text.setFocusableInTouchMode(true);
        city_text.setClickable(true);
        city_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    city_text.setBackgroundResource(R.drawable.city_03);
                    ToastUtil.show(MainActivity.this,"点击选择城市");
                    SelectCityFragment selectCityFragment = new SelectCityFragment();
                    selectCityFragment.show(getFragmentManager(), "选择城市");
                }else{
                    city_text.setBackground(null);
                }
            }
        });


        shidudata_text= (TextView) findViewById(R.id.shidudata_text);
        wumaidata_text= (TextView) findViewById(R.id.wumaidata_text);
        qiyadata_text= (TextView) findViewById(R.id.qiyadata_text);

        temp_txtview_left= (TextView) findViewById(R.id.temp_txtview_left);
        weather_txtview_left= (TextView) findViewById(R.id.weather_txtview_left);

        time_text= (TextView) findViewById(R.id.time_text);
        qitianyubao_textview= (TextView) findViewById(R.id.qitianyubao_textview);


        fenshiyubao_textview= (TextView) findViewById(R.id.fenshiyubao_textview);
        fenshiyubao_textview.setClickable(true);
        fenshiyubao_textview.setFocusable(true);
        fenshiyubao_textview.setFocusableInTouchMode(true);

        shenhuozhishu_textview= (TextView) findViewById(R.id.shenhuozhishu_textview);
        shenhuozhishu_textview.setClickable(true);
        shenhuozhishu_textview.setFocusable(true);
        shenhuozhishu_textview.setFocusableInTouchMode(true);

        gaosuqixiang_textview= (TextView) findViewById(R.id.gaosuqixiang_textview);
        gaosuqixiang_textview.setClickable(true);
        gaosuqixiang_textview.setFocusable(true);
        gaosuqixiang_textview.setFocusableInTouchMode(true);

        wumaiditu_textview= (TextView) findViewById(R.id.wumaiditu_textview);
        wumaiditu_textview.setClickable(true);
        wumaiditu_textview.setFocusable(true);
        wumaiditu_textview.setFocusableInTouchMode(true);

        taifengzhuanti_textview= (TextView) findViewById(R.id.taifengzhuanti_textview);
        taifengzhuanti_textview.setClickable(true);
        taifengzhuanti_textview.setFocusable(true);
        taifengzhuanti_textview.setFocusableInTouchMode(true);

        weather_img= (ImageView) findViewById(R.id.weather_img);

        bottom_relative= (LinearLayout) findViewById(R.id.bottom_relative);

        //七天预报
        qitianyubao_textview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    qitianyubao_textview.setBackground(getResources().getDrawable(R.drawable.yb2_03));

                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    Fragment fragment = new YuBaoFragment();
                    transaction.replace(R.id.bottom_relative, fragment);
                    bottom_relative.setNextFocusUpId(R.id.qitianyubao_textview);
                    bottom_relative.setNextFocusForwardId(R.id.qitianyubao_textview);
                    transaction.commit();
                }else{
                    qitianyubao_textview.setBackground(getResources().getDrawable(R.drawable.yb2_05));
                }
            }
        });

        //分时预报
        fenshiyubao_textview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    fenshiyubao_textview.setBackground(getResources().getDrawable(R.drawable.yb2_03));

                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    Fragment fragment = new FenShiYuBaoFragment();
                    transaction.replace(R.id.bottom_relative, fragment);
                    bottom_relative.setNextFocusUpId(R.id.fenshiyubao_textview);
                    bottom_relative.setNextFocusForwardId(R.id.fenshiyubao_textview);
                    transaction.commit();
                }else{
                    fenshiyubao_textview.setBackground(getResources().getDrawable(R.drawable.yb2_05));
                }
            }
        });

        //生活指数
        shenhuozhishu_textview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    shenhuozhishu_textview.setBackground(getResources().getDrawable(R.drawable.yb2_03));
                    shenhuozhishu_textview.requestFocus(View.FOCUS_RIGHT);
                    bottom_relative.setFocusable(false);

                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    Fragment fragment = new SHIndexFragmentNew();
                    transaction.replace(R.id.bottom_relative, fragment);
                    transaction.commit();



                }else{
                    shenhuozhishu_textview.setBackground(getResources().getDrawable(R.drawable.yb2_05));
                }
            }
        });

        shenhuozhishu_textview.setClickable(true);

        shenhuozhishu_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentTransaction transaction = fragmentManager.beginTransaction();
//                Fragment fragment = new SHIndexFragmentNew();
//                transaction.replace(R.id.bottom_relative, fragment);
//                transaction.commit();
            }
        });

        //高速气象
        gaosuqixiang_textview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    gaosuqixiang_textview.setBackground(getResources().getDrawable(R.drawable.yb2_03));

                    bottom_relative.setFocusable(false);

                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    Fragment fragment = new GaosuFragment();
                    transaction.replace(R.id.bottom_relative, fragment);
                    transaction.commit();
                }else{
                    gaosuqixiang_textview.setBackground(getResources().getDrawable(R.drawable.yb2_05));
                }
            }
        });

        gaosuqixiang_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentTransaction transaction = fragmentManager.beginTransaction();
//                Fragment fragment = new GaosuFragment();
//                transaction.replace(R.id.bottom_relative, fragment);
//                transaction.commit();
            }
        });

        //雾霾地图
        wumaiditu_textview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    wumaiditu_textview.setBackground(getResources().getDrawable(R.drawable.yb2_03));
                    wumaiditu_textview.requestFocus(View.FOCUS_RIGHT);
                    bottom_relative.setFocusable(false);
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    Fragment fragment = new WuMaiFragment();
                    transaction.replace(R.id.bottom_relative, fragment);
                    transaction.commit();
                }else{
                    wumaiditu_textview.setBackground(getResources().getDrawable(R.drawable.yb2_05));
                }
            }
        });

        wumaiditu_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentTransaction transaction = fragmentManager.beginTransaction();
//                Fragment fragment = new WuMaiFragment();
//                transaction.replace(R.id.bottom_relative, fragment);
//                transaction.commit();
            }
        });

        //台风专题
        taifengzhuanti_textview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    taifengzhuanti_textview.setBackground(getResources().getDrawable(R.drawable.yb2_03));
                    ToastUtil.show(MainActivity.this,"点击 OK/确定 进入台风专题");
                }else{
                    taifengzhuanti_textview.setBackground(getResources().getDrawable(R.drawable.yb2_05));
                }
            }
        });

        taifengzhuanti_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taifengzhuanti_textview.requestFocus();
                Intent intent=new Intent(MainActivity.this,TaiFengMainActivity.class);
                startActivity(intent);
            }
        });


        mHandler = new Handler() {

            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case COMPLETE:

//                        Toast.makeText(MainActivity.this, "更新完成", Toast.LENGTH_SHORT).show();
                        Log.i("WEATHER", "更新完成");

                        ShiKuangEntity shiKuangEntity=AppGlobal.getInstance().getShiKuangEntity();

                        shidudata_text.setText(shiKuangEntity.getSd());

                        String aqiStr=shiKuangEntity.getAqi().trim();
                        if(aqiStr!=null && !aqiStr.equals("") && !aqiStr.equals("?")){
                            wumaidata_text.setText(getLevelStr(Integer.valueOf(aqiStr)));
                        }else{
                            wumaidata_text.setText("--");
                        }


                        qiyadata_text.setText(shiKuangEntity.getQy()+"hpa");

                        temp_txtview_left.setText(shiKuangEntity.getTemp()+"°");

//                        if(shiKuangEntity.getCityname()!=null && shiKuangEntity.getCityname().length()>0){
//                            city_text.setText(shiKuangEntity.getCityname().trim());
//                        }else{
//                            city_text.setText(AppGlobal.getInstance().getSecondCity());
//                        }

                        city_text.setText(AppGlobal.getInstance().getSecondCity());


                        time_text.setText(shiKuangEntity.getTime().trim());

                        List<YBTodayEntity> ybTodayEntityList=AppGlobal.getInstance().getYbTodayEntityList();

                        if(ybTodayEntityList!=null && ybTodayEntityList.size()>0){
                            for(YBTodayEntity yb:ybTodayEntityList){
                                if(yb.getDn().trim().equals("白天")){
                                    weather_txtview_left.setText(yb.getWea());
                                }
                            }
                        }
                        String weatherStr=AppGlobal.getInstance().getImgStr().trim();

                        weather_img.setBackground(getImage(weatherStr));

                        qitianyubao_textview.setClickable(true);
                        qitianyubao_textview.setFocusable(true);
                        qitianyubao_textview.setFocusableInTouchMode(true);
                        qitianyubao_textview.requestFocus();

                        GetHighWayDataAsyncTask getHighWayDataAsyncTask=new GetHighWayDataAsyncTask();
                        getHighWayDataAsyncTask.execute();
                        break;
                    case ROADCOMPLETE:
                        GetAQIDataAsyncTask getAQIDataAsyncTask=new GetAQIDataAsyncTask();
                        getAQIDataAsyncTask.execute();

                        int lengthInt=AppGlobal.getInstance().getHighWayEntityList().size();

//                        Toast.makeText(MainActivity.this, "道路数据初始化完成: "+lengthInt+" 条",Toast.LENGTH_SHORT).show();

                        break;
                    case AQICOMPLETE:
//                        Toast.makeText(MainActivity.this, "AQI数据初始化完成",Toast.LENGTH_SHORT).show();
                        break;
                    case NONETWORK:
                        Toast.makeText(MainActivity.this, "请检查网络连接情况",Toast.LENGTH_SHORT).show();
                        break;
                    case TIMEOUT:
                        Toast.makeText(MainActivity.this, "连接服务器超时",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            };
        };

        getWidthHeight();
    }

    private void getWidthHeight(){

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        double density  = dm.density;      // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
        double densityDPI = dm.densityDpi;     // 屏幕密度（每寸像素：120/160/240/320）
        double xdpi = dm.xdpi;
        double ydpi = dm.ydpi;

        Log.e(TAG + "DisplayMetrics", "xdpi=" + xdpi + "; ydpi=" + ydpi);
        Log.e(TAG + "DisplayMetrics", "density=" + density + "; densityDPI=" + densityDPI);

        int screenWidthDip = dm.widthPixels;        // 屏幕宽（dip，如：320dip）
        int screenHeightDip = dm.heightPixels;      // 屏幕宽（dip，如：533dip）
        Log.e(TAG + "DisplayMetrics", "screenWidthDip=" + screenWidthDip + "; screenHeightDip=" + screenHeightDip);

        int screenWidth  = (int)(dm.widthPixels * density + 0.5f);      // 屏幕宽（px，如：480px）
        int screenHeight = (int)(dm.heightPixels * density + 0.5f);
        Log.e(TAG + "DisplayMetrics", "screenWidth=" + screenWidth + "; screenHeight=" + screenHeight);
        AppGlobal.getInstance().setWidth(screenWidthDip);
        AppGlobal.getInstance().setHeight(screenHeightDip);
    }

    private String getLevelStr(int level){

        if(level<50){
            return "优";
        }else if(level <100){
            return "良";
        }else if(level <150){
            return "轻度污染";
        }else if(level<200){
            return "中度污染";
        }else if(level<300){
            return "重度污染";
        }else{
            return "严重污染";
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i("WEATHER","应用出现");

        progressdialog=new ProgressDialog(MainActivity.this);
        progressdialog.setCancelable(true);
        progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressdialog.setMessage("更新中...");
        progressdialog.setIndeterminate(true);
        progressdialog.show();

        //获取气象数据
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {

                if(NetUtils.isNetworkConnected(MainActivity.this) ||
                        NetUtils.isWifiConnected(MainActivity.this)){

                    GetWeatherDataAsyncTask getWeatherDataAsyncTask=new GetWeatherDataAsyncTask(AppGlobal.URL);
                    Log.i("WEATHER",AppGlobal.URL+"   "+new Date().toString());
                    getWeatherDataAsyncTask.execute();
                }
            }
        };

        timer.scheduleAtFixedRate(timerTask,10, 30 * 60 * 1000);


        List<String> hangzhou_List=new ArrayList<>();

        hangzhou_List.add(getResources().getString(R.string.str30));
        hangzhou_List.add(getResources().getString(R.string.str31));
        hangzhou_List.add(getResources().getString(R.string.str32));
        hangzhou_List.add(getResources().getString(R.string.str33));
        hangzhou_List.add(getResources().getString(R.string.str34));
        hangzhou_List.add(getResources().getString(R.string.str35));
        hangzhou_List.add(getResources().getString(R.string.str36));
        hangzhou_List.add(getResources().getString(R.string.str37));

        cityMap.put(getResources().getString(R.string.str14),hangzhou_List);

        List<String> ningbo_List=new ArrayList<>();
        ningbo_List.add(getResources().getString(R.string.str40));
        ningbo_List.add(getResources().getString(R.string.str41));
        ningbo_List.add(getResources().getString(R.string.str42));
        ningbo_List.add(getResources().getString(R.string.str43));
        ningbo_List.add(getResources().getString(R.string.str44));
        ningbo_List.add(getResources().getString(R.string.str45));
        ningbo_List.add(getResources().getString(R.string.str46));
        ningbo_List.add(getResources().getString(R.string.str47));
        ningbo_List.add(getResources().getString(R.string.str48));

        cityMap.put(getResources().getString(R.string.str15),ningbo_List);

        List<String> wenzhou_List=new ArrayList<>();
        wenzhou_List.add(getResources().getString(R.string.str51));
        wenzhou_List.add(getResources().getString(R.string.str52));
        wenzhou_List.add(getResources().getString(R.string.str53));
        wenzhou_List.add(getResources().getString(R.string.str54));
        wenzhou_List.add(getResources().getString(R.string.str55));
        wenzhou_List.add(getResources().getString(R.string.str56));
        wenzhou_List.add(getResources().getString(R.string.str57));
        wenzhou_List.add(getResources().getString(R.string.str58));
        wenzhou_List.add(getResources().getString(R.string.str59));

        cityMap.put(getResources().getString(R.string.str16),wenzhou_List);



        List<String> shaoxing_List=new ArrayList<>();
        shaoxing_List.add(getResources().getString(R.string.str60));
        shaoxing_List.add(getResources().getString(R.string.str61));
        shaoxing_List.add(getResources().getString(R.string.str62));
        shaoxing_List.add(getResources().getString(R.string.str63));
        shaoxing_List.add(getResources().getString(R.string.str64));
        shaoxing_List.add(getResources().getString(R.string.str65));

        cityMap.put(getResources().getString(R.string.str17),shaoxing_List);


        List<String> huzhou_List=new ArrayList<>();
        huzhou_List.add(getResources().getString(R.string.str66));
        huzhou_List.add(getResources().getString(R.string.str68));
        huzhou_List.add(getResources().getString(R.string.str69));
        huzhou_List.add(getResources().getString(R.string.str70));

        cityMap.put(getResources().getString(R.string.str18),huzhou_List);



        List<String> jiaxing_List=new ArrayList<>();
        jiaxing_List.add(getResources().getString(R.string.str71));
        jiaxing_List.add(getResources().getString(R.string.str73));
        jiaxing_List.add(getResources().getString(R.string.str74));
        jiaxing_List.add(getResources().getString(R.string.str75));
        jiaxing_List.add(getResources().getString(R.string.str76));
        jiaxing_List.add(getResources().getString(R.string.str77));

        cityMap.put(getResources().getString(R.string.str19),jiaxing_List);



        List<String> jinhua_List=new ArrayList<>();
        jinhua_List.add(getResources().getString(R.string.str78));
        jinhua_List.add(getResources().getString(R.string.str80));
        jinhua_List.add(getResources().getString(R.string.str81));
        jinhua_List.add(getResources().getString(R.string.str82));
        jinhua_List.add(getResources().getString(R.string.str83));
        jinhua_List.add(getResources().getString(R.string.str84));
        jinhua_List.add(getResources().getString(R.string.str85));
        jinhua_List.add(getResources().getString(R.string.str86));

        cityMap.put(getResources().getString(R.string.str20),jinhua_List);



        List<String> xuzhou_List=new ArrayList<>();
        xuzhou_List.add(getResources().getString(R.string.str87));
        xuzhou_List.add(getResources().getString(R.string.str88));
        xuzhou_List.add(getResources().getString(R.string.str89));
        xuzhou_List.add(getResources().getString(R.string.str90));
        xuzhou_List.add(getResources().getString(R.string.str91));
        xuzhou_List.add(getResources().getString(R.string.str92));

        cityMap.put(getResources().getString(R.string.str21),xuzhou_List);



        List<String> zhoushan_List=new ArrayList<>();
        zhoushan_List.add(getResources().getString(R.string.str93));
        zhoushan_List.add(getResources().getString(R.string.str94));
        zhoushan_List.add(getResources().getString(R.string.str95));
        zhoushan_List.add(getResources().getString(R.string.str96));

        cityMap.put(getResources().getString(R.string.str22),zhoushan_List);



        List<String> taizhou_List=new ArrayList<>();
        taizhou_List.add(getResources().getString(R.string.str115));
        taizhou_List.add(getResources().getString(R.string.str97));
        taizhou_List.add(getResources().getString(R.string.str98));
        taizhou_List.add(getResources().getString(R.string.str99));
        taizhou_List.add(getResources().getString(R.string.str100));
        taizhou_List.add(getResources().getString(R.string.str101));
        taizhou_List.add(getResources().getString(R.string.str102));
        taizhou_List.add(getResources().getString(R.string.str103));
        taizhou_List.add(getResources().getString(R.string.str104));
        taizhou_List.add(getResources().getString(R.string.str105));

        cityMap.put(getResources().getString(R.string.str23),taizhou_List);

        List<String> lishui_List=new ArrayList<>();
        lishui_List.add(getResources().getString(R.string.str106));
        lishui_List.add(getResources().getString(R.string.str107));
        lishui_List.add(getResources().getString(R.string.str108));
        lishui_List.add(getResources().getString(R.string.str109));
        lishui_List.add(getResources().getString(R.string.str110));
        lishui_List.add(getResources().getString(R.string.str111));
        lishui_List.add(getResources().getString(R.string.str112));
        lishui_List.add(getResources().getString(R.string.str113));
        lishui_List.add(getResources().getString(R.string.str114));

        cityMap.put(getResources().getString(R.string.str24),lishui_List);
        AppGlobal.getInstance().setCityMap(null);
        AppGlobal.getInstance().setCityMap(cityMap);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onFragmentInteraction(String topCity, String secondCity) {
        //更新数据刷新界面
        ToastUtil.show(MainActivity.this,topCity+"  "+secondCity);
        String mUrl=getWeatherUrl(topCity,secondCity);
        AppGlobal.getInstance().setTopCity(topCity);
        AppGlobal.getInstance().setSecondCity(secondCity);
        GetWeatherDataAsyncTask getWeatherDataAsyncTask=new GetWeatherDataAsyncTask(mUrl);
        Log.i("WEATHER",AppGlobal.URL+"   "+new Date().toString());
        getWeatherDataAsyncTask.execute();
    }

    private void refreshActivity(){
        MainActivity.this.finish();
        Intent intent=new Intent(MainActivity.this,MainActivity.class);
        startActivity(intent);
    }

    private String getWeatherUrl(String city,String cityzone){
        /**
         *  <string name="str60">越城区</string>
         <string name="str61">柯桥区</string>
         <string name="str62">上虞区</string>
         <string name="str63">诸暨市</string>
         <string name="str64">嵊州市</string>
         <string name="str65">新昌县</string>
         */
        String mServerIP=AppGlobal.SERVERIP;
        String mServerPort=AppGlobal.SERVERPORT;
        String mUrl="";
        if(city.equals(getString(R.string.str14))){
            if(cityzone.equals(getString(R.string.str30))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210101.json";
            }else if(cityzone.equals(getString(R.string.str31))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210106.json";
            }else if(cityzone.equals(getString(R.string.str32))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210102.json";
            }else if(cityzone.equals(getString(R.string.str33))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210108.json";
            }else if(cityzone.equals(getString(R.string.str34))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210105.json";
            }else if(cityzone.equals(getString(R.string.str35))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210107.json";
            }else if(cityzone.equals(getString(R.string.str36))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210103.json";
            }else if(cityzone.equals(getString(R.string.str37))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210104.json";
            }
        }
        if(city.equals(getString(R.string.str15))){
            if(cityzone.equals(getString(R.string.str40))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210401.json";
            }else if(cityzone.equals(getString(R.string.str41))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210410.json";
            }else if(cityzone.equals(getString(R.string.str42))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210412.json";
            }else if(cityzone.equals(getString(R.string.str43))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210411.json";
            }else if(cityzone.equals(getString(R.string.str44))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210404.json";
            }else if(cityzone.equals(getString(R.string.str45))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210403.json";
            }else if(cityzone.equals(getString(R.string.str46))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210405.json";
            }else if(cityzone.equals(getString(R.string.str47))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210406.json";
            }else if(cityzone.equals(getString(R.string.str48))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210408.json";
            }
        }
        if(city.equals(getString(R.string.str16))){
            if(cityzone.equals(getString(R.string.str51))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210701.json";
            }else if(cityzone.equals(getString(R.string.str52))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210705.json";
            }else if(cityzone.equals(getString(R.string.str53))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210707.json";
            }else if(cityzone.equals(getString(R.string.str54))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210706.json";
            }else if(cityzone.equals(getString(R.string.str55))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210708.json";
            }else if(cityzone.equals(getString(R.string.str56))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210704.json";
            }else if(cityzone.equals(getString(R.string.str57))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210709.json";
            }else if(cityzone.equals(getString(R.string.str58))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210703.json";
            }else if(cityzone.equals(getString(R.string.str59))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210702.json";
            }
        }
        if(city.equals(getString(R.string.str17))){
            if(cityzone.equals(getString(R.string.str60))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210501.json";
            }else if(cityzone.equals(getString(R.string.str61))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210506.json";
            }else if(cityzone.equals(getString(R.string.str62))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210503.json";
            }else if(cityzone.equals(getString(R.string.str63))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210502.json";
            }else if(cityzone.equals(getString(R.string.str64))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210505.json";
            }else if(cityzone.equals(getString(R.string.str65))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210504.json";
            }
        }

        if(city.equals(getString(R.string.str18))){
            if(cityzone.equals(getString(R.string.str66))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210201.json";
            }else if(cityzone.equals(getString(R.string.str68))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210204.json";
            }else if(cityzone.equals(getString(R.string.str69))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210202.json";
            }else if(cityzone.equals(getString(R.string.str70))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210203.json";
            }
        }

        if(city.equals(getString(R.string.str19))){
            if(cityzone.equals(getString(R.string.str71))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210301.json";
            }else if(cityzone.equals(getString(R.string.str73))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210303.json";
            }else if(cityzone.equals(getString(R.string.str74))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210305.json";
            }else if(cityzone.equals(getString(R.string.str75))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210304.json";
            }else if(cityzone.equals(getString(R.string.str76))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210302.json";
            }else if(cityzone.equals(getString(R.string.str77))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210306.json";
            }
        }

        if(city.equals(getString(R.string.str20))){
            if(cityzone.equals(getString(R.string.str78))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210901.json";
            }else if(cityzone.equals(getString(R.string.str80))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210903.json";
            }else if(cityzone.equals(getString(R.string.str81))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210905.json";
            }else if(cityzone.equals(getString(R.string.str82))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210907.json";
            }else if(cityzone.equals(getString(R.string.str83))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210904.json";
            }else if(cityzone.equals(getString(R.string.str84))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210906.json";
            }else if(cityzone.equals(getString(R.string.str85))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210902.json";
            }else if(cityzone.equals(getString(R.string.str86))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210908.json";
            }
        }

        if(city.equals(getString(R.string.str21))){
            if(cityzone.equals(getString(R.string.str87))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101211001.json";
            }else if(cityzone.equals(getString(R.string.str88))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101211006.json";
            }else if(cityzone.equals(getString(R.string.str89))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101211005.json";
            }else if(cityzone.equals(getString(R.string.str90))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101211002.json";
            }else if(cityzone.equals(getString(R.string.str91))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101211003.json";
            }else if(cityzone.equals(getString(R.string.str92))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101211004.json";
            }
        }

        if(city.equals(getString(R.string.str22))){
            if(cityzone.equals(getString(R.string.str93))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101211106.json";
            }else if(cityzone.equals(getString(R.string.str94))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101211105.json";
            }else if(cityzone.equals(getString(R.string.str95))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101211104.json";
            }else if(cityzone.equals(getString(R.string.str96))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101211102.json";
            }else if(cityzone.equals(getString(R.string.str116))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101211101.json";
            }
        }

        if(city.equals(getString(R.string.str23))){
            if(cityzone.equals(getString(R.string.str115))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210609.json";
            }else if(cityzone.equals(getString(R.string.str97))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210611.json";
            }else if(cityzone.equals(getString(R.string.str98))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210612.json";
            }else if(cityzone.equals(getString(R.string.str99))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210613.json";
            }else if(cityzone.equals(getString(R.string.str100))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210607.json";
            }else if(cityzone.equals(getString(R.string.str101))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210610.json";
            }else if(cityzone.equals(getString(R.string.str102))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210603.json";
            }else if(cityzone.equals(getString(R.string.str103))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210604.json";
            }else if(cityzone.equals(getString(R.string.str104))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210605.json";
            }else if(cityzone.equals(getString(R.string.str105))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210606.json";
            }else if(cityzone.equals(getString(R.string.str117))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210601.json";
            }
        }

        if(city.equals(getString(R.string.str24))){
            if(cityzone.equals(getString(R.string.str106))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210801.json";
            }else if(cityzone.equals(getString(R.string.str107))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210803.json";
            }else if(cityzone.equals(getString(R.string.str108))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210805.json";
            }else if(cityzone.equals(getString(R.string.str109))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210804.json";
            }else if(cityzone.equals(getString(R.string.str110))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210802.json";
            }else if(cityzone.equals(getString(R.string.str111))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210808.json";
            }else if(cityzone.equals(getString(R.string.str112))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210806.json";
            }else if(cityzone.equals(getString(R.string.str113))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210807.json";
            }else if(cityzone.equals(getString(R.string.str114))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/101210809.json";
            }
        }

//        <!--普陀山风景名胜区:10121110104A;
//        横店电影城:10121090402A;
//        南湖景区:10121030101A;
//        枫泾古镇:10121030201A;
//        西塘古镇:10121030202A;
//        西湖:10121010109A;
//        西溪国家湿地公园:10121010110A;
//        淳安县千岛湖风景区:10121010401A;
//        浙西大峡谷:10121010702A;
//        天目山风景区:10121010701A;
//        乌镇:10121020102A;
//        南浔古镇景区:10121020101A;
//        中南百草园:10121020302A
//
//                <string name="str121">普陀山风景名胜区</string>
//        <string name="str122">横店电影城</string>
//        <string name="str123">南湖景区</string>
//        <string name="str124">枫泾古镇</string>
//        <string name="str125">西塘古镇</string>
//        <string name="str126">西湖</string>
//        <string name="str127">西溪国家湿地公园</string>
//        <string name="str128">淳安县千岛湖风景区</string>
//        <string name="str129">浙西大峡谷</string>
//        <string name="str130">天目山风景区</string>
//        <string name="str131">乌镇</string>
//        <string name="str132">南浔古镇景区</string>
//        <string name="str133">中南百草园</string>-->



        if(city.equals(getString(R.string.str120))){
            if(cityzone.equals(getString(R.string.str121))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/10121110104A.json";
            }else if(cityzone.equals(getString(R.string.str122))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/10121090402A.json";
            }else if(cityzone.equals(getString(R.string.str123))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/10121030101A.json";
            }else if(cityzone.equals(getString(R.string.str124))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/10121030201A.json";
            }else if(cityzone.equals(getString(R.string.str125))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/10121030202A.json";
            }else if(cityzone.equals(getString(R.string.str126))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/10121010109A.json";
            }else if(cityzone.equals(getString(R.string.str127))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/10121010110A.json";
            }else if(cityzone.equals(getString(R.string.str128))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/10121010401A.json";
            }else if(cityzone.equals(getString(R.string.str129))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/10121010702A.json";
            }else if(cityzone.equals(getString(R.string.str130))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/10121010701A.json";
            }else if(cityzone.equals(getString(R.string.str131))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/10121020102A.json";
            }else if(cityzone.equals(getString(R.string.str132))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/10121020101A.json";
            }else if(cityzone.equals(getString(R.string.str133))){
                mUrl="http://"+mServerIP+":"+mServerPort+"/weather/json/"+ DataUtil.getDate(new Date())+"/10121020302A.json";
            }

        }

        return mUrl;
    }


    public class GetAQIDataAsyncTask extends AsyncTask<String, Integer, String>{

        Message msgAqi;
        String result;

        public GetAQIDataAsyncTask() {
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            msgAqi = mHandler.obtainMessage();
            msgAqi.what=AQICOMPLETE;
            mHandler.sendMessage(msgAqi);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... params) {

            if (NetUtils.isNetworkConnected(MainActivity.this) ||
                    NetUtils.isWifiConnected(MainActivity.this)) {

                try {
                    result = HttpConnUtils.download(AppGlobal.getInstance().AQIURL);

                    if(result!=null && !result.equals("")){

                        aqiEntityList=new ArrayList<>();

                        JSONArray jsonArrybtoday = new JSONArray(result);

                        for(int i=0;i<jsonArrybtoday.length();i++){

                            AQIEntity aqiEntity=new AQIEntity();
                            JSONObject jsonArrayaqi= (JSONObject) jsonArrybtoday.get(i);

                            if(jsonArrayaqi.has("city") && jsonArrayaqi.get("city")!=null){
                                aqiEntity.setCity(jsonArrayaqi.get("city").toString());
                            }
                            if(jsonArrayaqi.has("code") && jsonArrayaqi.get("code")!=null){
                                aqiEntity.setCode(jsonArrayaqi.get("code").toString());
                            }
                            if(jsonArrayaqi.has("aqi") && jsonArrayaqi.get("aqi")!=null){
                                aqiEntity.setAqi(jsonArrayaqi.get("aqi").toString());
                            }
                            aqiEntityList.add(aqiEntity);
                        }

                        AppGlobal.getInstance().setAqiEntityList(null);
                        AppGlobal.getInstance().setAqiEntityList(aqiEntityList);
                    }

                } catch (TimeoutController.TimeoutException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtil.show(MainActivity.this,"JSON解析错误");
                }
                Log.i("AQIINDEX",result);
            } else {
                msgAqi = mHandler.obtainMessage();
                msgAqi.what = NONETWORK;
                mHandler.sendMessage(msgAqi);
            }
            return null;
        }
    }

    //获取高速气象数据
    public class GetHighWayDataAsyncTask extends AsyncTask<String, Integer, String>{

        Message msghighway;

        private String result1014=null;
        private String result1015=null;
        private String result1016=null;
        private String result1017=null;
        private String result1018=null;
        private String result1119=null;
        private String result1120=null;
        private String result1121=null;
        private String result1118=null;
        private String result1222=null;
        private String result1223=null;
        private String result1322=null;


        public GetHighWayDataAsyncTask() {

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            msghighway = mHandler.obtainMessage();
            msghighway.what=ROADCOMPLETE;
            mHandler.sendMessage(msghighway);
            Log.i("HIGHWAY", "解析数据线程执行完毕");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                if (NetUtils.isNetworkConnected(MainActivity.this) ||
                        NetUtils.isWifiConnected(MainActivity.this)) {
                    Log.i("HIGHWAY", "连接网络成功，开始拉取数据");

                    highWayEntityList=new ArrayList<>();

                    result1014=HttpConnUtils.download(AppGlobal.getInstance().getURL(getString(R.string.wasu10),getString(R.string.wasu14)));
                    Log.i("HIGHWAY", AppGlobal.getInstance().getURL(getString(R.string.wasu10),getString(R.string.wasu14)));
                    Log.i("HIGHWAY", result1014);
                    result1015=HttpConnUtils.download(AppGlobal.getInstance().getURL(getString(R.string.wasu10),getString(R.string.wasu15)));
                    Log.i("HIGHWAY", AppGlobal.getInstance().getURL(getString(R.string.wasu10),getString(R.string.wasu15)));
                    Log.i("HIGHWAY", result1015);
                    result1016=HttpConnUtils.download(AppGlobal.getInstance().getURL(getString(R.string.wasu10),getString(R.string.wasu16)));
                    Log.i("HIGHWAY", AppGlobal.getInstance().getURL(getString(R.string.wasu10),getString(R.string.wasu16)));
                    Log.i("HIGHWAY", result1016);
                    result1017=HttpConnUtils.download(AppGlobal.getInstance().getURL(getString(R.string.wasu10),getString(R.string.wasu17)));
                    Log.i("HIGHWAY", AppGlobal.getInstance().getURL(getString(R.string.wasu10),getString(R.string.wasu17)));
                    Log.i("HIGHWAY", result1017);
                    result1018=HttpConnUtils.download(AppGlobal.getInstance().getURL(getString(R.string.wasu10),getString(R.string.wasu18)));
                    Log.i("HIGHWAY", AppGlobal.getInstance().getURL(getString(R.string.wasu10),getString(R.string.wasu18)));
                    Log.i("HIGHWAY", result1018);
                    result1119=HttpConnUtils.download(AppGlobal.getInstance().getURL(getString(R.string.wasu11),getString(R.string.wasu19)));
                    Log.i("HIGHWAY", AppGlobal.getInstance().getURL(getString(R.string.wasu11),getString(R.string.wasu19)));
                    Log.i("HIGHWAY", result1119);
                    result1120=HttpConnUtils.download(AppGlobal.getInstance().getURL(getString(R.string.wasu11),getString(R.string.wasu20)));
                    Log.i("HIGHWAY", AppGlobal.getInstance().getURL(getString(R.string.wasu11),getString(R.string.wasu20)));
                    Log.i("HIGHWAY", result1120);
                    result1121=HttpConnUtils.download(AppGlobal.getInstance().getURL(getString(R.string.wasu11),getString(R.string.wasu21)));
                    Log.i("HIGHWAY", AppGlobal.getInstance().getURL(getString(R.string.wasu11),getString(R.string.wasu21)));
                    Log.i("HIGHWAY", result1121);
                    result1118=HttpConnUtils.download(AppGlobal.getInstance().getURL(getString(R.string.wasu11),getString(R.string.wasu18)));
                    Log.i("HIGHWAY", AppGlobal.getInstance().getURL(getString(R.string.wasu11),getString(R.string.wasu18)));
                    Log.i("HIGHWAY", result1118);
                    result1222=HttpConnUtils.download(AppGlobal.getInstance().getURL(getString(R.string.wasu12),getString(R.string.wasu22)));
                    Log.i("HIGHWAY", AppGlobal.getInstance().getURL(getString(R.string.wasu12),getString(R.string.wasu22)));
                    Log.i("HIGHWAY", result1222);
                    result1223=HttpConnUtils.download(AppGlobal.getInstance().getURL(getString(R.string.wasu12),getString(R.string.wasu23)));
                    Log.i("HIGHWAY", AppGlobal.getInstance().getURL(getString(R.string.wasu12),getString(R.string.wasu23)));
                    Log.i("HIGHWAY", result1223);
                    result1322=HttpConnUtils.download(AppGlobal.getInstance().getURL(getString(R.string.wasu13),getString(R.string.wasu22)));
                    Log.i("HIGHWAY", AppGlobal.getInstance().getURL(getString(R.string.wasu13),getString(R.string.wasu22)));
                    Log.i("HIGHWAY", result1322);

                    /**
                     * "twoAverWs":2
                     "temper":14.1
                     "relHumi":77
                     "instantVis":7177
                     "twoAverWd":290
                     "date":"2015-04-20 15:03:00"
                     "roadId":"G15"
                     */

                    Log.i("HIGHWAY", "开始解析数据");

                    if(result1014!=null){

                        Log.i("HIGHWAY", result1014);
//                        ToastUtil.show(MainActivity.this,result1014);

                        HighWayEntity highWayEntity=new HighWayEntity();

                        JSONObject jsonObject = new JSONObject(result1014);

                        if(jsonObject.has("twoAverWs") && jsonObject.get("twoAverWs")!=null){
                            highWayEntity.setTwoAverWs(jsonObject.get("twoAverWs").toString());
                        }
                        if(jsonObject.has("temper") && jsonObject.get("temper")!=null){
                            highWayEntity.setTemper(jsonObject.get("temper").toString());
                        }
                        if(jsonObject.has("relHumi") && jsonObject.get("relHumi")!=null){
                            highWayEntity.setRelHumi(jsonObject.get("relHumi").toString());
                        }
                        if(jsonObject.has("instantVis") && jsonObject.get("instantVis")!=null){
                            highWayEntity.setInstantVis(jsonObject.get("instantVis").toString());
                        }
                        if(jsonObject.has("twoAverWd") && jsonObject.get("twoAverWd")!=null){
                            highWayEntity.setTwoAverWd(jsonObject.get("twoAverWd").toString());
                        }
                        if(jsonObject.has("roadId") && jsonObject.get("roadId")!=null){
                            highWayEntity.setRoadId(jsonObject.get("roadId").toString());
                        }

                        if(jsonObject.has("roadName") && jsonObject.get("roadName")!=null){
                            highWayEntity.setRoadName(jsonObject.get("roadName").toString());
                        }

                        if(jsonObject.has("sectionname") && jsonObject.get("sectionname")!=null){
                            highWayEntity.setSectionname(jsonObject.get("sectionname").toString());
                        }

                        JSONObject jsonArrayId = jsonObject.getJSONObject("id");

                        if(jsonArrayId.has("date") && jsonArrayId.get("date")!=null){
                            highWayEntity.setDate(jsonArrayId.get("date").toString());
                        }

                        highWayEntityList.add(highWayEntity);
                    }

                    if(result1015!=null){
                        Log.i("HIGHWAY", result1015);
//                        ToastUtil.show(MainActivity.this,result1015);

                        HighWayEntity highWayEntity=new HighWayEntity();

                        JSONObject jsonObject = new JSONObject(result1015);

                        if(jsonObject.has("twoAverWs") && jsonObject.get("twoAverWs")!=null){
                            highWayEntity.setTwoAverWs(jsonObject.get("twoAverWs").toString());
                        }
                        if(jsonObject.has("temper") && jsonObject.get("temper")!=null){
                            highWayEntity.setTemper(jsonObject.get("temper").toString());
                        }
                        if(jsonObject.has("relHumi") && jsonObject.get("relHumi")!=null){
                            highWayEntity.setRelHumi(jsonObject.get("relHumi").toString());
                        }
                        if(jsonObject.has("instantVis") && jsonObject.get("instantVis")!=null){
                            highWayEntity.setInstantVis(jsonObject.get("instantVis").toString());
                        }
                        if(jsonObject.has("twoAverWd") && jsonObject.get("twoAverWd")!=null){
                            highWayEntity.setTwoAverWd(jsonObject.get("twoAverWd").toString());
                        }
                        if(jsonObject.has("roadId") && jsonObject.get("roadId")!=null){
                            highWayEntity.setRoadId(jsonObject.get("roadId").toString());
                        }

                        if(jsonObject.has("roadName") && jsonObject.get("roadName")!=null){
                            highWayEntity.setRoadName(jsonObject.get("roadName").toString());
                        }

                        if(jsonObject.has("sectionname") && jsonObject.get("sectionname")!=null){
                            highWayEntity.setSectionname(jsonObject.get("sectionname").toString());
                        }

                        JSONObject jsonArrayId = jsonObject.getJSONObject("id");

                        if(jsonArrayId.has("date") && jsonArrayId.get("date")!=null){
                            highWayEntity.setDate(jsonArrayId.get("date").toString());
                        }

                        highWayEntityList.add(highWayEntity);
                    }

                    if(result1016!=null){
                        Log.i("HIGHWAY", result1016);
                        HighWayEntity highWayEntity=new HighWayEntity();

                        JSONObject jsonObject = new JSONObject(result1016);

                        if(jsonObject.has("twoAverWs") && jsonObject.get("twoAverWs")!=null){
                            highWayEntity.setTwoAverWs(jsonObject.get("twoAverWs").toString());
                        }
                        if(jsonObject.has("temper") && jsonObject.get("temper")!=null){
                            highWayEntity.setTemper(jsonObject.get("temper").toString());
                        }
                        if(jsonObject.has("relHumi") && jsonObject.get("relHumi")!=null){
                            highWayEntity.setRelHumi(jsonObject.get("relHumi").toString());
                        }
                        if(jsonObject.has("instantVis") && jsonObject.get("instantVis")!=null){
                            highWayEntity.setInstantVis(jsonObject.get("instantVis").toString());
                        }
                        if(jsonObject.has("twoAverWd") && jsonObject.get("twoAverWd")!=null){
                            highWayEntity.setTwoAverWd(jsonObject.get("twoAverWd").toString());
                        }
                        if(jsonObject.has("roadId") && jsonObject.get("roadId")!=null){
                            highWayEntity.setRoadId(jsonObject.get("roadId").toString());
                        }

                        if(jsonObject.has("roadName") && jsonObject.get("roadName")!=null){
                            highWayEntity.setRoadName(jsonObject.get("roadName").toString());
                        }

                        if(jsonObject.has("sectionname") && jsonObject.get("sectionname")!=null){
                            highWayEntity.setSectionname(jsonObject.get("sectionname").toString());
                        }

                        JSONObject jsonArrayId = jsonObject.getJSONObject("id");

                        if(jsonArrayId.has("date") && jsonArrayId.get("date")!=null){
                            highWayEntity.setDate(jsonArrayId.get("date").toString());
                        }

                        highWayEntityList.add(highWayEntity);

                    }

                    if(result1017!=null){
                        Log.i("HIGHWAY", result1017);

                        HighWayEntity highWayEntity=new HighWayEntity();

                        JSONObject jsonObject = new JSONObject(result1017);

                        if(jsonObject.has("twoAverWs") && jsonObject.get("twoAverWs")!=null){
                            highWayEntity.setTwoAverWs(jsonObject.get("twoAverWs").toString());
                        }
                        if(jsonObject.has("temper") && jsonObject.get("temper")!=null){
                            highWayEntity.setTemper(jsonObject.get("temper").toString());
                        }
                        if(jsonObject.has("relHumi") && jsonObject.get("relHumi")!=null){
                            highWayEntity.setRelHumi(jsonObject.get("relHumi").toString());
                        }
                        if(jsonObject.has("instantVis") && jsonObject.get("instantVis")!=null){
                            highWayEntity.setInstantVis(jsonObject.get("instantVis").toString());
                        }
                        if(jsonObject.has("twoAverWd") && jsonObject.get("twoAverWd")!=null){
                            highWayEntity.setTwoAverWd(jsonObject.get("twoAverWd").toString());
                        }
                        if(jsonObject.has("roadId") && jsonObject.get("roadId")!=null){
                            highWayEntity.setRoadId(jsonObject.get("roadId").toString());
                        }

                        if(jsonObject.has("roadName") && jsonObject.get("roadName")!=null){
                            highWayEntity.setRoadName(jsonObject.get("roadName").toString());
                        }

                        if(jsonObject.has("sectionname") && jsonObject.get("sectionname")!=null){
                            highWayEntity.setSectionname(jsonObject.get("sectionname").toString());
                        }

                        JSONObject jsonArrayId = jsonObject.getJSONObject("id");

                        if(jsonArrayId.has("date") && jsonArrayId.get("date")!=null){
                            highWayEntity.setDate(jsonArrayId.get("date").toString());
                        }

                        highWayEntityList.add(highWayEntity);

                    }

                    if(result1018!=null){
                        Log.i("HIGHWAY", result1018);
                        HighWayEntity highWayEntity=new HighWayEntity();

                        JSONObject jsonObject = new JSONObject(result1018);

                        if(jsonObject.has("twoAverWs") && jsonObject.get("twoAverWs")!=null){
                            highWayEntity.setTwoAverWs(jsonObject.get("twoAverWs").toString());
                        }
                        if(jsonObject.has("temper") && jsonObject.get("temper")!=null){
                            highWayEntity.setTemper(jsonObject.get("temper").toString());
                        }
                        if(jsonObject.has("relHumi") && jsonObject.get("relHumi")!=null){
                            highWayEntity.setRelHumi(jsonObject.get("relHumi").toString());
                        }
                        if(jsonObject.has("instantVis") && jsonObject.get("instantVis")!=null){
                            highWayEntity.setInstantVis(jsonObject.get("instantVis").toString());
                        }
                        if(jsonObject.has("twoAverWd") && jsonObject.get("twoAverWd")!=null){
                            highWayEntity.setTwoAverWd(jsonObject.get("twoAverWd").toString());
                        }
                        if(jsonObject.has("roadId") && jsonObject.get("roadId")!=null){
                            highWayEntity.setRoadId(jsonObject.get("roadId").toString());
                        }

                        if(jsonObject.has("roadName") && jsonObject.get("roadName")!=null){
                            highWayEntity.setRoadName(jsonObject.get("roadName").toString());
                        }

                        if(jsonObject.has("sectionname") && jsonObject.get("sectionname")!=null){
                            highWayEntity.setSectionname(jsonObject.get("sectionname").toString());
                        }

                        JSONObject jsonArrayId = jsonObject.getJSONObject("id");

                        if(jsonArrayId.has("date") && jsonArrayId.get("date")!=null){
                            highWayEntity.setDate(jsonArrayId.get("date").toString());
                        }

                        highWayEntityList.add(highWayEntity);

                    }

                    if(result1119!=null){
                        Log.i("HIGHWAY", result1119);
                        HighWayEntity highWayEntity=new HighWayEntity();

                        JSONObject jsonObject = new JSONObject(result1119);

                        if(jsonObject.has("twoAverWs") && jsonObject.get("twoAverWs")!=null){
                            highWayEntity.setTwoAverWs(jsonObject.get("twoAverWs").toString());
                        }
                        if(jsonObject.has("temper") && jsonObject.get("temper")!=null){
                            highWayEntity.setTemper(jsonObject.get("temper").toString());
                        }
                        if(jsonObject.has("relHumi") && jsonObject.get("relHumi")!=null){
                            highWayEntity.setRelHumi(jsonObject.get("relHumi").toString());
                        }
                        if(jsonObject.has("instantVis") && jsonObject.get("instantVis")!=null){
                            highWayEntity.setInstantVis(jsonObject.get("instantVis").toString());
                        }
                        if(jsonObject.has("twoAverWd") && jsonObject.get("twoAverWd")!=null){
                            highWayEntity.setTwoAverWd(jsonObject.get("twoAverWd").toString());
                        }
                        if(jsonObject.has("roadId") && jsonObject.get("roadId")!=null){
                            highWayEntity.setRoadId(jsonObject.get("roadId").toString());
                        }

                        if(jsonObject.has("roadName") && jsonObject.get("roadName")!=null){
                            highWayEntity.setRoadName(jsonObject.get("roadName").toString());
                        }

                        if(jsonObject.has("sectionname") && jsonObject.get("sectionname")!=null){
                            highWayEntity.setSectionname(jsonObject.get("sectionname").toString());
                        }

                        JSONObject jsonArrayId = jsonObject.getJSONObject("id");

                        if(jsonArrayId.has("date") && jsonArrayId.get("date")!=null){
                            highWayEntity.setDate(jsonArrayId.get("date").toString());
                        }

                        highWayEntityList.add(highWayEntity);

                    }

                    if(result1120!=null){
                        Log.i("HIGHWAY", result1120);
                        HighWayEntity highWayEntity=new HighWayEntity();

                        JSONObject jsonObject = new JSONObject(result1120);

                        if(jsonObject.has("twoAverWs") && jsonObject.get("twoAverWs")!=null){
                            highWayEntity.setTwoAverWs(jsonObject.get("twoAverWs").toString());
                        }
                        if(jsonObject.has("temper") && jsonObject.get("temper")!=null){
                            highWayEntity.setTemper(jsonObject.get("temper").toString());
                        }
                        if(jsonObject.has("relHumi") && jsonObject.get("relHumi")!=null){
                            highWayEntity.setRelHumi(jsonObject.get("relHumi").toString());
                        }
                        if(jsonObject.has("instantVis") && jsonObject.get("instantVis")!=null){
                            highWayEntity.setInstantVis(jsonObject.get("instantVis").toString());
                        }
                        if(jsonObject.has("twoAverWd") && jsonObject.get("twoAverWd")!=null){
                            highWayEntity.setTwoAverWd(jsonObject.get("twoAverWd").toString());
                        }
                        if(jsonObject.has("roadId") && jsonObject.get("roadId")!=null){
                            highWayEntity.setRoadId(jsonObject.get("roadId").toString());
                        }

                        if(jsonObject.has("roadName") && jsonObject.get("roadName")!=null){
                            highWayEntity.setRoadName(jsonObject.get("roadName").toString());
                        }

                        if(jsonObject.has("sectionname") && jsonObject.get("sectionname")!=null){
                            highWayEntity.setSectionname(jsonObject.get("sectionname").toString());
                        }

                        JSONObject jsonArrayId = jsonObject.getJSONObject("id");

                        if(jsonArrayId.has("date") && jsonArrayId.get("date")!=null){
                            highWayEntity.setDate(jsonArrayId.get("date").toString());
                        }

                        highWayEntityList.add(highWayEntity);

                    }

                    if(result1121!=null){
                        Log.i("HIGHWAY", result1121);
                        HighWayEntity highWayEntity=new HighWayEntity();

                        JSONObject jsonObject = new JSONObject(result1121);

                        if(jsonObject.has("twoAverWs") && jsonObject.get("twoAverWs")!=null){
                            highWayEntity.setTwoAverWs(jsonObject.get("twoAverWs").toString());
                        }
                        if(jsonObject.has("temper") && jsonObject.get("temper")!=null){
                            highWayEntity.setTemper(jsonObject.get("temper").toString());
                        }
                        if(jsonObject.has("relHumi") && jsonObject.get("relHumi")!=null){
                            highWayEntity.setRelHumi(jsonObject.get("relHumi").toString());
                        }
                        if(jsonObject.has("instantVis") && jsonObject.get("instantVis")!=null){
                            highWayEntity.setInstantVis(jsonObject.get("instantVis").toString());
                        }
                        if(jsonObject.has("twoAverWd") && jsonObject.get("twoAverWd")!=null){
                            highWayEntity.setTwoAverWd(jsonObject.get("twoAverWd").toString());
                        }
                        if(jsonObject.has("roadId") && jsonObject.get("roadId")!=null){
                            highWayEntity.setRoadId(jsonObject.get("roadId").toString());
                        }

                        if(jsonObject.has("roadName") && jsonObject.get("roadName")!=null){
                            highWayEntity.setRoadName(jsonObject.get("roadName").toString());
                        }

                        if(jsonObject.has("sectionname") && jsonObject.get("sectionname")!=null){
                            highWayEntity.setSectionname(jsonObject.get("sectionname").toString());
                        }

                        JSONObject jsonArrayId = jsonObject.getJSONObject("id");

                        if(jsonArrayId.has("date") && jsonArrayId.get("date")!=null){
                            highWayEntity.setDate(jsonArrayId.get("date").toString());
                        }

                        highWayEntityList.add(highWayEntity);

                    }

                    if(result1118!=null){
                        Log.i("HIGHWAY", result1118);
                        HighWayEntity highWayEntity=new HighWayEntity();

                        JSONObject jsonObject = new JSONObject(result1118);

                        if(jsonObject.has("twoAverWs") && jsonObject.get("twoAverWs")!=null){
                            highWayEntity.setTwoAverWs(jsonObject.get("twoAverWs").toString());
                        }
                        if(jsonObject.has("temper") && jsonObject.get("temper")!=null){
                            highWayEntity.setTemper(jsonObject.get("temper").toString());
                        }
                        if(jsonObject.has("relHumi") && jsonObject.get("relHumi")!=null){
                            highWayEntity.setRelHumi(jsonObject.get("relHumi").toString());
                        }
                        if(jsonObject.has("instantVis") && jsonObject.get("instantVis")!=null){
                            highWayEntity.setInstantVis(jsonObject.get("instantVis").toString());
                        }
                        if(jsonObject.has("twoAverWd") && jsonObject.get("twoAverWd")!=null){
                            highWayEntity.setTwoAverWd(jsonObject.get("twoAverWd").toString());
                        }
                        if(jsonObject.has("roadId") && jsonObject.get("roadId")!=null){
                            highWayEntity.setRoadId(jsonObject.get("roadId").toString());
                        }

                        if(jsonObject.has("roadName") && jsonObject.get("roadName")!=null){
                            highWayEntity.setRoadName(jsonObject.get("roadName").toString());
                        }

                        if(jsonObject.has("sectionname") && jsonObject.get("sectionname")!=null){
                            highWayEntity.setSectionname(jsonObject.get("sectionname").toString());
                        }

                        JSONObject jsonArrayId = jsonObject.getJSONObject("id");

                        if(jsonArrayId.has("date") && jsonArrayId.get("date")!=null){
                            highWayEntity.setDate(jsonArrayId.get("date").toString());
                        }

                        highWayEntityList.add(highWayEntity);

                    }

                    if(result1222!=null){
                        Log.i("HIGHWAY", result1222);
                        HighWayEntity highWayEntity=new HighWayEntity();

                        JSONObject jsonObject = new JSONObject(result1222);

                        if(jsonObject.has("twoAverWs") && jsonObject.get("twoAverWs")!=null){
                            highWayEntity.setTwoAverWs(jsonObject.get("twoAverWs").toString());
                        }
                        if(jsonObject.has("temper") && jsonObject.get("temper")!=null){
                            highWayEntity.setTemper(jsonObject.get("temper").toString());
                        }
                        if(jsonObject.has("relHumi") && jsonObject.get("relHumi")!=null){
                            highWayEntity.setRelHumi(jsonObject.get("relHumi").toString());
                        }
                        if(jsonObject.has("instantVis") && jsonObject.get("instantVis")!=null){
                            highWayEntity.setInstantVis(jsonObject.get("instantVis").toString());
                        }
                        if(jsonObject.has("twoAverWd") && jsonObject.get("twoAverWd")!=null){
                            highWayEntity.setTwoAverWd(jsonObject.get("twoAverWd").toString());
                        }
                        if(jsonObject.has("roadId") && jsonObject.get("roadId")!=null){
                            highWayEntity.setRoadId(jsonObject.get("roadId").toString());
                        }

                        if(jsonObject.has("roadName") && jsonObject.get("roadName")!=null){
                            highWayEntity.setRoadName(jsonObject.get("roadName").toString());
                        }

                        if(jsonObject.has("sectionname") && jsonObject.get("sectionname")!=null){
                            highWayEntity.setSectionname(jsonObject.get("sectionname").toString());
                        }

                        JSONObject jsonArrayId = jsonObject.getJSONObject("id");

                        if(jsonArrayId.has("date") && jsonArrayId.get("date")!=null){
                            highWayEntity.setDate(jsonArrayId.get("date").toString());
                        }

                        highWayEntityList.add(highWayEntity);

                    }

                    if(result1223!=null){
                        Log.i("HIGHWAY", result1223);
                        HighWayEntity highWayEntity=new HighWayEntity();

                        JSONObject jsonObject = new JSONObject(result1223);

                        if(jsonObject.has("twoAverWs") && jsonObject.get("twoAverWs")!=null){
                            highWayEntity.setTwoAverWs(jsonObject.get("twoAverWs").toString());
                        }
                        if(jsonObject.has("temper") && jsonObject.get("temper")!=null){
                            highWayEntity.setTemper(jsonObject.get("temper").toString());
                        }
                        if(jsonObject.has("relHumi") && jsonObject.get("relHumi")!=null){
                            highWayEntity.setRelHumi(jsonObject.get("relHumi").toString());
                        }
                        if(jsonObject.has("instantVis") && jsonObject.get("instantVis")!=null){
                            highWayEntity.setInstantVis(jsonObject.get("instantVis").toString());
                        }
                        if(jsonObject.has("twoAverWd") && jsonObject.get("twoAverWd")!=null){
                            highWayEntity.setTwoAverWd(jsonObject.get("twoAverWd").toString());
                        }
                        if(jsonObject.has("roadId") && jsonObject.get("roadId")!=null){
                            highWayEntity.setRoadId(jsonObject.get("roadId").toString());
                        }
                        if(jsonObject.has("roadName") && jsonObject.get("roadName")!=null){
                            highWayEntity.setRoadName(jsonObject.get("roadName").toString());
                        }

                        if(jsonObject.has("sectionname") && jsonObject.get("sectionname")!=null){
                            highWayEntity.setSectionname(jsonObject.get("sectionname").toString());
                        }


                        JSONObject jsonArrayId = jsonObject.getJSONObject("id");

                        if(jsonArrayId.has("date") && jsonArrayId.get("date")!=null){
                            highWayEntity.setDate(jsonArrayId.get("date").toString());
                        }

                        highWayEntityList.add(highWayEntity);

                    }

                    if(result1322!=null){
                        Log.i("HIGHWAY", result1322);
                        HighWayEntity highWayEntity=new HighWayEntity();

                        JSONObject jsonObject = new JSONObject(result1322);

                        if(jsonObject.has("twoAverWs") && jsonObject.get("twoAverWs")!=null){
                            highWayEntity.setTwoAverWs(jsonObject.get("twoAverWs").toString());
                        }
                        if(jsonObject.has("temper") && jsonObject.get("temper")!=null){
                            highWayEntity.setTemper(jsonObject.get("temper").toString());
                        }
                        if(jsonObject.has("relHumi") && jsonObject.get("relHumi")!=null){
                            highWayEntity.setRelHumi(jsonObject.get("relHumi").toString());
                        }
                        if(jsonObject.has("instantVis") && jsonObject.get("instantVis")!=null){
                            highWayEntity.setInstantVis(jsonObject.get("instantVis").toString());
                        }
                        if(jsonObject.has("twoAverWd") && jsonObject.get("twoAverWd")!=null){
                            highWayEntity.setTwoAverWd(jsonObject.get("twoAverWd").toString());
                        }
                        if(jsonObject.has("roadId") && jsonObject.get("roadId")!=null){
                            highWayEntity.setRoadId(jsonObject.get("roadId").toString());
                        }

                        if(jsonObject.has("roadName") && jsonObject.get("roadName")!=null){
                            highWayEntity.setRoadName(jsonObject.get("roadName").toString());
                        }

                        if(jsonObject.has("sectionname") && jsonObject.get("sectionname")!=null){
                            highWayEntity.setSectionname(jsonObject.get("sectionname").toString());
                        }

                        JSONObject jsonArrayId = jsonObject.getJSONObject("id");

                        if(jsonArrayId.has("date") && jsonArrayId.get("date")!=null){
                            highWayEntity.setDate(jsonArrayId.get("date").toString());
                        }

                        highWayEntityList.add(highWayEntity);

                    }

                    AppGlobal.getInstance().setHighWayEntityList(null);
                    AppGlobal.getInstance().setHighWayEntityList(highWayEntityList);
                    Log.i("HIGHWAY", "解析数据完成");
                    Log.i("HIGHWAY", highWayEntityList.toString());

                   // Log.i("HIGHWAY", result);
                } else {
                    msghighway=mHandler.obtainMessage();
                    msghighway.what = NONETWORK;
                    mHandler.sendMessage(msghighway);
                }

                if (result != null) {
                    return result;
                }
            }catch (TimeoutController.TimeoutException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
                Log.i("HIGHWAY", e.toString());
                Log.i("HIGHWAY", "JSON解析错误！");
            }

            return null;
        }
    }


    //获取气象数据
    public class GetWeatherDataAsyncTask extends AsyncTask<String, Integer, String> {

        private String url;
        Message msgweather;

        public GetWeatherDataAsyncTask(String url) {
            this.url = url;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressdialog.dismiss();
            msgweather = mHandler.obtainMessage();
            msgweather.what=COMPLETE;
            mHandler.sendMessage(msgweather);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {

            String result = null;

            try {
                if (NetUtils.isNetworkConnected(MainActivity.this) ||
                        NetUtils.isWifiConnected(MainActivity.this)) {
                    result = HttpConnUtils.download(url);
                    Log.i("WEATHER",result);
                } else {
                    msgweather = mHandler.obtainMessage();
                    msgweather.what = NONETWORK;
                    mHandler.sendMessage(msgweather);
                }

                if (result != null) {

                    weatherEntityList=new ArrayList<>();
                    indexEntityList=new ArrayList<>();
                    ybTodayEntityList=new ArrayList<>();
                    ybfsEntityList=new ArrayList<>();

                    if (result.startsWith("?")) {
                        result = result.trim().replaceAll("[\\t\\n\\r]", "").substring(1);
                    } else {
                        result = result.trim().replaceAll("[\\t\\n\\r]", "");
                    }

                    JSONObject jsonObject = new JSONObject(result);
                    /**
                     *
                     * "datestr":"星期二 04月07日",
                     "nongli":"二月十九",
                     */

                    if(jsonObject.has("datestr") && jsonObject.get("datestr")!=null){
                        dataStr=jsonObject.get("datestr").toString();
                        AppGlobal.getInstance().setDataStr(dataStr);
                    }

                    if(jsonObject.has("nongli") && jsonObject.get("nongli")!=null){
                        oldDateStr=jsonObject.get("nongli").toString();
                        AppGlobal.getInstance().setOldDateStr(oldDateStr);
                    }



                    if(jsonObject.has("shikuang") && jsonObject.get("shikuang")!=null){

                        JSONObject jsonArrayshikuang = jsonObject.getJSONObject("shikuang");

                        ShiKuangEntity shiKuangEntity = new ShiKuangEntity();

                        if(jsonArrayshikuang.has("time") && jsonArrayshikuang.get("time")!=null){
                            shiKuangEntity.setTime(jsonArrayshikuang.get("time").toString());
                        }

                        if(jsonArrayshikuang.has("temp") && jsonArrayshikuang.get("temp")!=null){
                            shiKuangEntity.setTemp(jsonArrayshikuang.get("temp").toString());
                        }

                        if(jsonArrayshikuang.has("sd") && jsonArrayshikuang.get("sd")!=null){
                            shiKuangEntity.setSd(jsonArrayshikuang.get("sd").toString());
                        }
                        if(jsonArrayshikuang.has("cityname") && jsonArrayshikuang.get("cityname")!=null){
                            shiKuangEntity.setCityname(jsonArrayshikuang.get("cityname").toString());
                        }
                        if(jsonArrayshikuang.has("nameen") && jsonArrayshikuang.get("nameen")!=null){
                            shiKuangEntity.setNameen(jsonArrayshikuang.get("nameen").toString());
                        }
                        if(jsonArrayshikuang.has("city") && jsonArrayshikuang.get("city")!=null){
                            shiKuangEntity.setCity(jsonArrayshikuang.get("city").toString());
                            AppGlobal.getInstance().setCitycode(jsonArrayshikuang.get("city").toString());
                        }

                        if(jsonArrayshikuang.has("aqi") && jsonArrayshikuang.get("aqi")!=null){
                            shiKuangEntity.setAqi(jsonArrayshikuang.get("aqi").toString());
                            AppGlobal.getInstance().setAqiStr(jsonArrayshikuang.get("aqi").toString());
                        }

                        if(jsonArrayshikuang.has("qy") && jsonArrayshikuang.get("qy")!=null){
                            shiKuangEntity.setQy(jsonArrayshikuang.get("qy").toString());
                        }

                        AppGlobal.getInstance().setShiKuangEntity(null);
                        AppGlobal.getInstance().setShiKuangEntity(shiKuangEntity);

                    }



                    if(jsonObject.has("ybtoday")){

                        //当天预报数组
                        JSONArray jsonArrybtoday = jsonObject.getJSONArray("ybtoday");
                        /**
                         * {"sj":"7日 周二","dn":"夜间","wea":"小雨","tem":"6°C","win":"北风3-4级",
                         * "sunUp":"日落 18:20","sunDown":"日落 18:20"}
                         */

                        for (int i = 0; i < jsonArrybtoday.length(); i++) {

                            JSONObject jsonArrayybtoday = (JSONObject) jsonArrybtoday.get(i);
                            YBTodayEntity ybTodayEntity=new YBTodayEntity();

                            if(jsonArrayybtoday.has("sj") && jsonArrayybtoday.get("sj")!=null){
                                ybTodayEntity.setSj(jsonArrayybtoday.get("sj").toString());
                            }

                            if(jsonArrayybtoday.has("dn") && jsonArrayybtoday.get("dn")!=null){
                                ybTodayEntity.setDn(jsonArrayybtoday.get("dn").toString());
                            }

                            if(jsonArrayybtoday.has("wea") && jsonArrayybtoday.get("wea")!=null){
                                ybTodayEntity.setWea(jsonArrayybtoday.get("wea").toString());
                            }

                            if(jsonArrayybtoday.has("tem") && jsonArrayybtoday.get("tem")!=null){
                                ybTodayEntity.setTem(jsonArrayybtoday.get("tem").toString());
                            }
                            if(jsonArrayybtoday.has("win") && jsonArrayybtoday.get("win")!=null){
                                ybTodayEntity.setWin(jsonArrayybtoday.get("win").toString());
                            }
                            if(jsonArrayybtoday.has("sunUp") && jsonArrayybtoday.get("sunUp")!=null){
                                ybTodayEntity.setSunUp(jsonArrayybtoday.get("sunUp").toString());
                            }
                            if(jsonArrayybtoday.has("sunDown") && jsonArrayybtoday.get("sunDown")!=null){
                                ybTodayEntity.setSunDown(jsonArrayybtoday.get("sunDown").toString());
                            }

                            ybTodayEntityList.add(ybTodayEntity);
                        }

                        AppGlobal.getInstance().setYbTodayEntityList(null);
                        AppGlobal.getInstance().setYbTodayEntityList(ybTodayEntityList);

                    }


                    if(jsonObject.has("yb")){
                        JSONArray jsonArrayyb = jsonObject.getJSONArray("yb");
                        if(jsonArrayyb!=null && jsonArrayyb.length()>0){
                            for (int i = 0; i < jsonArrayyb.length(); i++) {
                                JSONObject o = (JSONObject) jsonArrayyb.get(i);
                                WeatherEntity weatherEntity=new WeatherEntity();
                                if (o.has("sj") && o.getString("sj") != null) {
                                    weatherEntity.setSj(o.getString("sj"));
                                }
                                if (o.has("dn") && o.getString("dn") != null) {
                                    weatherEntity.setDn(o.getString("dn"));
                                }
                                if (o.has("wea") && o.getString("wea") != null) {
                                    weatherEntity.setWea(o.getString("wea"));
                                }
                                if (o.has("tem1") && o.getString("tem1") != null) {
                                    weatherEntity.setTem1(o.getString("tem1"));
                                }
                                if (o.has("img1") && o.getString("img1") != null) {
                                    weatherEntity.setImg1(o.getString("img1"));
                                }
                                if (o.has("img2") && o.getString("img2") != null) {
                                    weatherEntity.setImg2(o.getString("img2"));
                                }
                                if (o.has("tem2") && o.getString("tem2") != null) {
                                    weatherEntity.setTem2(o.getString("tem2"));
                                }
                                if (o.has("win") && o.getString("win") != null) {
                                    weatherEntity.setWin(o.getString("win"));
                                }
                                if (o.has("sunUp") && o.getString("sunUp") != null) {
                                    weatherEntity.setSunUp(o.getString("sunUp"));
                                }
                                if (o.has("sunDown") && o.getString("sunDown") != null) {
                                    weatherEntity.setSunDown(o.getString("sunDown"));
                                }

                                if(weatherEntity.getSj()!=null && weatherEntity.getSj().equals("今天")){
                                    AppGlobal.getInstance().setImgStr(weatherEntity.getImg1());
                                    AppGlobal.getInstance().setWeather(weatherEntity.getWea());
                                }

                                weatherEntityList.add(weatherEntity);
                            }

                            AppGlobal.getInstance().setWeatherEntityList(null);
                            AppGlobal.getInstance().setWeatherEntityList(weatherEntityList);

                        }
                    }


                    //指数
                    if(jsonObject.has("zhishu")){
                        JSONArray jsonArrayzhishu = jsonObject.getJSONArray("zhishu");
                        if(jsonArrayzhishu!=null && jsonArrayzhishu.length()>0){
                            for (int i = 0; i < jsonArrayzhishu.length(); i++) {
                                JSONObject obj = (JSONObject) jsonArrayzhishu.get(i);
                                IndexEntity indexEntity=new IndexEntity();
                                /**
                                 *  private String name;
                                 private String state;
                                 private String jy;
                                 private String gxtime;
                                 */
                                if (obj.has("name") && obj.getString("name") != null) {
                                    indexEntity.setName(obj.getString("name"));
                                }
                                if (obj.has("state") && obj.getString("state") != null) {
                                    indexEntity.setState(obj.getString("state"));
                                }
                                if (obj.has("jy") && obj.getString("jy") != null) {
                                    indexEntity.setJy(obj.getString("jy"));
                                }
                                if (obj.has("gxtime") && obj.getString("gxtime") != null) {
                                    indexEntity.setGxtime(obj.getString("gxtime"));
                                }

                                indexEntityList.add(indexEntity);
                            }

                            AppGlobal.getInstance().setIndexEntityList(null);
                            AppGlobal.getInstance().setIndexEntityList(indexEntityList);

                        }

                    }


                    //分时预报
                    if(jsonObject.has("ybfs")){
                        JSONArray jsonArrayybfs = jsonObject.getJSONArray("ybfs");

                        if(jsonArrayybfs!=null && jsonArrayybfs.length()>0){
                            for (int i = 0; i < jsonArrayybfs.length(); i++) {
                                JSONObject obj = (JSONObject) jsonArrayybfs.get(i);
                                YBFSEntity ybfsEntity=new YBFSEntity();
                                /**
                                 *   private String time;
                                 private String temp;
                                 {"time":"08时-11时  ","temper":"18℃/14℃"}
                                 */
                                if (obj.has("time") && obj.getString("time") != null) {
                                    ybfsEntity.setTime(obj.getString("time"));
                                }
                                if (obj.has("temper") && obj.getString("temper") != null) {
                                    ybfsEntity.setTemp(obj.getString("temper"));
                                }
                                ybfsEntityList.add(ybfsEntity);
                            }
                            AppGlobal.getInstance().setYbfsEntityList(null);
                            AppGlobal.getInstance().setYbfsEntityList(ybfsEntityList);
                        }
                    }


                }

                return null;
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (TimeoutController.TimeoutException e) {
                e.printStackTrace();
            }

            return null;
        }
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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (!isExits) {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                isExits = true;
                timer = new Timer();
                timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        if (isExits) {
                            isExits = false;
                        }
                    }
                };
                timer.schedule(timerTask, 3000);
            } else {
                this.finish();
            }
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_wasu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
