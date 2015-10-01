package com.lbs.weatherdemo.activity.drawview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.View;

import com.lbs.weatherdemo.R;
import com.lbs.weatherdemo.activity.entity.ScreenPoint;
import com.lbs.weatherdemo.activity.util.LatLon2ScreenUtil;

/**
 * Created by mars on 2015/4/7.
 */
public class DrawView extends View {
    private Context context;

    public DrawView(Context context) {
        super(context);
        this.context=context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
		/*
		 * 方法 说明 drawRect 绘制矩形 drawCircle 绘制圆形 drawOval 绘制椭圆 drawPath 绘制任意多边形
		 * drawLine 绘制直线 drawPoin 绘制点
		 */
        // 创建画笔
        Paint p1 = new Paint();
        p1.setStrokeWidth(2);
        p1.setAntiAlias(true);
        p1.setColor(getResources().getColor(R.color.tf07));
//
        Paint p2 = new Paint();
        p2.setStrokeWidth(2);
        p2.setAntiAlias(true);
        p2.setColor(getResources().getColor(R.color.tf03));


        //参数a为透明度，取值范围为0~255，数值越小越透明。
        Paint p3 = new Paint();
        p3.setAntiAlias(true);
        p3.setStrokeWidth(1);
        p3.setAlpha(25);
        p3.setColor(getResources().getColor(R.color.tf04));
        p3.setStyle(Paint.Style.STROKE);

        Paint p4 = new Paint();
        p4.setAntiAlias(true);
        p4.setStrokeWidth(1);
        p4.setAlpha(40);
        p4.setColor(getResources().getColor(R.color.tf03));
        p4.setStyle(Paint.Style.FILL_AND_STROKE);


        //画点
        /**
         * 高德
         120.234375,30.297018
         108.94043,34.307144

         百度
         121.489397,31.263223
         120.137195,30.302207
         108.960837,34.293348
         */
        LatLon2ScreenUtil latLon2ScreenUtil=new LatLon2ScreenUtil(this.context);
        ScreenPoint sp1=latLon2ScreenUtil.latLon2Screen(120.234375,30.297018);
        ScreenPoint sp2=latLon2ScreenUtil.latLon2Screen(108.94043,34.307144);
        ScreenPoint sp3=latLon2ScreenUtil.latLon2Screen(121.489397,31.263223);
        ScreenPoint sp4=latLon2ScreenUtil.latLon2Screen(120.137195,30.302207);
        ScreenPoint sp5=latLon2ScreenUtil.latLon2Screen(108.960837,34.293348);
        ScreenPoint sp6=latLon2ScreenUtil.latLon2Screen(116.83718,27.228704);

        ScreenPoint sp7=latLon2ScreenUtil.latLon2Screen(83.574867,34.276168);
        ScreenPoint sp8=latLon2ScreenUtil.latLon2Screen(92.994283,30.403927);
        ScreenPoint sp9=latLon2ScreenUtil.latLon2Screen(109.919797,25.70534);
        ScreenPoint sp10=latLon2ScreenUtil.latLon2Screen(119.339213,26.635036);

        p2.setStyle(Paint.Style.FILL_AND_STROKE);
        p1.setStyle(Paint.Style.FILL_AND_STROKE);

        canvas.drawCircle(sp1.getScreenX(), sp1.getScreenY(), 2, p1);
        canvas.drawPoint(sp1.getScreenX(), sp1.getScreenY(), p1);
        Log.i("WEATHER", "screenX1: " + sp1.getScreenX() + "  screenY1: " + sp1.getScreenY());
        canvas.drawCircle(sp2.getScreenX(), sp2.getScreenY(), 2, p1);
        canvas.drawPoint(sp2.getScreenX(), sp2.getScreenY(), p1);
        Log.i("WEATHER","screenX2: "+sp2.getScreenX()+"  screenY2: "+sp2.getScreenY());
        canvas.drawCircle(sp3.getScreenX(), sp3.getScreenY(), 2, p2);
        canvas.drawPoint(sp3.getScreenX(), sp3.getScreenY(), p2);
        Log.i("WEATHER","screenX3: "+sp3.getScreenX()+"  screenY3: "+sp3.getScreenY());
        canvas.drawCircle(sp4.getScreenX(), sp4.getScreenY(), 2, p2);
        canvas.drawPoint(sp4.getScreenX(), sp4.getScreenY(), p2);
        Log.i("WEATHER","screenX4: "+sp4.getScreenX()+"  screenY4: "+sp4.getScreenY());
        canvas.drawCircle(sp5.getScreenX(), sp5.getScreenY(), 2, p2);
        canvas.drawPoint(sp5.getScreenX(), sp5.getScreenY(), p2);
        Log.i("WEATHER","screenX5: "+sp5.getScreenX()+"  screenY5: "+sp5.getScreenY());

        canvas.drawCircle(sp6.getScreenX(), sp6.getScreenY(), 120, p3);// 圆
        canvas.drawCircle(sp6.getScreenX(), sp6.getScreenY(), 60, p4);// 圆

        Path path1=new Path();
       // paint.setStrokeWidth((float) 20.0);
        path1.moveTo(sp7.getScreenX(), sp7.getScreenY());
        canvas.drawCircle(sp7.getScreenX(), sp7.getScreenY(), 2, p1);
        path1.lineTo(sp8.getScreenX(), sp8.getScreenY());
        canvas.drawCircle(sp8.getScreenX(), sp8.getScreenY(), 2, p1);
        path1.lineTo(sp9.getScreenX(), sp9.getScreenY());
        canvas.drawCircle(sp9.getScreenX(), sp9.getScreenY(), 2, p1);
        path1.lineTo(sp10.getScreenX(), sp10.getScreenY());
        canvas.drawCircle(sp10.getScreenX(), sp10.getScreenY(), 2, p1);
        canvas.drawPath(path1, p3);

        //canvas.drawPoints(new float[]{60,400,65,400,70,400}, p2);//画多个点

//        //画图片，就是贴图
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
//        canvas.drawBitmap(bitmap, 250,360, p);


        //        canvas.drawText("画线及弧线：", 10, 60, p);
//        p.setColor(Color.GREEN);// 设置绿色
//        canvas.drawLine(60, 40, 100, 40, p);// 画线
//        canvas.drawLine(110, 40, 190, 80, p);// 斜线
//        //画笑脸弧线
//        p.setStyle(Paint.Style.STROKE);//设置空心
//        RectF oval1=new RectF(150,20,180,40);
//        canvas.drawArc(oval1, 180, 180, false, p);//小弧形
//        oval1.set(190, 20, 220, 40);
//        canvas.drawArc(oval1, 180, 180, false, p);//小弧形
//        oval1.set(160, 30, 210, 60);
//        canvas.drawArc(oval1, 0, 180, false, p);//小弧形

//        canvas.drawText("画矩形：", 10, 80, p);
//        p.setColor(Color.GRAY);// 设置灰色
//        p.setStyle(Paint.Style.FILL);//设置填满
//        canvas.drawRect(60, 60, 80, 80, p);// 正方形
//        canvas.drawRect(60, 90, 160, 100, p);// 长方形
//
//        canvas.drawText("画扇形和椭圆:", 10, 120, p);
//		/* 设置渐变色 这个正方形的颜色是改变的 */
//        Shader mShader = new LinearGradient(0, 0, 100, 100,
//                new int[] { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW,
//                        Color.LTGRAY }, null, Shader.TileMode.REPEAT); // 一个材质,打造出一个线性梯度沿著一条线。
//        p.setShader(mShader);
//        // p.setColor(Color.BLUE);
//        RectF oval2 = new RectF(60, 100, 200, 240);// 设置个新的长方形，扫描测量
//        canvas.drawArc(oval2, 200, 130, true, p);
//        // 画弧，第一个参数是RectF：该类是第二个参数是角度的开始，第三个参数是多少度，第四个参数是真的时候画扇形，是假的时候画弧线
//        //画椭圆，把oval改一下
//        oval2.set(210,100,250,130);
//        canvas.drawOval(oval2, p);

//        canvas.drawText("画三角形：", 10, 200, p);
//        // 绘制这个三角形,你可以绘制任意多边形
//        Path path = new Path();
//        path.moveTo(80, 200);// 此点为多边形的起点
//        path.lineTo(120, 250);
//        path.lineTo(80, 250);
//        path.close(); // 使这些点构成封闭的多边形
//        canvas.drawPath(path, p);

        // 你可以绘制很多任意多边形，比如下面画六连形
//        p1.reset();//重置
//        p1.setColor(Color.LTGRAY);
//        p1.setStyle(Paint.Style.STROKE);//设置空心
//        Path path1=new Path();
//        path1.moveTo(1220, 10);
//        path1.lineTo(1210, 12);
//        path1.lineTo(1200, 14);
//        path1.lineTo(1180, 16);
//        path1.lineTo(1172, 18);
//        path1.lineTo(1100, 25);
        //path1.close();//封闭
        //canvas.drawPath(path1, p1);
		/*
		 * Path类封装复合(多轮廓几何图形的路径
		 * 由直线段*、二次曲线,和三次方曲线，也可画以油画。drawPath(路径、油漆),要么已填充的或抚摸
		 * (基于油漆的风格),或者可以用于剪断或画画的文本在路径。
		 */

//        //画圆角矩形
//        p.setStyle(Paint.Style.FILL);//充满
//        p.setColor(Color.LTGRAY);
//        p.setAntiAlias(true);// 设置画笔的锯齿效果
//        canvas.drawText("画圆角矩形:", 10, 260, p);
//        RectF oval3 = new RectF(80, 260, 200, 300);// 设置个新的长方形
//        canvas.drawRoundRect(oval3, 20, 15, p);//第二个参数是x半径，第三个参数是y半径
//
//        //画贝塞尔曲线
//        canvas.drawText("画贝塞尔曲线:", 10, 310, p);
//        p.reset();
//        p.setStyle(Paint.Style.STROKE);
//        p.setColor(Color.GREEN);
//        Path path2=new Path();
//        path2.moveTo(100, 320);//设置Path的起点
//        path2.quadTo(150, 310, 170, 400); //设置贝塞尔曲线的控制点坐标和终点坐标
//        canvas.drawPath(path2, p);//画出贝塞尔曲线
    }
}
