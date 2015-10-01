package com.lbs.weatherdemo.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lbs.weatherdemo.R;
import com.lbs.weatherdemo.activity.entity.IndexEntity;
import com.lbs.weatherdemo.activity.entity.ShiKuangEntity;
import com.lbs.weatherdemo.activity.entity.WeatherEntity;
import com.lbs.weatherdemo.activity.entity.YBTodayEntity;
import com.lbs.weatherdemo.activity.util.AppGlobal;
import com.lbs.weatherdemo.activity.util.DataUtil;
import com.lbs.weatherdemo.activity.util.HttpConnUtils;
import com.lbs.weatherdemo.activity.util.NetUtils;

import org.apache.commons.httpclient.util.TimeoutController;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mars on 2015/3/18.
 */
public class MainOldActivity extends Activity implements View.OnClickListener {

    private TextView txtview01=null;
    private TextView txtview02=null;
    private TextView txtview03=null;

    private static final int COMPLETE = 1;
    private static final int NONETWORK = 2;
    private static final int TIMEOUT = 3;

    private static final String TAG="WEATHER";

    // 再按一次退出程序
    private boolean isExits = false;

    //left_top
    private ImageView left_weather_logo_img=null;
    private TextView left_temp_txtview=null;
    private TextView left_weather_txtview=null;
    private TextView left_wind_txtview=null;

    //right_top
    private TextView txtviewProvince=null;
    private TextView txtviewCity=null;
    private Spinner spinnerCityZone=null;
    private ArrayAdapter<CharSequence> mArrayAdapter=null;

    private TextView txtviewWeek=null;
    private TextView txtviewDate=null;
    private TextView txtviewOldDate=null;
    private TextView txtviewCurrTemp=null;

    //right_middle
    private TextView txtview_data1=null;
    private ImageView img_weather1=null;
    private TextView txt_temp01=null;
    private TextView txt_weather01=null;
    private TextView txt_wind01=null;

    private TextView txtview_data2=null;
    private ImageView img_weather2=null;
    private TextView txt_temp02=null;
    private TextView txt_weather02=null;
    private TextView txt_wind02=null;

    private TextView txtview_data3=null;
    private ImageView img_weather3=null;
    private TextView txt_temp03=null;
    private TextView txt_weather03=null;
    private TextView txt_wind03=null;

    private TextView txtview_data4=null;
    private ImageView img_weather4=null;
    private TextView txt_temp04=null;
    private TextView txt_weather04=null;
    private TextView txt_wind04=null;

    private TextView txtview_data5=null;
    private ImageView img_weather5=null;
    private TextView txt_temp05=null;
    private TextView txt_weather05=null;
    private TextView txt_wind05=null;

    private TextView txtview_data6=null;
    private ImageView img_weather6=null;
    private TextView txt_temp06=null;
    private TextView txt_weather06=null;
    private TextView txt_wind06=null;

    private TextView index_chuanyi=null;
    private TextView index_xiyi=null;
    private TextView index_ganmao=null;
    private TextView index_shushi=null;
    private TextView index_yundong=null;
    private TextView index_xiche=null;
    private TextView index_huoxian=null;
    private TextView index_yujing=null;

    private ImageView iv_chuanyi=null;
    private ImageView iv_xiyi=null;
    private ImageView iv_ganmao=null;
    private ImageView iv_shushi=null;
    private ImageView iv_yundong=null;
    private ImageView iv_xiche=null;
    private ImageView iv_huoxian=null;
    private ImageView iv_yujing=null;

    private TextView txtviewRise=null;
    private TextView txtviewDown=null;

    //预报
    List<WeatherEntity> weatherEntityList=null;
    //指数
    List<IndexEntity> indexEntityList=null;
    //日出日落
    List<YBTodayEntity> ybTodayEntityList=null;
    private String dataStr;
    private String oldDateStr;

    private Timer timer;
    private TimerTask timerTask;

    private Handler mHandler=null;

