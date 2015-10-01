package com.lbs.weatherdemo.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lbs.weatherdemo.R;
import com.lbs.weatherdemo.activity.entity.IndexEntity;

import java.util.List;

/**
 * Created by mars on 2015/5/12.
 */
public class SHIndexAdapter extends RecyclerView.Adapter<SHIndexAdapter.ViewHolder> {

    List<IndexEntity> IndexEntity_list=null;
    LayoutInflater inflater=null;
    private IndexEntity[] indexEntities=new IndexEntity[]{};
    Context context=null;

    public SHIndexAdapter(IndexEntity[] indexEntities,Context context) {
        inflater=LayoutInflater.from(context);
        this.indexEntities = indexEntities;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView zhishuimg;
        public TextView zhishuTxtview;
        public TextView zhishuContentTxtview;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public SHIndexAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.shindex_item,parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.zhishuimg = (ImageView) view.findViewById(R.id.zhishuimg);
        viewHolder.zhishuTxtview = (TextView) view.findViewById(R.id.zhishuTxtview);
        viewHolder.zhishuContentTxtview = (TextView) view.findViewById(R.id.zhishuContentTxtview);
        return viewHolder;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(SHIndexAdapter.ViewHolder holder, int position) {

        switch(position){
            case 0:
                if(indexEntities[position]!=null && indexEntities[position] instanceof IndexEntity){
                    holder.zhishuContentTxtview.setText(indexEntities[position].getState());
                }
                holder.zhishuimg.setBackgroundResource(R.drawable.chuanyi);
                holder.zhishuTxtview.setText(R.string.index01);
                break;
            case 1:
                holder.zhishuimg.setBackgroundResource(R.drawable.ganmao);
                holder.zhishuTxtview.setText(R.string.index02);
                if(indexEntities[position]!=null && indexEntities[position] instanceof IndexEntity){
                    holder.zhishuContentTxtview.setText(indexEntities[position].getState());
                }
                break;
            case 2:
                holder.zhishuimg.setBackgroundResource(R.drawable.huazhuang);
                holder.zhishuTxtview.setText(R.string.index03);
                if(indexEntities[position]!=null && indexEntities[position] instanceof IndexEntity){
                    holder.zhishuContentTxtview.setText(indexEntities[position].getState());
                }
                break;
            case 3:
                holder.zhishuimg.setBackgroundResource(R.drawable.taiyangjing);
                holder.zhishuTxtview.setText(R.string.index04);
                if(indexEntities[position]!=null && indexEntities[position] instanceof IndexEntity){
                    holder.zhishuContentTxtview.setText(indexEntities[position].getState());
                }
                break;
            case 4:
                holder.zhishuimg.setBackgroundResource(R.drawable.ziwaixian);
                holder.zhishuTxtview.setText(R.string.index05);
                if(indexEntities[position]!=null && indexEntities[position] instanceof IndexEntity){
                    holder.zhishuContentTxtview.setText(indexEntities[position].getState());
                }
                break;
            case 5:
                holder.zhishuimg.setBackgroundResource(R.drawable.shushi);
                holder.zhishuTxtview.setText(R.string.index06);
                if(indexEntities[position]!=null && indexEntities[position] instanceof IndexEntity){
                    holder.zhishuContentTxtview.setText(indexEntities[position].getState());
                }
                break;
            case 6:
                holder.zhishuimg.setBackgroundResource(R.drawable.lvyou);
                holder.zhishuTxtview.setText(R.string.index07);
                if(indexEntities[position]!=null && indexEntities[position] instanceof IndexEntity){
                    holder.zhishuContentTxtview.setText(indexEntities[position].getState());
                }
                break;
            case 7:
                holder.zhishuimg.setBackgroundResource(R.drawable.yundong);
                holder.zhishuTxtview.setText(R.string.index08);
                if(indexEntities[position]!=null && indexEntities[position] instanceof IndexEntity){
                    holder.zhishuContentTxtview.setText(indexEntities[position].getState());
                }
                break;
            case 8:
                holder.zhishuimg.setBackgroundResource(R.drawable.xiche);
                holder.zhishuTxtview.setText(R.string.index09);
                if(indexEntities[position]!=null && indexEntities[position] instanceof IndexEntity){
                    holder.zhishuContentTxtview.setText(indexEntities[position].getState());
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return indexEntities.length;
    }
}
