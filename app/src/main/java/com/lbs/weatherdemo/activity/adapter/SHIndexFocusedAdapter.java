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

/**
 * Created by mars on 2015/5/12.
 */
public class SHIndexFocusedAdapter extends RecyclerView.Adapter<SHIndexFocusedAdapter.ViewHolder> {

    LayoutInflater inflater=null;
    private IndexEntity[] indexEntities=new IndexEntity[]{};
    Context context=null;

    public SHIndexFocusedAdapter(IndexEntity[] indexEntities, Context context) {
        inflater=LayoutInflater.from(context);
        this.indexEntities = indexEntities;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView logo_img;
        public TextView index01_Textview;
        public TextView index02_Textview;
        public TextView index03_Textview;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public SHIndexFocusedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.fragment_focused_shindex,parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.logo_img = (ImageView) view.findViewById(R.id.logo_img);
        viewHolder.index01_Textview = (TextView) view.findViewById(R.id.index01_Textview);
        viewHolder.index02_Textview = (TextView) view.findViewById(R.id.index02_Textview);
        viewHolder.index03_Textview = (TextView) view.findViewById(R.id.index03_Textview);

        return viewHolder;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(SHIndexFocusedAdapter.ViewHolder holder, int position) {

        switch(position){
            case 0:

                holder.logo_img.setBackgroundResource(R.drawable.chuanyi);
                holder.index02_Textview.setText(R.string.index01);
                if(indexEntities[position]!=null && indexEntities[position] instanceof IndexEntity){
                    holder.index01_Textview.setText(indexEntities[position].getState());
                    holder.index03_Textview.setText(indexEntities[position].getJy());
                }
                break;
            case 1:
                holder.logo_img.setBackgroundResource(R.drawable.ganmao);
                holder.index02_Textview.setText(R.string.index02);
                if(indexEntities[position]!=null && indexEntities[position] instanceof IndexEntity){
                    holder.index01_Textview.setText(indexEntities[position].getState());
                    holder.index03_Textview.setText(indexEntities[position].getJy());
                }
                break;
            case 2:
                holder.logo_img.setBackgroundResource(R.drawable.huazhuang);
                holder.index02_Textview.setText(R.string.index03);
                if(indexEntities[position]!=null && indexEntities[position] instanceof IndexEntity){
                    holder.index01_Textview.setText(indexEntities[position].getState());
                    holder.index03_Textview.setText(indexEntities[position].getJy());
                }
                break;
            case 3:
                holder.logo_img.setBackgroundResource(R.drawable.taiyangjing);
                holder.index02_Textview.setText(R.string.index04);
                if(indexEntities[position]!=null && indexEntities[position] instanceof IndexEntity){
                    holder.index01_Textview.setText(indexEntities[position].getState());
                    holder.index03_Textview.setText(indexEntities[position].getJy());
                }
                break;
            case 4:
                holder.logo_img.setBackgroundResource(R.drawable.ziwaixian);
                holder.index02_Textview.setText(R.string.index05);
                if(indexEntities[position]!=null && indexEntities[position] instanceof IndexEntity){
                    holder.index01_Textview.setText(indexEntities[position].getState());
                    holder.index03_Textview.setText(indexEntities[position].getJy());
                }
                break;
            case 5:
                holder.logo_img.setBackgroundResource(R.drawable.shushi);
                holder.index02_Textview.setText(R.string.index06);
                if(indexEntities[position]!=null && indexEntities[position] instanceof IndexEntity){
                    holder.index01_Textview.setText(indexEntities[position].getState());
                    holder.index03_Textview.setText(indexEntities[position].getJy());
                }
                break;
            case 6:
                holder.logo_img.setBackgroundResource(R.drawable.lvyou);
                holder.index02_Textview.setText(R.string.index07);
                if(indexEntities[position]!=null && indexEntities[position] instanceof IndexEntity){
                    holder.index01_Textview.setText(indexEntities[position].getState());
                    holder.index03_Textview.setText(indexEntities[position].getJy());
                }
                break;
            case 7:
                holder.logo_img.setBackgroundResource(R.drawable.yundong);
                holder.index02_Textview.setText(R.string.index08);
                if(indexEntities[position]!=null && indexEntities[position] instanceof IndexEntity){
                    holder.index01_Textview.setText(indexEntities[position].getState());
                    holder.index03_Textview.setText(indexEntities[position].getJy());
                }
                break;
            case 8:
                holder.logo_img.setBackgroundResource(R.drawable.xiche);
                holder.index02_Textview.setText(R.string.index09);
                if(indexEntities[position]!=null && indexEntities[position] instanceof IndexEntity){
                    holder.index01_Textview.setText(indexEntities[position].getState());
                    holder.index03_Textview.setText(indexEntities[position].getJy());
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return indexEntities.length;
    }
}
