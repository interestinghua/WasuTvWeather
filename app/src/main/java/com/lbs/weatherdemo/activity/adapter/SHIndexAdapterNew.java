package com.lbs.weatherdemo.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lbs.weatherdemo.R;
import com.lbs.weatherdemo.activity.entity.IndexEntity;

/**
 * Created by mars on 2015/4/23.
 */
public class SHIndexAdapterNew extends BaseAdapter {

    private IndexEntity[] indexEntities=new IndexEntity[]{};
    LayoutInflater inflater=null;
    Context context=null;

    public SHIndexAdapterNew(IndexEntity[] indexEntities, Context context) {

        this.indexEntities = indexEntities;
        this.context=context;
        this.inflater = LayoutInflater.from(context);
    }

    public void onDateChange(IndexEntity[] indexEntities) {
        this.indexEntities = indexEntities;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return indexEntities.length;
    }

    @Override
    public Object getItem(int position) {
        return indexEntities[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vHolder=null;

        if(null==convertView){
            convertView=inflater.inflate(R.layout.fragment_focused_shindex, null);
            vHolder=new ViewHolder();
            vHolder.logo_img=(ImageView) convertView.findViewById(R.id.logo_img);
            vHolder.index02_Textview=(TextView) convertView.findViewById(R.id.index02_Textview);
            vHolder.index01_Textview=(TextView) convertView.findViewById(R.id.index01_Textview);
            vHolder.index03_Textview=(TextView) convertView.findViewById(R.id.index03_Textview);
            convertView.setTag(vHolder);
        }else{
            vHolder=(ViewHolder) convertView.getTag();
        }

        switch(position){
            case 0:
                if(indexEntities[position]!=null && indexEntities[position] instanceof IndexEntity){
                    vHolder.index01_Textview.setText(indexEntities[position].getState());
                    vHolder.index03_Textview.setText(indexEntities[position].getJy());
                }
                vHolder.logo_img.setBackgroundResource(R.drawable.chuanyi);
                vHolder.index02_Textview.setText(R.string.index01);
                break;
            case 1:
                vHolder.logo_img.setBackgroundResource(R.drawable.ganmao);
                vHolder.index02_Textview.setText(R.string.index02);
                if(indexEntities[position]!=null && indexEntities[position] instanceof IndexEntity){
                    vHolder.index01_Textview.setText(indexEntities[position].getState());
                    vHolder.index03_Textview.setText(indexEntities[position].getJy());
                }
                break;
            case 2:
                vHolder.logo_img.setBackgroundResource(R.drawable.huazhuang);
                vHolder.index02_Textview.setText(R.string.index03);
                if(indexEntities[position]!=null && indexEntities[position] instanceof IndexEntity){
                    vHolder.index01_Textview.setText(indexEntities[position].getState());
                    vHolder.index03_Textview.setText(indexEntities[position].getJy());
                }
                break;
            case 3:
                vHolder.logo_img.setBackgroundResource(R.drawable.taiyangjing);
                vHolder.index02_Textview.setText(R.string.index04);
                if(indexEntities[position]!=null && indexEntities[position] instanceof IndexEntity){
                    vHolder.index01_Textview.setText(indexEntities[position].getState());
                    vHolder.index03_Textview.setText(indexEntities[position].getJy());
                }
                break;
            case 4:
                vHolder.logo_img.setBackgroundResource(R.drawable.ziwaixian);
                vHolder.index02_Textview.setText(R.string.index05);
                if(indexEntities[position]!=null && indexEntities[position] instanceof IndexEntity){
                    vHolder.index01_Textview.setText(indexEntities[position].getState());
                    vHolder.index03_Textview.setText(indexEntities[position].getJy());
                }
                break;
            case 5:
                vHolder.logo_img.setBackgroundResource(R.drawable.shushi);
                vHolder.index02_Textview.setText(R.string.index06);
                if(indexEntities[position]!=null && indexEntities[position] instanceof IndexEntity){
                    vHolder.index01_Textview.setText(indexEntities[position].getState());
                    vHolder.index03_Textview.setText(indexEntities[position].getJy());
                }
                break;
            case 6:
                vHolder.logo_img.setBackgroundResource(R.drawable.lvyou);
                vHolder.index02_Textview.setText(R.string.index07);
                if(indexEntities[position]!=null && indexEntities[position] instanceof IndexEntity){
                    vHolder.index01_Textview.setText(indexEntities[position].getState());
                    vHolder.index03_Textview.setText(indexEntities[position].getJy());
                }
                break;
            case 7:
                vHolder.logo_img.setBackgroundResource(R.drawable.yundong);
                vHolder.index02_Textview.setText(R.string.index08);
                if(indexEntities[position]!=null && indexEntities[position] instanceof IndexEntity){
                    vHolder.index01_Textview.setText(indexEntities[position].getState());
                    vHolder.index03_Textview.setText(indexEntities[position].getJy());
                }
                break;
            case 8:
                vHolder.logo_img.setBackgroundResource(R.drawable.xiche);
                vHolder.index02_Textview.setText(R.string.index09);
                if(indexEntities[position]!=null && indexEntities[position] instanceof IndexEntity){
                    vHolder.index01_Textview.setText(indexEntities[position].getState());
                    vHolder.index03_Textview.setText(indexEntities[position].getJy());
                }
                break;
        }

        convertView.setNextFocusUpId(R.id.shenhuozhishu_textview);

        return convertView;
    }

    class ViewHolder{
        ImageView logo_img;
        TextView index02_Textview;
        TextView index01_Textview;
        TextView index03_Textview;
    }

}
