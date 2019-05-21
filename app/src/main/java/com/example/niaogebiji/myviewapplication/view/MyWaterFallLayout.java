package com.example.niaogebiji.myviewapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/***
 * 瀑布布局 2019.5.21
 * 1.不涉及padding margin
 * 图片的宽度 均分 算法 = (给定的宽度 - （columns - 1）*hSpace) / columns
 * 每次添加图片，找到当前最短的列，找到最长的列
 *
 *   左坐标
 *   第一个 (childwidth + hSpace)*0 = 0
 *   第二个 (childwidth + hSpace)*1
 *   第三个 (childwidth + hSpace)*2
 *
 *   上坐标
 *   top[minColum] 默认3次为0，后面就是每列中最短的高度了
 *
 *   点击回调接口
 *
 *   ------------------------------------------------------------------------
 *
 *   改进:每次都需要调用onMeasure方法，耗性能 -- 如何改
 *
 */
public class MyWaterFallLayout extends ViewGroup {

    private int columns = 3;//默认3列
    private int hSpace = 20;//指定每个图片间的水平间距
    private int vSpace = 20;//指定每个图片间的垂直间距
    private int childWidth = 0;//图片宽度
    private int top[];//保存当前每列的高度
    private OnItemClickListener mOnItemClickListener;

    public MyWaterFallLayout(Context context) {
        this(context,null);
    }

    public MyWaterFallLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyWaterFallLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        top = new int[columns];
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        int height ;
        int width ;

        //控件的宽
        childWidth = (measureWidth - (columns - 1) * hSpace) / columns ;//（总 - 空隙）/ 个数

        clearTop();

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            int childHeight = child.getMeasuredHeight();//子View的高度
            int minColumn = getMinHeightColumn();
            top[minColumn] += childHeight + vSpace;//累加
        }

        //循环之后，集合中包含有最小，最大的每列高度
        height = getMaxHeight();

        //TODO 这步可以直接选择屏幕宽为基准
        if(count < columns){//子控件数量并没有超过三列
            width = count * childWidth + (count - 1) * hSpace;
        }else{
            width = measureWidth;
        }

        setMeasuredDimension(measureWidthMode == MeasureSpec.EXACTLY ? measureWidth :width,
                measureHeightMode == MeasureSpec.EXACTLY ? measureHeight : height);

    }

    //清空数组
    private void clearTop() {
        for (int i = 0; i < columns; i++) {
            top[i] = 0;
        }
    }

    //获取最大高度的列的长度
    private int getMaxHeight() {
        int maxHeight = 0;
        for (int i = 0; i < columns; i++) {
            if(top[i] > maxHeight){
                maxHeight = top[i];
            }
        }
        return maxHeight;
    }

    //获取最小高度的列
    private int getMinHeightColumn() {
        int minColum = 0;
        for (int i = 0; i < columns; i++) {
            //以第一项为基准，如果小于第一项，那么它更小，重新赋值
            if(top[i] < top[minColum]){
                minColum = i;
            }
        }
        return minColum;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        //不涉及padding
        int left;
        int tempTop ;

        int count = getChildCount();

        clearTop();

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int childHeight = child.getMeasuredHeight();

            //TODO (childWidth + hSpace) * i 这种方式是一个一个从左到右
            int minColum = getMinHeightColumn();
            left = (childWidth + hSpace) * minColum;//每次从最小高度处布局
            tempTop = top[minColum];

            int lc = left;
            int tc = tempTop;
            int rc = left + childWidth;
            int bc = tc + childHeight;

            child.layout(lc,tc,rc,bc);

            top[minColum] += childHeight + vSpace;
        }
    }


    public interface  OnItemClickListener{
        void onItemClick(View v,int index);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
        for (int i = 0; i < getChildCount(); i++) {
            final int index = i;
            View child = getChildAt(i);
            child.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v,index);
                }
            });
        }
    }
}