    ProgressDialog progressdialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);

        txtview01= (TextView) findViewById(R.id.txtview01);
        txtview02= (TextView) findViewById(R.id.txtview02);
        txtview03= (TextView) findViewById(R.id.txtview03);

        txtviewRise= (TextView) findViewById(R.id.txtviewRise);
        txtviewDown= (TextView) findViewById(R.id.txtviewDown);
        spinnerCityZone= (Spinner) findViewById(R.id.spinnerCityZone);


        left_weather_logo_img= (ImageView) findViewById(R.id.left_weather_logo_img);
        left_temp_txtview= (TextView) findViewById(R.id.left_temp_txtview);
        left_wind_txtview= (TextView) findViewById(R.id.left_wind_txtview);
        left_weather_txtview= (TextView) findViewById(R.id.left_weather_txtview);

        txtviewProvince= (TextView) findViewById(R.id.txtviewProvince);
        txtviewCity= (TextView) findViewById(R.id.txtviewCity);

        txtviewWeek= (TextView) findViewById(R.id.txtviewWeek);
        txtviewDate= (TextView) findViewById(R.id.txtviewDate);
        txtviewOldDate= (TextView) findViewById(R.id.txtviewOldDate);
        txtviewCurrTemp= (TextView) findViewById(R.id.txtviewCurrTemp);

        txtview_data1= (TextView) findViewById(R.id.txtview_data1);
        img_weather1= (ImageView) findViewById(R.id.img_weather1);
        txt_temp01= (TextView) findViewById(R.id.txt_temp01);
        txt_weather01= (TextView) findViewById(R.id.txt_weather01);
        txt_wind01= (TextView) findViewById(R.id.txt_wind01);

        txtview_data2= (TextView) findViewById(R.id.txtview_data2);
        img_weather2= (ImageView) findViewById(R.id.img_weather2);
        txt_temp02= (TextView) findViewById(R.id.txt_temp02);
        txt_weather02= (TextView) findViewById(R.id.txt_weather02);
        txt_wind02= (TextView) findViewById(R.id.txt_wind02);

        txtview_data3= (TextView) findViewById(R.id.txtview_data3);
        img_weather3= (ImageView) findViewById(R.id.img_weather3);
        txt_temp03=(TextView) findViewById(R.id.txt_temp03);
        txt_weather03= (TextView) findViewById(R.id.txt_weather03);
        txt_wind03= (TextView) findViewById(R.id.txt_wind03);

        txtview_data4= (TextView) findViewById(R.id.txtview_data4);
        img_weather4= (ImageView) findViewById(R.id.img_weather4);
        txt_temp04= (TextView) findViewById(R.id.txt_temp04);
        txt_weather04= (TextView) findViewById(R.id.txt_weather04);
        txt_wind04= (TextView) findViewById(R.id.txt_wind04);

        txtview_data5= (TextView) findViewById(R.id.txtview_data5);
        img_weather5= (ImageView) findViewById(R.id.img_weather5);
        txt_temp05= (TextView) findViewById(R.id.txt_temp05);
        txt_weather05= (TextView) findViewById(R.id.txt_weather05);
        txt_wind05= (TextView) findViewById(R.id.txt_wind05);

        txtview_data6= (TextView) findViewById(R.id.txtview_data6);
        img_weather6= (ImageView) findViewById(R.id.img_weather6);
        txt_temp06= (TextView) findViewById(R.id.txt_temp06);
        txt_weather06= (TextView) findViewById(R.id.txt_weather06);
        txt_wind06= (TextView) findViewById(R.id.txt_wind06);

        //指数
        index_chuanyi= (TextView) findViewById(R.id.index_chuanyi);
        index_xiyi= (TextView) findViewById(R.id.index_xiyi);
        index_ganmao= (TextView) findViewById(R.id.index_ganmao);
        index_shushi= (TextView) findViewById(R.id.index_shushi);
        index_yundong= (TextView) findViewById(R.id.index_yundong);
        index_xiche= (TextView) findViewById(R.id.index_xiche);
        index_huoxian= (TextView) findViewById(R.id.index_huoxian);
        index_yujing= (TextView) findViewById(R.id.index_yujing);

        iv_chuanyi= (ImageView) findViewById(R.id.iv_chuanyi);
        iv_xiyi=(ImageView) findViewById(R.id.iv_xiyi);
        iv_ganmao=(ImageView) findViewById(R.id.iv_ganmao);
        iv_shushi=(ImageView) findViewById(R.id.iv_shushi);
        iv_yundong=(ImageView) findViewById(R.id.iv_yundong);
        iv_xiche=(ImageView) findViewById(R.id.iv_xiche);
        iv_huoxian=(ImageView) findViewById(R.id.iv_huoxian);
        iv_yujing=(ImageView) findViewById(R.id.iv_yujing);

        iv_chuanyi.setFocusable(true);
        iv_xiyi.setFocusable(true);
        iv_ganmao.setFocusable(true);
        iv_shushi.setFocusable(true);
        iv_yundong.setFocusable(true);
        iv_xiche.setFocusable(true);
        iv_huoxian.setFocusable(true);
        iv_yujing.setFocusable(true);

        iv_chuanyi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){

                }else{

                }

            }
        });

        mHandler = new Handler() {

            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case COMPLETE:
                        Toast.makeText(MainOldActivity.this, "更新完成",Toast.LENGTH_SHORT).show();
                        Log.i("WEATHER","更新完成");
                        indexEntityList=AppGlobal.getInstance().getIndexEntityList();

                        if(indexEntityList!=null && indexEntityList.size()>0){
                                index_yujing.setText("无");
                            for(IndexEntity ie:indexEntityList){
                                if(ie.getName().equals("穿衣指数")){
                                    index_chuanyi.setText(ie.getState());
                                }
                                if(ie.getName().equals("感冒指数")){
                                    index_ganmao.setText(ie.getState());
                                }
                                if(ie.getName().equals("舒适度指数")){
                                    index_shushi.setText(ie.getState());
                                }
                                if(ie.getName().equals("运动指数")){
                                    index_yundong.setText(ie.getState());
                                }
                                if(ie.getName().equals("洗车指数")){
                                    index_xiche.setText(ie.getState());
                                }
                                if(ie.getName().equals("洗车指数")){
                                    index_xiyi.setText(ie.getState());
                                }
                                if(ie.getName().equals("紫外线指数")){
                                    index_huoxian.setText(ie.getState());
                                }
                            }
                        }

                        txtviewWeek.setText(AppGlobal.getInstance().getDataStr().split(" ")[0]);
                        txtviewDate.setText(AppGlobal.getInstance().getDataStr().split(" ")[1]);
                        txtviewOldDate.setText("农历 "+AppGlobal.getInstance().getOldDateStr());
                        txtviewCurrTemp.setText(AppGlobal.getInstance().getShiKuangEntity().getTemp());

                        ybTodayEntityList=AppGlobal.getInstance().getYbTodayEntityList();
                        if(ybTodayEntityList!=null && ybTodayEntityList.size()>0){
                            for(YBTodayEntity yb:ybTodayEntityList){
                                if(yb.getDn().equals("白天")){
                                    txtviewRise.setText(yb.getSunUp());
                                }else if(yb.getDn().equals("夜间")){
                                    txtviewDown.setText(yb.getSunDown());
                                }
                            }
                        }

                        weatherEntityList=AppGlobal.getInstance().getWeatherEntityList();

                        if(weatherEntityList!=null && weatherEntityList.size()>0){

                            left_temp_txtview.setText(weatherEntityList.get(0).getTem2()+"~"+weatherEntityList.get(0).getTem1());
                            left_weather_txtview.setText(weatherEntityList.get(0).getWea());
                            left_wind_txtview.setText(weatherEntityList.get(0).getWin());
                            left_weather_logo_img.setBackground(getImage(weatherEntityList.get(0).getImg1().trim()));

                            txt_temp01.setText(weatherEntityList.get(1).getTem2()+"~"+weatherEntityList.get(1).getTem1());
                            txt_weather01.setText(weatherEntityList.get(1).getWea());
                            txt_wind01.setText(weatherEntityList.get(1).getWin());
                            txtview_data1.setText(weatherEntityList.get(1).getSj()+" "+weatherEntityList.get(1).getDn());
                            img_weather1.setBackground(getImage(weatherEntityList.get(1).getImg1().trim()));

                            txt_temp02.setText(weatherEntityList.get(2).getTem2()+"~"+weatherEntityList.get(2).getTem1());
                            txt_weather02.setText(weatherEntityList.get(2).getWea());
                            txt_wind02.setText(weatherEntityList.get(2).getWin());
                            txtview_data2.setText(weatherEntityList.get(2).getSj()+" "+weatherEntityList.get(2).getDn());
                            img_weather2.setBackground(getImage(weatherEntityList.get(2).getImg1().trim()));

                            txt_temp03.setText(weatherEntityList.get(3).getTem2()+"~"+weatherEntityList.get(3).getTem1());
                            txt_weather03.setText(weatherEntityList.get(3).getWea());
                            txt_wind03.setText(weatherEntityList.get(3).getWin());
                            txtview_data3.setText(weatherEntityList.get(3).getSj()+" "+weatherEntityList.get(3).getDn());
                            img_weather3.setBackground(getImage(weatherEntityList.get(3).getImg1().trim()));

                            txt_temp04.setText(weatherEntityList.get(4).getTem2()+"~"+weatherEntityList.get(4).getTem1());
                            txt_weather04.setText(weatherEntityList.get(4).getWea());
                            txt_wind04.setText(weatherEntityList.get(4).getWin());
                            txtview_data4.setText(weatherEntityList.get(4).getSj()+" "+weatherEntityList.get(4).getDn());
                            img_weather4.setBackground(getImage(weatherEntityList.get(4).getImg1().trim()));

                            txt_temp05.setText(weatherEntityList.get(5).getTem2()+"~"+weatherEntityList.get(5).getTem1());
                            txt_weather05.setText(weatherEntityList.get(5).getWea());
                            txt_wind05.setText(weatherEntityList.get(5).getWin());
                            txtview_data5.setText(weatherEntityList.get(5).getSj()+" "+weatherEntityList.get(5).getDn());
                            img_weather5.setBackground(getImage(weatherEntityList.get(5).getImg1().trim()));

                            txt_temp06.setText(weatherEntityList.get(6).getTem2()+"~"+weatherEntityList.get(6).getTem1());
                            txt_weather06.setText(weatherEntityList.get(6).getWea());
                            txt_wind06.setText(weatherEntityList.get(6).getWin());
                            txtview_data6.setText(weatherEntityList.get(6).getSj()+" "+weatherEntityList.get(6).getDn());
                            img_weather6.setBackground(getImage(weatherEntityList.get(6).getImg1().trim()));
                        }
                        break;
                    case NONETWORK:
                        Toast.makeText(MainOldActivity.this, "请检查网络连接情况",Toast.LENGTH_SHORT).show();
                        break;
                    case TIMEOUT:
                        Toast.makeText(MainOldActivity.this, "连接服务器超时",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            };
        };

