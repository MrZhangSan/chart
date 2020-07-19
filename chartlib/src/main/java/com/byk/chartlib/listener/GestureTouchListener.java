package com.byk.chartlib.listener;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import androidx.core.view.GestureDetectorCompat;

import com.byk.chartlib.adapter.IAdapter;
import com.byk.chartlib.data.DataProvider;
import com.byk.chartlib.view.ChartView;

public class GestureTouchListener<T extends ChartView> implements
        GestureDetector.OnGestureListener,
        ScaleGestureDetector.OnScaleGestureListener{

    private T mChartView;

    // 最后一次滑动位置
    private MotionEvent mLastMotion;

    private boolean mScrollEnable = true;

    //是否支持放缩
    private boolean mScaleEnable = true;

    private boolean mIsLongPress = false;

    //是否处于触摸状态
    protected boolean mIsTouch = false;

    //是否正处于滚动状态
    private boolean mIsScrolling = false;

    // 是否在放缩状态
    private boolean mIsScale = false;

    //平移因子
    protected float  mScrollFactor = 0.9f;

    //放缩因子
    protected float  mSccleFactor = 0.5f;

    //最近一次两个放缩手指的距离
    private float mPreviousSpanX;

    public GestureTouchListener(T chartView) {
        this.mChartView = chartView;

    }


    @Override
    public boolean onDown(MotionEvent e) {
        mLastMotion = MotionEvent.obtain(e);
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if (isLongPress()){
            mIsLongPress = false;

            //TODO 长按下显示处理
        }else {
            //TODO 切换显示
        }
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (!isScrollEnable()){//长按状态下，返回true,处理显示
            return false;
        }

        if (isScale()){
            return false;
        }
        System.out.println("-------------isScale"+isScale());

        IAdapter adapter = mChartView.getAdapter();
        DataProvider dataProvider = adapter.getDataProvider();

        //允许滑动的情况下，处理显示滚动
        if (Math.abs(distanceX)>Math.abs(distanceY)){
            float deltaX = e2.getX() - mLastMotion.getX();

            dataProvider.getTranformer();

            if (Math.abs(deltaX) > (dataProvider.getItemWidth()*mScrollFactor)) {
                System.out.println("-------------onScroll");
                int consume = dataProvider.translation(-(int) (deltaX / (dataProvider.getItemWidth()*mScrollFactor)));
                mLastMotion = MotionEvent.obtain(e2);

                //消费数量大于0刷新界面
                if (Math.abs(consume)>0){
                    adapter.notifyInvalidate();
                }
                adapter.notifyInvalidate();
            }
        }

        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        mLastMotion = MotionEvent.obtain(event);
        this.mIsLongPress = true;

        if (isScale()){
            return;
        }

        if (mChartView instanceof OnLongPressSlideListener){
            ((OnLongPressSlideListener)mChartView).onLongPressBegin(mLastMotion);
        }
        System.out.println("-------------onLongPressBegin" + mLastMotion.getY());
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        return true;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        if (!isScaleEnable()) {
            return false;
        }

        if (mIsScrolling){
            return false;
        }

        IAdapter adapter = mChartView.getAdapter();

        DataProvider dataProvider = adapter.getDataProvider();
        float pointWidth = dataProvider.getItemWidth();
        float deltaX = detector.getCurrentSpanX() - mPreviousSpanX;
        if (Math.abs(deltaX) > (pointWidth * mSccleFactor)) {
            int consume = dataProvider.scale(-(int) (deltaX / (pointWidth * mSccleFactor)));
            mPreviousSpanX = detector.getCurrentSpanX();

            //消费数量大于0刷新界面
            if (Math.abs(consume)>0){
                adapter.notifyInvalidate();
            }
        }

        return mIsScale;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        mIsScale = true;

        if (isLongPress()){
            return false;
        }
        mPreviousSpanX = detector.getCurrentSpanX();
        System.out.println("-------------onScaleBegin");
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        mIsScale = false;
        System.out.println("-------------onScaleEnd");
    }


    public boolean isScrollEnable() {
        return mScrollEnable;
    }


    public boolean isScaleEnable() {
        return mScaleEnable;
    }

    public boolean isScale() {
        return mIsScale;
    }

    public boolean isLongPress() {
        return mIsLongPress;
    }

}
