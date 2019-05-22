package com.example.niaogebiji.myviewapplication.view.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/***
 * 2019.5.22
 */
public class CustomLayoutManager extends RecyclerView.LayoutManager {

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
    }


    private int mItemWidth,mItemHeight;

    //所有item布局处理的地方
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);


        if(getItemCount() == 0){
            detachAndScrapAttachedViews(recycler);
            return;
        }

        detachAndScrapAttachedViews(recycler);

        //取第一个View
        View childView = recycler.getViewForPosition(0);
        measureChildWithMargins(childView,0,0);
        mItemHeight = getDecoratedMeasuredHeight(childView);
        mItemWidth = getDecoratedMeasuredWidth(childView);

        //一屏幕显示的item个数
        int visibleCount = getVerticalSpace() / mItemHeight ;

        int offsetY = 0;
        for (int i = 0; i < getItemCount(); i++) {
            View child = recycler.getViewForPosition(i);//通过制定位置获取到一个ViewHolder实例
            addView(child);
            measureChildWithMargins(child,0,0);
            int width = getDecoratedMeasuredWidth(child);
            int height = getDecoratedMeasuredHeight(child);
            layoutDecorated(child,0,offsetY,width,offsetY + height);
            offsetY += height;
        }

        //如果所有子View的高度和没有填满RecyclerView的高度，
        // 则将高度设置为RecyclerView的高度
        mTotalHeight = Math.max(offsetY,getVerticalSpace());
    }


    private int getVerticalSpace(){
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    //让其可以垂直滑动
    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public boolean canScrollHorizontally() {
        return false;
    }

    private int mTotalHeight = 0;
    private int mSumDy = 0;
    //获取RecyclerView子对象的滚动距离
    //手指由下往上移动（80 - 60） dy > 0 起始点 - 终结点 = 偏移量
    //手指向下运动 （60 - 90）dy < 0 起始点 - 终结点 = 偏移量
    //相当于recycler内部调用scrollTo一样，正反混弄
    //这里可以联想 invalidate时 --  https://blog.csdn.net/yanbober/article/details/50419117
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {

        //顶部判断 所有的dy加起来 < 0，默认为0
        int travle = dy;
        if(mSumDy + dy < 0){//当移出屏幕的距离（22） + （-32）滚动的距离小于0时，到头了，将移出屏幕的距离赋值给变量，移动即可
            travle =- mSumDy;
        }else if(mSumDy + dy > mTotalHeight - getVerticalSpace()){//mSumDy可以理解为超出屏幕的距离 + dy + 一屏幕的高度 > mTotalHeight
            //如果滑动到最底部
            travle = mTotalHeight - getVerticalSpace() - mSumDy;//取 总的 - 移出屏幕的高度 ，不采用dy
        }

        mSumDy += travle;//如果不走上面的判断👆，那么会累加滑动的偏移量，总的高度🈶了

        offsetChildrenVertical(-travle);
        return dy;
    }
}
