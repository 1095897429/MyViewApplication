package com.example.niaogebiji.myviewapplication.view.recyclerview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/***
 * 时间轴 -- 2019.5.21
 */
public class TimeLineItemDecoration extends RecyclerView.ItemDecoration {

    private int mCircleSize = 14;//圆圈的半径
    private Paint mPaint;//画笔
    private int mPaintSize = 6;//画笔宽度
    private String mPaintColor = "#B8B8B8";//画笔默认颜色

    public TimeLineItemDecoration() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mPaintSize);
        mPaint.setColor(Color.parseColor(mPaintColor));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //使item从outRect中右移200px,outRect 可理解为item，它的left是item左撑开的距离
        Rect rect = new Rect(200, 0, 0, 0);
        outRect.set(rect);
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(canvas, parent, state);

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            //自己到父容器的距离
            int left = child.getLeft();
            int top = child.getTop();
            int bottom = child.getBottom();
            int right = child.getRight();

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int bottomMargin = params.bottomMargin;//防止在item根布局中设置marginTop和marginBottom
            int topMargin = params.topMargin;

            int leftX = left / 2;//轴线的x轴坐标
            int height = child.getHeight();//item的高度，不包含Margin

            if (childCount==1){
                canvas.drawLine(leftX, top, leftX, bottom - height / 2, mPaint);
            }else {
                if (i == 0) {
                    //(startX,startY) -> (endX,endY)
                    canvas.drawLine(leftX, top + height / 2, leftX, bottom + bottomMargin, mPaint);
                } else if (i == childCount - 1) {
                    canvas.drawLine(leftX, top - topMargin, leftX, bottom - height / 2, mPaint);
                } else {
                    canvas.drawLine(leftX, top - topMargin, leftX, bottom - height / 2, mPaint);
                    canvas.drawLine(leftX, top + height / 2, leftX, bottom + bottomMargin, mPaint);
                }
            }
            canvas.drawCircle(leftX, top + height / 2, mCircleSize, mPaint);
        }
    }
}
