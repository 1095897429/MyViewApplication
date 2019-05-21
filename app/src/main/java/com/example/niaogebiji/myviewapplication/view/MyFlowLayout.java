package com.example.niaogebiji.myviewapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/***
 * 流式布局 2019.5.20
 * 1.没涉及VG自身的padding -- ok
 * 2.自身的margin是被父类计算在内了,比如LinearLayout下方的VG,写入VG的margin，LinearLayout是计算过的 -- ok
 * 3.涉及VG自己的padding -- ①在计算换行时 ②最后设定宽高 ③默认布局时的坐标
 * 4.调整了宽度最大值的获取 -- 2019.5.21
 * 5.调整了layout时换行时left的重新赋值 -- 2019.5.21
 *
 * 点击事件
 */
public class MyFlowLayout extends ViewGroup {

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public MyFlowLayout(Context context) {
        super(context);
    }

    public MyFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        //记录每一行 每一列的变量
        int lineWidth = 0;
        int lineHeight = 0;


        //最后测量的宽高
        int height = 0;
        int width = 0;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child  = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);//去掉VG自身的padding了的剩余空间

            //子控件margin
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            //当前子空间实际占据的高度
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            //当前子空间实际占据的宽度
            int childWidth =  child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;

            int paddingLeft = getPaddingLeft();
            int paddingRight = getPaddingRight();
            //TODO 换行条件判断 -- 减去VG自身的padding = 控件真正能给予的宽度
           if(lineWidth + childWidth > measureWidth - paddingLeft - paddingRight){

               width = Math.max(lineWidth,childWidth);
               height += lineHeight;//高度累加
               //因为由于盛不下当前控件，而将此控件调到下一行，所以将此控件的高度和宽度初始化给lineHeight、lineWidth
               lineWidth = childWidth;
               lineHeight = childHeight;
           }else{
               lineWidth += childWidth;//宽度累加
               lineHeight = Math.max(lineHeight,childHeight);
           }

           //最后一行设值 -- 加入VG自身的padding
            if(i == count - 1){
                //TODO 别忘了控件自身的top 和 bottom
               height += lineHeight + getPaddingTop() + getPaddingBottom();

               width = Math.max(width,measureWidth);
                Log.e("tag","控件的最大宽度：" + width);
            }
        }

        //最后通过👇方式设值
        setMeasuredDimension((measureWidthMode == MeasureSpec.AT_MOST?width :measureWidth),
                measureHeightMode == MeasureSpec.AT_MOST?height:measureHeight);
    }

    //其实就是找到控件的左坐标点
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //当前坐标的top left -- 需要加上VG自身的padding
        int top = 0 + getPaddingTop();
        int left = 0 + getPaddingLeft();
        //记录每一行 每一列的变量
        int lineWidth = 0;
        int lineHeight = 0;
        int count = getChildCount();

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);

            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            //当前子空间实际占据的高度 宽度
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;



            if(lineWidth + childWidth > getMeasuredWidth() - getPaddingLeft() - getPaddingRight()){
                top += childHeight;//下一次重新赋值
                left = 0 + getPaddingLeft();//注意换行时也是默认的值
                lineHeight = childHeight;
                lineWidth = childWidth;
            }else{
                lineWidth += childWidth;//宽度累加
                lineHeight = Math.max(lineHeight,childHeight);
            }

            //计算childView的left,top,right,bottom
            int lc  = left + lp.leftMargin;
            int tc = top + lp.topMargin;

            int rc = lc + child.getMeasuredWidth();
            int bc = tc + child.getMeasuredHeight();
            child.layout(lc,tc,rc,bc);

            left += childWidth;//下一次重新赋值 -- 更新

        }
    }

    //⬇下方是重写的部分

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
    }


    public interface  OnItemClickListener{
        void onItemClick(View v,int index);
    }
}
