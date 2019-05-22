package com.example.niaogebiji.myviewapplication.view.commonview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/***
 * 2019.5.22 -- textview的一些知识
 * FontMetrics类 的公式熟悉
 * 绘制文本的最小矩形
 */
public class MyTextView extends View {
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        String text = "harvic\'s blog";

        int baseLineY = 200 ;
        int baseLineX = 0;

        //写文字
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setTextSize(120);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(text,baseLineX,baseLineY,paint);


        //FontMetrics中的变量fm.ascent代表ascenty的坐标 - baseline的坐标；它是个负值，
        Paint.FontMetrics fm = paint.getFontMetrics();
        float ascent = baseLineY  + fm.ascent;
        float descent = baseLineY + fm.descent;
        float top = baseLineY + fm.top;
        float bottom = baseLineY + fm.bottom;

        //画基线
        paint.setColor(Color.RED);
        canvas.drawLine(baseLineX,baseLineY,3000,baseLineY,paint);

        //画top
        paint.setColor(Color.BLUE);
        canvas.drawLine(baseLineX, top, 3000, top, paint);

        //画ascent
        paint.setColor(Color.GREEN);
        canvas.drawLine(baseLineX, ascent, 3000, ascent, paint);

        //画descent
        paint.setColor(Color.YELLOW);
        canvas.drawLine(baseLineX, descent, 3000, descent, paint);

        //画bottom
        paint.setColor(Color.RED);
        canvas.drawLine(baseLineX, bottom, 3000, bottom, paint);



        int baseLineY2 = 400;

        //绘制text所占区域,计算各种线的坐标
        Paint.FontMetricsInt fm2 = paint.getFontMetricsInt();
        int top2 =  (baseLineY2 + fm2.top);
        int bottom2 =  (baseLineY2 + fm2.bottom);
        int width2 = (int) paint.measureText(text);
        Rect rect = new Rect(baseLineX,top2,baseLineX + width2,bottom2 );
        paint.setColor(Color.GREEN);
        canvas.drawRect(rect,paint);


        //绘制最小矩形
        Rect minRect = new Rect();
        paint.getTextBounds(text,0,text.length(),minRect);
        Log.e("tag",minRect.top + "");
        //TODO 以baselineY2为基准
        minRect.top = baseLineY2 + minRect.top;
        minRect.bottom = baseLineY2 + minRect.bottom;
        paint.setColor(Color.RED);
        canvas.drawRect(minRect,paint);

        //绘制文本
        paint.setColor(Color.BLACK);
        canvas.drawText(text,baseLineX,baseLineY2,paint);


    }
}
