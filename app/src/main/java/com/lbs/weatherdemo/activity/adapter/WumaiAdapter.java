package com.lbs.weatherdemo.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.lbs.weatherdemo.R;
import com.lbs.weatherdemo.activity.entity.AQIEntity;

import java.util.List;

/**
 * Created by mars on 2015/4/23.
 */
public class WumaiAdapter extends BaseAdapter {

    List<AQIEntity> AQIEntity_list=null;
    LayoutInflater inflater=null;
    Context context=null;

    public WumaiAdapter(List<AQIEntity> AQIEntity_list,Context context) {

        this.AQIEntity_list = AQIEntity_list;
        this.context=context;
        this.inflater = LayoutInflater.from(context);
    }

    public void onDateChange(List<AQIEntity> AQIEntity_list) {
        this.AQIEntity_list = AQIEntity_list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return AQIEntity_list.size();
    }

    @Override
    public Object getItem(int position) {
        return AQIEntity_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void SetFocus(int index){
//        for(int i=0;i<imgItems.length;i++){
//            if(i!=index){
//                imgItems[i].setBackgroundResource(0);//恢复未选中的样式
//            }
//        }
//        imgItems[index].setBackgroundResource(selResId);//设置选中的样式
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vHolder=null;

        if(null==convertView){

            convertView=inflater.inflate(R.layout.wumai_item, null);
            convertView.setLayoutParams(new GridView.LayoutParams(565, 85));//重点行

            vHolder=new ViewHolder();
            vHolder.diquTxtview=(TextView) convertView.findViewById(R.id.diquTxtview);
            vHolder.zhiliangTxtview=(TextView) convertView.findViewById(R.id.zhiliangTxtview);

            convertView.setTag(vHolder);
        }else{
            vHolder=(ViewHolder) convertView.getTag();
        }

        String diqu=AQIEntity_list.get(position).getCity();
        String aqi=AQIEntity_list.get(position).getAqi();

        if(aqi!=null && !aqi.equals("") && aqi.indexOf("?")<0){
            int aqiInt=Integer.valueOf(aqi);
            String levelAqi=getLevelStr(aqiInt);
            vHolder.zhiliangTxtview.setText(levelAqi);
        }else{
            vHolder.zhiliangTxtview.setText("---");
        }

        vHolder.diquTxtview.setText(diqu);

        if(position==AQIEntity_list.size()-1){
            convertView.setNextFocusRightId(R.id.wumaiditu_textview);
        }

        return convertView;
    }

    class ViewHolder{
        TextView diquTxtview;
        TextView zhiliangTxtview;
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
}