//        left_temp_txtview= (TextView) findViewById(R.id.left_temp_txtview);
//        Typeface fontFace = Typeface.createFromAsset(this.getAssets(),"fonts/BlairMdITC TT Medium.ttf");
//        left_temp_txtview.setTypeface(fontFace);

        txtview01.setFocusable(true);
        txtview02.setFocusable(true);
        txtview03.setFocusable(true);

        txtview01.setOnClickListener(this);
        txtview02.setOnClickListener(this);
        txtview03.setOnClickListener(this);

        spinnerCityZone.setFocusable(true);

        //定义添加适配器：
        mArrayAdapter = ArrayAdapter.createFromResource(this, R.array.cityZoneSXArray, R.layout.spinner_style);
        mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCityZone.setAdapter(mArrayAdapter);
        /**
         <item>越城区</item>
         <item>柯桥区</item>
         <item>上虞区</item>
         <item>诸暨市</item>
         <item>嵊州市</item>
         <item>新昌县</item>
         */

        //响应下拉框的选中值发生变化的事件处理：
        spinnerCityZone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String cityZone=getResources().getStringArray(R.array.cityZoneSXArray)[position];
                String mUrl=getWeatherUrl("绍兴市",cityZone);
                GetWeatherDataAsyncTask getWeatherDataAsyncTask=new GetWeatherDataAsyncTask(mUrl);
                Log.i("WEATHER",AppGlobal.URL+"   "+new Date().toString());
                getWeatherDataAsyncTask.execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        txtview01.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    txtview01.setBackground(null);
                    txtview01.setBackground(getResources().getDrawable(R.drawable.btn5));
                    txtview01.setTextColor(getResources().getColor(R.color.ziti0));
                    txtview03.setBackground(getResources().getDrawable(R.drawable.btn4));
                    txtview02.setBackground(getResources().getDrawable(R.drawable.btn4));

                }else{
                    txtview01.setBackground(null);
                    txtview01.setBackground(getResources().getDrawable(R.drawable.btn4));
                    txtview01.setTextColor(getResources().getColor(R.color.ziti4));
                }

            }
        });

        txtview02.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    txtview02.setBackground(null);
                    txtview02.setBackground(getResources().getDrawable(R.drawable.btn5));
                    txtview02.setTextColor(getResources().getColor(R.color.ziti0));
                    txtview01.setBackground(getResources().getDrawable(R.drawable.btn4));
                    txtview03.setBackground(getResources().getDrawable(R.drawable.btn4));
                }else{
                    txtview02.setBackground(null);
                    txtview02.setBackground(getResources().getDrawable(R.drawable.btn4));
                    txtview02.setTextColor(getResources().getColor(R.color.ziti4));
                }

            }
        });

        txtview03.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    txtview03.setBackground(null);
                    txtview03.setBackground(getResources().getDrawable(R.drawable.btn5));
                    txtview03.setTextColor(getResources().getColor(R.color.ziti0));
                    txtview01.setBackground(getResources().getDrawable(R.drawable.btn4));
                    txtview02.setBackground(getResources().getDrawable(R.drawable.btn4));

                }else{
                    txtview03.setBackground(null);
                    txtview03.setBackground(getResources().getDrawable(R.drawable.btn4));
                    txtview03.setTextColor(getResources().getColor(R.color.ziti4));
                }
            }
        });

        Log.i("WEATHER","应用创建");

        getWidthHeight();
    }

    private void refreshActivity(){
        this.finish();
        Intent intent=new Intent(MainOldActivity.this,MainOldActivity.class);
        startActivity(intent);
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

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i("WEATHER","应用出现");

        progressdialog=new ProgressDialog(MainOldActivity.this);
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

                if(NetUtils.isNetworkConnected(MainOldActivity.this) ||
                        NetUtils.isWifiConnected(MainOldActivity.this)){

                    GetWeatherDataAsyncTask getWeatherDataAsyncTask=new GetWeatherDataAsyncTask(AppGlobal.URL);
                    Log.i("WEATHER",AppGlobal.URL+"   "+new Date().toString());
                    getWeatherDataAsyncTask.execute();

                }else{

                    left_weather_logo_img.setBackground(getResources().getDrawable(R.drawable.d00));
                    left_temp_txtview.setText(R.string.str9);
                    left_weather_txtview.setText(R.string.str9);
                    left_wind_txtview.setText(R.string.str9);
                    txtviewCurrTemp.setText(R.string.str9);

                    txt_temp01.setText(R.string.str9);
                    txt_temp02.setText(R.string.str9);
                    txt_temp03.setText(R.string.str9);
                    txt_temp04.setText(R.string.str9);
                    txt_temp05.setText(R.string.str9);
                    txt_temp06.setText(R.string.str9);

                    txt_weather01.setText(R.string.str9);
                    txt_weather02.setText(R.string.str9);
                    txt_weather03.setText(R.string.str9);
                    txt_weather04.setText(R.string.str9);
                    txt_weather05.setText(R.string.str9);
                    txt_weather06.setText(R.string.str9);

                    txt_wind01.setText(R.string.str9);
                    txt_wind02.setText(R.string.str9);
                    txt_wind03.setText(R.string.str9);
                    txt_wind04.setText(R.string.str9);
                    txt_wind05.setText(R.string.str9);
                    txt_wind06.setText(R.string.str9);

                    txtviewRise.setText(R.string.str9);
                    txtviewDown.setText(R.string.str9);

                    index_xiyi.setText(R.string.str10);
                    index_ganmao.setText(R.string.str10);
                    index_shushi.setText(R.string.str10);
                    index_yundong.setText(R.string.str10);
                    index_xiche.setText(R.string.str10);
                    index_huoxian.setText(R.string.str10);
                    index_yujing.setText(R.string.str10);
                }
            }
        };

        timer.scheduleAtFixedRate(timerTask,200, 30 * 60 * 1000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("WEATHER","应用停止");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("WEATHER","应用销毁");
        progressdialog.dismiss();
        //imgView=null;
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

        return mUrl;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

//        int index=0;
//
//        if( KeyEvent.KEYCODE_DPAD_LEFT == keyCode ){  //如果按下的是左键
//            if(index==0 || index<=0){
//                index=1;
//            }
//            imgbtnArray[index-1].setFocusable(true);
//            index--;
//        }
//        if( KeyEvent.KEYCODE_DPAD_RIGHT == keyCode ){  //如果按下的是右键
//
//            if(index==2 || index>=2){
//                index=1;
//            }
//            imgbtnArray[index+1].setFocusable(true);
//            index++;
//        }

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
    public void onClick(View v) {
        int id=v.getId();
        Intent intent=null;
        switch(id){
            case R.id.txtview01:
                intent=new Intent(this,TaiFengMainActivity.class);
                startActivity(intent);
                break;
            case R.id.txtview02:
                intent=new Intent(this,WuMaiMainActivity.class);
                startActivity(intent);
                break;
            case R.id.txtview03:
                intent=new Intent(this,JiaoTongActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    //获取气象数据
    public class GetWeatherDataAsyncTask extends AsyncTask<String, Integer, String> {

        private String url;
        Message msg = new Message();

        public GetWeatherDataAsyncTask(String url) {
            this.url = url;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressdialog.dismiss();
            msg.what=COMPLETE;
            mHandler.sendMessage(msg);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {

//            String detailResult = null;
            String result = null;

            try {
                if (NetUtils.isNetworkConnected(MainOldActivity.this) ||
                    NetUtils.isWifiConnected(MainOldActivity.this)) {
                    result = HttpConnUtils.download(url);
                    Log.i("WEATHER",result);
                } else {
                    msg.what = NONETWORK;
                }

                if (result != null) {

                    weatherEntityList=new ArrayList<>();
                    indexEntityList=new ArrayList<>();
                    ybTodayEntityList=new ArrayList<>();

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
                    }
                    if(jsonArrayshikuang.has("aqi") && jsonArrayshikuang.get("aqi")!=null){
                        shiKuangEntity.setAqi(jsonArrayshikuang.get("aqi").toString());
                    }
                    if(jsonArrayshikuang.has("qy") && jsonArrayshikuang.get("qy")!=null){
                        shiKuangEntity.setQy(jsonArrayshikuang.get("qy").toString());
                    }

                    AppGlobal.getInstance().setShiKuangEntity(null);
                    AppGlobal.getInstance().setShiKuangEntity(shiKuangEntity);


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


                    JSONArray jsonArrayyb = jsonObject.getJSONArray("yb");

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

                        weatherEntityList.add(weatherEntity);
                    }

                    AppGlobal.getInstance().setWeatherEntityList(null);
                    AppGlobal.getInstance().setWeatherEntityList(weatherEntityList);


                    //指数
                    JSONArray jsonArrayzhishu = jsonObject.getJSONArray("zhishu");

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

                return null;
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (TimeoutController.TimeoutException e) {
                e.printStackTrace();
            }
            mHandler.sendMessage(msg);
            return null;
        }
    }
}
