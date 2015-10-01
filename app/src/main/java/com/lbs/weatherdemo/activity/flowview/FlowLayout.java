package com.lbs.weatherdemo.activity.flowview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mars on 2015/4/22.
 */
public class FlowLayout extends ViewGroup {

    public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public FlowLayout(Context context) {
        this(context,null);
    }

    //保存所有的view
    private List<List<View>> mAllViews=new ArrayList<List<View>>();

    //保存每一行的高度
    private List<Integer> mLineHeight=new ArrayList<Integer>();


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //父view的宽度和高度,fill_parent,match_parent
        int sizeWidth=MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth=MeasureSpec.getMode(widthMeasureSpec);

        int sizeHeight=MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight=MeasureSpec.getMode(heightMeasureSpec);

        //wrap_content
        int width=0;
        int height=0;

        //记住每一行的宽度和高度
        int lineWidth=0;
        int lineHeight=0;

        //得到内部元素的个数
        int cCount=getChildCount();

        for (int i = 0; i < cCount; i++) {
            View child=getChildAt(i);
            //测量子view的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            //子View的由父View决定
            MarginLayoutParams lpLayoutParams=(MarginLayoutParams) child.getLayoutParams();

            //子view占据的宽度
            int childWidth=child.getMeasuredWidth()+lpLayoutParams.leftMargin+lpLayoutParams.rightMargin;
            //子view占据的高度
            int childHeight=child.getMeasuredHeight()+lpLayoutParams.bottomMargin+lpLayoutParams.topMargin;

            //换行
            if(lineWidth+childWidth>sizeWidth-getPaddingLeft()-getPaddingRight()){
                //确定上一行的宽度，最终宽度是所有行中最大的宽度
                width=Math.max(width, lineWidth);
                //叠加高度
                height+=lineHeight;
                //换行，重置宽度，开辟新行,新行的宽度
                lineWidth=childWidth;
                //新行高度
                lineHeight=childHeight;
            }else{
                //未换行则叠加行宽
                lineWidth+=childWidth;
                //比较当前行最高的控件
                lineHeight=Math.max(lineHeight, childHeight);
            }

            if (i==cCount-1) {
                width=Math.max(childWidth, width);
                height+=lineHeight;
            }

            Log.i("TAG", "sizeWidth:"+sizeWidth);
            Log.i("TAG", "sizeHeight:"+sizeHeight);

        }

        Log.i("TAG", "width:" + width);
        Log.i("TAG", "height:"+height);

        setMeasuredDimension(modeWidth==MeasureSpec.EXACTLY?sizeWidth:width+getPaddingLeft()+getPaddingRight(),
                modeHeight==MeasureSpec.EXACTLY?sizeHeight:height+getPaddingTop()+getPaddingBottom());

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        mAllViews.clear();
        mLineHeight.clear();

        //已经执行完onMeasure()方法，当前view的宽度
        int width=getWidth();

        Log.i("TAG", "onLayout:width"+width);

        int lineWidth=0;
        int lineHeight=0;

        //每一行的view
        List<View> lineViews=new ArrayList<View>();
        int cCount=getChildCount();

        for (int i = 0; i < cCount; i++) {
            View child=getChildAt(i);
            MarginLayoutParams lpLayoutParams=(MarginLayoutParams) child.getLayoutParams();
            int childWidth=child.getMeasuredWidth();
            int childHeight=child.getMeasuredHeight();

            //如果需要换行
            if(childWidth+lineWidth+lpLayoutParams.leftMargin+lpLayoutParams.rightMargin>width){
                mLineHeight.add(lineHeight);
                mAllViews.add(lineViews);
                //新行的宽度......
                lineWidth=0;
                //新行的高度
                lineHeight=childHeight+lpLayoutParams.topMargin+lpLayoutParams.bottomMargin;
                lineViews=new ArrayList<View>();
            }
            lineWidth += childWidth + lpLayoutParams.leftMargin + lpLayoutParams.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lpLayoutParams.topMargin + lpLayoutParams.bottomMargin);
            //未换行，添加元素
            lineViews.add(child);
        }

        //处理最后一行
        mLineHeight.add(lineHeight);
        mAllViews.add(lineViews);


        //设置子View位置
        int left = getPaddingLeft();
        int top = getPaddingTop();

        // 行数
        int lineNum = mAllViews.size();

        for (int i = 0; i < lineNum; i++){
            // 当前行的所有的View
            lineViews = mAllViews.get(i);
            lineHeight = mLineHeight.get(i);

            for (int j = 0; j < lineViews.size(); j++){
                View child = lineViews.get(j);
                // 判断child的状态
                if (child.getVisibility() == View.GONE){
                    continue;
                }

                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                // 为子View进行布局
                child.layout(lc, tc, rc, bc);

                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;//也可用rc来更新
            }

            //遍历完一行
            left = getPaddingLeft();
            top += lineHeight;
        }

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

}
