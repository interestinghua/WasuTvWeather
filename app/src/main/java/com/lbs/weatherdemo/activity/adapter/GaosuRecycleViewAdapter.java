package com.lbs.weatherdemo.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lbs.weatherdemo.R;
import com.lbs.weatherdemo.activity.entity.HighWayEntity;

import java.util.List;

/**
 * Created by mars on 2015/5/12.
 */
public class GaosuRecycleViewAdapter extends RecyclerView.Adapter<GaosuRecycleViewAdapter.ViewHolder> {

    List<HighWayEntity> HighWayEntity_list=null;
    LayoutInflater inflater=null;
    Context context=null;

    public GaosuRecycleViewAdapter(List<HighWayEntity> HighWayEntity_list, Context context) {
        inflater=LayoutInflater.from(context);
        this.HighWayEntity_list = HighWayEntity_list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView roadidTextview;
        public TextView roadnameTextview;
        public TextView dateTextview;
        public TextView fengsuTextview;
        public TextView njdTextview;
        public TextView wenduTextview;
        public TextView fengxiangTextview;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public GaosuRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.highway_item,parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
//        parent.getContext();

        viewHolder.roadidTextview = (TextView) view.findViewById(R.id.roadidTextview);
        viewHolder.roadnameTextview = (TextView) view.findViewById(R.id.roadnameTextview);
        viewHolder.dateTextview = (TextView) view.findViewById(R.id.dateTextview);
        viewHolder.fengsuTextview = (TextView) view.findViewById(R.id.fengsuTextview);
        viewHolder.njdTextview = (TextView) view.findViewById(R.id.njdTextview);
        viewHolder.wenduTextview = (TextView) view.findViewById(R.id.wenduTextview);
        viewHolder.fengxiangTextview = (TextView) view.findViewById(R.id.fengxiangTextview);

        return viewHolder;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(GaosuRecycleViewAdapter.ViewHolder holder, int position) {
        String roadid=HighWayEntity_list.get(position).getRoadId();
        String roadname=HighWayEntity_list.get(position).getRoadName()+HighWayEntity_list.get(position).getSectionname();
        String data=HighWayEntity_list.get(position).getDate();
        String fengsu=HighWayEntity_list.get(position).getTwoAverWs();
        String njd=HighWayEntity_list.get(position).getInstantVis();
        String wendu=HighWayEntity_list.get(position).getTemper();
        String fengxiang=HighWayEntity_list.get(position).getTwoAverWd();

        holder.roadidTextview.setText(roadid);
        holder.roadnameTextview.setText(roadname);
        holder.dateTextview.setText(data);
        holder.fengsuTextview.setText(fengsu);
        holder.njdTextview.setText(njd);
        holder.wenduTextview.setText(wendu);
        holder.fengxiangTextview.setText(fengxiang);


    }

    @Override
    public int getItemCount() {
        return HighWayEntity_list.size();
    }
}
