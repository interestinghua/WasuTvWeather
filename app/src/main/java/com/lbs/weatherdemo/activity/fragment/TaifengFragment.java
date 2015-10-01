package com.lbs.weatherdemo.activity.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.MapsInitializer;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.Circle;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.Polyline;
import com.amap.api.maps2d.model.PolylineOptions;
import com.lbs.weatherdemo.R;
import com.lbs.weatherdemo.activity.MainActivity;
import com.lbs.weatherdemo.activity.entity.CenterPoint;
import com.lbs.weatherdemo.activity.entity.TyphoonEntity;
import com.lbs.weatherdemo.activity.util.AppGlobal;
import com.lbs.weatherdemo.activity.util.HttpConnUtils;
import com.lbs.weatherdemo.activity.util.NetUtils;

import org.apache.commons.httpclient.util.TimeoutController;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaifengFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

//    private LatLon2ScreenUtil latLon2ScreenUtil=null;

    private static final int NOTYPHOON = 1;
    private static final int NONETWORK = 2;
    private static final int TIMEOUT = 3;
    private static final int DRAWTYPHOON = 4;

    private MainActivity context;

    private MapView mapView;
    private AMap aMap;
    private UiSettings mUiSettings = null;

    public static TaifengFragment newInstance(String param1, String param2) {
        TaifengFragment fragment = new TaifengFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public TaifengFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context= (MainActivity) activity;
//        latLon2ScreenUtil=new LatLon2ScreenUtil(activity);
//        ScreenPoint sp=latLon2ScreenUtil.latLon2Screen(121.464844, 31.235114);

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


        View view=inflater.inflate(R.layout.fragment_taifeng, container, false);
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写

//        Log.i("WEATHER", "screenX: " + sp.getScreenX() + "  screenY: " + sp.getScreenY());
        //init();
        initMap();
        setUpMap();

        ProgressAsyncTask progressAsyncTask=new ProgressAsyncTask();
        progressAsyncTask.execute();
        return view;
    }

    /**
     * 初始化AMap对象
     */
    private void initMap() {
        if (aMap == null) {
//            int count =mapView.getChildCount();
//            for(int i=0;i<count;i++){
//                mapView.getChildAt(i).setVisibility(View.INVISIBLE);
//            }

            aMap = mapView.getMap();
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(30.002517, 120.590057), 8));
        }
    }

    private void setUpMap() {

        // 设置地图UI
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        mUiSettings = aMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setScaleControlsEnabled(true);
        mUiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_LEFT);
        MapsInitializer.sdcardDir = getSdCacheDir(context);
    }

    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case NOTYPHOON:
                    Toast.makeText(context, "当前无台风", Toast.LENGTH_SHORT).show();
                    break;
                case NONETWORK:
                    Toast.makeText(context, "请检查网络连接",Toast.LENGTH_SHORT).show();
                    break;
                case TIMEOUT:
                    Toast.makeText(context, "连接服务器超时",Toast.LENGTH_SHORT).show();
                    break;
                case DRAWTYPHOON:
                    String detailResult = CurrentDrawTyphoon(AppGlobal.getInstance().getCpList());
                    String[] detail=detailResult.split("&");
                    String str1="当前台风 名称:" + detail[0] + "("
                            + detail[1] + ")" + " 编号:"
                            + detail[2] + " 时间:" + detail[3] + "  类型:"
                            + detail[4] + "  经度:" + detail[5] + " 纬度:"
                            + detail[6] + " 中心气压: "
                            + detail[7] + "百帕  风速:"
                            + detail[8] + "米/秒" + " 移动方向:"
                            + detail[9] + " 移动速度:"
                            + detail[10] + "米/秒"+"___"+detail[0] + "("
                            + detail[1]+" "+detail[2]+")";
                    System.out.println("台风详情" + str1);
                    break;
                default:
                    break;
            }
        };
    };

    public class ProgressAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println("*****异步请求后*****");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("*****异步请求前*****");
        }

        @SuppressLint("DefaultLocale")
        @Override
        protected String doInBackground(String... args) {
            Message msg = new Message();

            System.out.println("*****异步请求中*****");
            List<TyphoonEntity> tyEntityList = new ArrayList<>();
            List<TyphoonEntity> tyCurrentEntityList = new ArrayList<>();

            Calendar ca = Calendar.getInstance();
            int year = ca.get(Calendar.YEAR);

            // 获取当前台风
            String url1 = AppGlobal.TYPHOONSERVER + "year.action?year=" + String.valueOf(year);
//			String url1 = "http://www.lbschina.com.cn:8089/typhoonserver/DataCache/year.action?year=" + String.valueOf(year);
            String result1 = null;

            try {
                if (NetUtils.isNetworkConnected(context)
                        || NetUtils.isWifiConnected(context)) {
                    result1 = HttpConnUtils.download(url1);
                    //result1 = AppGlobal.CONTENT;
                } else {
                    msg.what = NONETWORK;
                }

                // 台风记录数组
                if (result1 != null) {
                    System.out.println("**************result1**************" + result1);
                    if (result1.startsWith("?")) {
                        result1 = result1.trim().replaceAll("[\\t\\n\\r]", "").substring(1);
                    } else {
                        result1 = result1.trim().replaceAll("[\\t\\n\\r]", "");
                    }
                    JSONArray jsonArray = new JSONArray(result1);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject o = (JSONObject) jsonArray.get(i);

                        TyphoonEntity te = new TyphoonEntity();

                        if (o.getString("CNAME") != null) {
                            te.setCname(o.getString("CNAME"));
                        }
                        if (o.getString("ID") != null) {
                            te.setId(o.getString("ID"));
                        }
                        if (o.getString("image") != null) {
                            te.setImage(o.getString("image"));
                        }
                        if (o.getString("Introduction") != null) {
                            te.setIntroduction(o.getString("Introduction"));
                        }
                        if (o.getString("NAME") != null) {
                            te.setName(o.getString("NAME"));
                        }
                        if (o.getString("running") != null) {
                            te.setRunning(o.getString("running"));
                        }
                        if (o.getString("start_time") != null) {
                            te.setStarttime(o.getString("start_time"));
                        }
                        if (o.getString("warn_level") != null) {
                            te.setWarnlevel(o.getString("warn_level"));
                        }
                        if (o.getString("YQ") != null) {
                            te.setYq(o.getString("YQ"));
                        }
                        if (o.getString("ZQ") != null) {
                            te.setZq(o.getString("ZQ"));
                        }

                        tyEntityList.add(te);
                    }

                    if (tyEntityList != null && tyEntityList.size() > 0) {
                        for (TyphoonEntity te : tyEntityList) {
                            if (te.getRunning().toLowerCase().equals("true")) {
                                // 表示当前活跃台风
                                tyCurrentEntityList.add(te);
                            }
                        }
                    }

                    if (tyCurrentEntityList != null && tyCurrentEntityList.size() > 0) {
                        SharedPreferences TFISEXIST_info = context.getSharedPreferences("TFISEXIST_info",context.MODE_PRIVATE);
                        TFISEXIST_info.edit().putBoolean("ISEXIT", true).commit();
                        for (TyphoonEntity tce : tyCurrentEntityList) {
                            String tfid = tce.getId();
                            String url = AppGlobal.TYPHOONSERVER+"id.action?id="+ tfid;
                            //String url = "http://www.lbschina.com.cn:8089/typhoonserver/DataCache/id.action?id="+ tfid;
                            GetRouteDataAsyncTask getrouteTask = new GetRouteDataAsyncTask(url);
                            getrouteTask.execute(url);
                        }
                    } else {
                        SharedPreferences TFISEXIST_info = context.getSharedPreferences("TFISEXIST_info",context.MODE_PRIVATE);
                        TFISEXIST_info.edit().putBoolean("ISEXIT", false).commit();
                        msg.what = NOTYPHOON;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (TimeoutController.TimeoutException e) {
                e.printStackTrace();
            }
            mHandler.sendMessage(msg);
            return null;
        }
    }


    //获取当前台风信息
    public class GetRouteDataAsyncTask extends AsyncTask<String, Integer, String> {

        private String url;

        public GetRouteDataAsyncTask(String url) {
            this.url = url;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println("*****异步请求后*****");
        }

        @Override
        protected String doInBackground(String... args) {

            System.out.println("*****GetRouteDataAsyncTask 异步请求中*****");
            Message msg = new Message();
            List<CenterPoint> cpList = new ArrayList<>();
            String detailResult = null;
            String result = null;

            try {
                if (NetUtils.isNetworkConnected(context)
                        || NetUtils.isWifiConnected(context)) {
                    result = HttpConnUtils.download(url);
                } else {
                    msg.what = NONETWORK;
                }

                if (result != null) {
                    if (result.startsWith("?")) {
                        result = result.trim().replaceAll("[\\t\\n\\r]", "").substring(1);
                    } else {
                        result = result.trim().replaceAll("[\\t\\n\\r]", "");
                    }

                    JSONObject jo2 = new JSONObject(result);
                    JSONArray jsonArray = jo2.getJSONArray("lishi");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject o = (JSONObject) jsonArray.get(i);
                        CenterPoint cp = new CenterPoint();
                        if (o.getString("ID") != null) {
                            cp.setId(o.getString("ID"));
                        }
                        if (o.getString("RQSJ") != null) {
                            cp.setRqsj(o.getString("RQSJ"));
                        }
                        if (o.getString("JD") != null) {
                            cp.setCenterx(Double.valueOf(o.getString("JD")));
                        }
                        if (o.getString("WD") != null) {
                            cp.setCentery(Double.valueOf(o.getString("WD")));
                        }
                        if (o.getString("FS") != null) {
                            cp.setFengsu(Double.valueOf(o.getString("FS")));
                        }
                        if (o.getString("QY") != null) {
                            cp.setQiya(Double.valueOf(o.getString("QY")));
                        }
                        if (o.getString("TY") != null) {
                            cp.setType(o.getString("TY"));
                        }
                        if (o.getString("NAME") != null) {
                            cp.setName(o.getString("NAME"));
                        }
                        if (o.getString("radius7") != null) {
                            cp.setRadious7(Double.valueOf(o
                                    .getString("radius7")));
                        }
                        if (o.getString("radius10") != null) {
                            cp.setRadious10(Double.valueOf(o
                                    .getString("radius10")));
                        }
                        if (o.getString("movesd") != null) {
                            cp.setMovespeed(Double.valueOf(o
                                    .getString("movesd")));
                        }
                        if (o.getString("movefx") != null) {
                            cp.setMovedirection(o.getString("movefx"));
                        }
                        if (o.getString("ENAME") != null) {
                            cp.setEname(o.getString("ENAME"));
                        }
                        if (o.getString("FL") != null) {
                            cp.setFengli(Double.valueOf(o.getString("FL")));
                        }

                        cpList.add(cp);
                    }

                    if (cpList != null && cpList.size() > 0) {
                        //存储最后一个点
                        AppGlobal.getInstance().setCp(null);
                        AppGlobal.getInstance().setCp(cpList.get(0));
                        AppGlobal.getInstance().setCpList(null);
                        AppGlobal.getInstance().setCpList(cpList);
                        msg.what=DRAWTYPHOON;
                    }
                }
                return detailResult;
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (TimeoutController.TimeoutException e) {
                e.printStackTrace();
            }
            mHandler.sendMessage(msg);
            return null;
        }
    }


    //绘制当前台风
    public String CurrentDrawTyphoon(List<CenterPoint> cpList) {

        List<LatLng> latng_list = null;
        if (cpList != null && cpList.size()>0) {
            latng_list = new ArrayList<>();
            for (CenterPoint c : cpList) {
                LatLng latng = new LatLng(c.getCentery(), c.getCenterx());
                latng_list.add(latng);
            }
        }

        String typhoonDetail = null;

        // 台风路径中心点位置
        double vx = 0.0f;
        double vy = 0.0f;

        for (LatLng ll : latng_list) {
            vx += ll.longitude;
            vy += ll.latitude;
        }

        vx = vx / latng_list.size();
        vy = vy / latng_list.size();

        List<Polyline> currentPolyline=new ArrayList<>();
        List<Marker> currentMarkerList = new ArrayList<>();

        // 添加起点标示
        int len = cpList.size();
        Marker startMarker=aMap.addMarker(new MarkerOptions().position(
                new LatLng(cpList.get(len - 1).getCentery(), cpList.get(len - 1).getCenterx())).anchor(0.5f, 0.8f)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.fengbao1)));
        currentMarkerList.add(startMarker);

        MarkerOptions markerOptions = new MarkerOptions();

        // 获取最新点信息
        String timetypelistLenStr = cpList.get(0).getRqsj().trim();
        String typeTyphoon = cpList.get(0).getType().trim();

        typhoonDetail=cpList.get(0).getName()+"&"+cpList.get(0).getEname()+"&"+cpList.get(0).getId()+"&"
                +timetypelistLenStr+"&"+typeTyphoon+"&"+cpList.get(0).getCenterx()+"&"+cpList.get(0).getCentery()+"&"+cpList.get(0).getQiya()
                +"&"+cpList.get(0).getFengsu()+"&"+cpList.get(0).getMovedirection()+"&"+cpList.get(0).getMovespeed();



        for (int k = 0; k < cpList.size() - 1; k++) {
            Polyline currentpolyline=aMap.addPolyline((new PolylineOptions())
                    .add(new LatLng(cpList.get(k).getCentery(), cpList.get(k).getCenterx()),
                            new LatLng(cpList.get(k + 1).getCentery(), cpList.get(k + 1).getCenterx()))
                    .color(Color.argb(95, 238, 64, 0)).width(7));
            currentPolyline.add(currentpolyline);

            String type = cpList.get(k).getType().trim();

            markerOptions.position(new LatLng(cpList.get(k).getCentery(),
                    cpList.get(k).getCenterx()));
            // 9月25日8时
            String timeStr = cpList.get(k).getRqsj().trim();
            markerOptions.snippet("台风名称:"+cpList.get(k).getName().trim()+"("+cpList.get(k).getEname()+")"+"\n"+"时间:" + timeStr + "  类型:" + type + "\n"
                    + "经度:" + cpList.get(k).getCenterx() + "    纬度:"
                    + cpList.get(k).getCentery() + "\n" + "中心气压: "
                    + cpList.get(k).getQiya() + "百帕  风速:"
                    + cpList.get(k).getFengsu() + "米/秒" + "\n" + "移动方向:"
                    + cpList.get(k).getMovedirection() + " 移动速度:"
                    + cpList.get(k).getMovespeed() + "米/秒");
            markerOptions.draggable(true);
            markerOptions.anchor(0.5f, 0.5f);
            markerOptions.title(timeStr);
            if (type.equals("热带低压")) {
                markerOptions.icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.redaidiya1));
            } else if (type.equals("热带风暴")) {
                markerOptions.icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.redaifengbao1));
            } else if (type.equals("强热带风暴")) {
                markerOptions.icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.qiangredaifengbao1));
            } else if (type.equals("台风")) {
                markerOptions.icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.taifeng1));
            } else if (type.equals("强台风")) {
                markerOptions.icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.qiangtaifeng1));
            } else if (type.equals("超强台风")) {
                markerOptions.icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.chaoqiangtaifeng1));
            } else if (type.equals("热带低气压")) {
                markerOptions.icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.redaidiya1));
            } else {
                markerOptions.icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.redaidiya1));
            }
            Marker currentmarker=aMap.addMarker(markerOptions);
            currentMarkerList.add(currentmarker);
        }

        // 台风终点
        //存储终点信息
        List<Circle> currentEndCircle=new ArrayList<>();
        double cy = cpList.get(0).getCentery();
        double cx = cpList.get(0).getCenterx();
        double radious7 = cpList.get(0).getRadious7();// km
        double radious10 = cpList.get(0).getRadious10();// km

        LatLng centerlatng = new LatLng(cy, cx);
        Circle circle7=aMap.addCircle(new CircleOptions().center(centerlatng)
                .radius(radious7 * 1000)// 单位米
                .strokeColor(Color.YELLOW)
                .fillColor(Color.argb(50, 0, 178, 238)).strokeWidth(3));
        currentEndCircle.add(circle7);

        Circle circle8=aMap.addCircle(new CircleOptions().center(centerlatng)
                .radius(radious10 * 1000)// 单位米
                .strokeColor(Color.RED).fillColor(Color.argb(50, 0, 178, 238))
                .strokeWidth(2));
        currentEndCircle.add(circle8);

        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latng_list.get(0).latitude, latng_list.get(0).longitude), 9));

        latng_list.clear();

        return typhoonDetail;
    }

    private String getSdCacheDir(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File fExternalStorageDirectory = Environment.getExternalStorageDirectory();
            File autonaviDir = new File(fExternalStorageDirectory, "mobilemap");
            if (!autonaviDir.exists()) {
                autonaviDir.mkdir();
            }
            File minimapDir = new File(autonaviDir, "map");
            if (!minimapDir.exists()) {
                minimapDir.mkdir();
            }
            return minimapDir.toString() + "/";
        } else {
            return null;
        }
    }
}