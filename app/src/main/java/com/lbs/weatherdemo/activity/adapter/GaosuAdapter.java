package com.lbs.weatherdemo.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lbs.weatherdemo.R;
import com.lbs.weatherdemo.activity.entity.HighWayEntity;

import java.util.List;

/**
 * Created by mars on 2015/4/23.
 */
public class GaosuAdapter extends BaseAdapter {

    List<HighWayEntity> HighWayEntity_list=null;
    LayoutInflater inflater=null;
    Context context=null;

    public GaosuAdapter(List<HighWayEntity> HighWayEntity_list, Context context) {

        this.HighWayEntity_list = HighWayEntity_list;
        this.context=context;
        this.inflater = LayoutInflater.from(context);
    }

    public void onDateChange(List<HighWayEntity> HighWayEntity_list) {
        this.HighWayEntity_list = HighWayEntity_list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return HighWayEntity_list.size();
    }

    @Override
    public Object getItem(int position) {
        return HighWayEntity_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vHolder=null;

        if(null==convertView){
            convertView=inflater.inflate(R.layout.highway_item, null);

            vHolder=new ViewHolder();
            vHolder.roadidTextview=(TextView) convertView.findViewById(R.id.roadidTextview);
            vHolder.roadnameTextview=(TextView) convertView.findViewById(R.id.roadnameTextview);
            vHolder.dateTextview=(TextView) convertView.findViewById(R.id.dateTextview);
            vHolder.fengsuTextview=(TextView) convertView.findViewById(R.id.fengsuTextview);
            vHolder.njdTextview=(TextView) convertView.findViewById(R.id.njdTextview);
            vHolder.wenduTextview=(TextView) convertView.findViewById(R.id.wenduTextview);
            vHolder.fengxiangTextview=(TextView) convertView.findViewById(R.id.fengxiangTextview);

            convertView.setTag(vHolder);
        }else{
            vHolder=(ViewHolder) convertView.getTag();
        }

        String roadid=HighWayEntity_list.get(position).getRoadId();
        String roadname=HighWayEntity_list.get(position).getRoadName()+HighWayEntity_list.get(position).getSectionname();
        String data=HighWayEntity_list.get(position).getDate();
        String fengsu=HighWayEntity_list.get(position).getTwoAverWs();
        String njd=HighWayEntity_list.get(position).getInstantVis();
        String wendu=HighWayEntity_list.get(position).getTemper();
        String fengxiang=HighWayEntity_list.get(position).getTwoAverWd();

        vHolder.roadidTextview.setText(roadid);
        vHolder.roadnameTextview.setText(roadname);
        vHolder.dateTextview.setText(data);
        vHolder.fengsuTextview.setText(fengsu);
        vHolder.njdTextview.setText(njd);
        vHolder.wenduTextview.setText(wendu);
        vHolder.fengxiangTextview.setText(fengxiang);

        convertView.setNextFocusUpId(R.id.gaosuqixiang_textview);

        return convertView;
    }

    class ViewHolder{
        TextView roadidTextview;
        TextView roadnameTextview;
        TextView dateTextview;
        TextView fengsuTextview;
        TextView njdTextview;
        TextView wenduTextview;
        TextView fengxiangTextview;
    }

}
