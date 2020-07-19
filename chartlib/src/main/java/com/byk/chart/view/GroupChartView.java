package com.byk.chart.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.core.view.GestureDetectorCompat;

import com.byk.chart.bean.ChartPoint;
import com.byk.chart.listener.OnChartChangeListener;
import com.byk.chart.listener.OnLongPressSlideListener;

import java.util.ArrayList;
import java.util.List;


/**
 * 处理手指触摸事件
 */
public class GroupChartView extends RelativeLayout implements ScaleGestureDetector.OnScaleGestureListener, GestureDetector.OnGestureListener {
    private OnChartChangeListener mOnChartChangeListener;

    protected GestureDetectorCompat mDetector;
    protected ScaleGestureDetector mScaleDetector;

    private List<IChartView> mChartViews = new ArrayList<>();


    private boolean isScaleing = false;

    private boolean isLongPress = false;

    public GroupChartView(Context context) {
        this(context,null);
    }

    public GroupChartView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GroupChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mScaleDetector = new ScaleGestureDetector(getContext(), this);
        mDetector = new GestureDetectorCompat(getContext(), this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mChartViews.clear();
        mChartViews.addAll(searchChartView(this));

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:{
                if (isLongPress()){
                    IChartView chartView = getChild((int) event.getRawX(),(int) event.getRawY());
                    if (chartView != null){
                        MotionEvent motionEvent = getMotionEvent(chartView, event);
                        ((OnLongPressSlideListener)chartView).onLongPressSlide(motionEvent);
                        onPressListener(chartView);
                    }
                }
            }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:{
                if (isLongPress()){
                    IChartView chartView = getChild((int) event.getRawX(),(int) event.getRawY());
                    if (chartView != null){
                        MotionEvent motionEvent = getMotionEvent(chartView, event);
                        ((OnLongPressSlideListener)chartView).onLongPressFinish(motionEvent);
                        onPressListener(chartView);
                    }else {
                        int count = mChartViews.size();
                        if (count > 0){
                            ((OnLongPressSlideListener)mChartViews.get(0)).onLongPressFinish(event);
                            onPressListener(mChartViews.get(0));
                        }
                    }

                    setLongPress(false);
                }

            }
                break;
        }

        mScaleDetector.onTouchEvent(event);
        mDetector.onTouchEvent(event);

        return true;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        System.out.println("group--------------onScale");

        // 获取第一个子控件实现放缩
        int count = mChartViews.size();
        if (count > 0){
            mChartViews.get(0).getGestureTouchListener().onScale(detector);
        }

        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        System.out.println("group--------------onScaleBegin");
        isScaleing = true;
        int count = mChartViews.size();
        if (count > 0){
            mChartViews.get(0).getGestureTouchListener().onScaleBegin(detector);
        }
        return isScaleing;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        System.out.println("group--------------onScaleEnd");
        int count = mChartViews.size();
        if (count > 0){
            mChartViews.get(0).getGestureTouchListener().onScaleEnd(detector);
        }
        isScaleing = false;
    }


    @Override
    public boolean onDown(MotionEvent e) {
        int count = mChartViews.size();
        if (count > 0){
            mChartViews.get(0).getGestureTouchListener().onDown(e);
        }
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        int count = mChartViews.size();
        if (count > 0){
            mChartViews.get(0).getGestureTouchListener().onScroll(e1,e2,distanceX,distanceY);
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        IChartView chartView = getChild((int) event.getRawX(),(int) event.getRawY());
        setLongPress(true);
        if (chartView != null){
            MotionEvent motionEvent = getMotionEvent(chartView, event);
            ((OnLongPressSlideListener)chartView).onLongPressBegin(motionEvent);

            onPressListener(chartView);
        }
    }

    /**
     * 手指长按监听回调
     * @param chartView
     */
    private void onPressListener(IChartView chartView){
        if (mOnChartChangeListener != null){
            ChartPoint chartPoint = chartView.getAdapter().getDataProvider().getChartPoint();
            if (chartPoint!= null){
                mOnChartChangeListener.onLongPress((int) chartPoint.x);
            }
        }
    }

    private void onDisplayChangeListener(IChartView chartView){
        if (mOnChartChangeListener != null){
            ChartPoint chartPoint = chartView.getAdapter().getDataProvider().getChartPoint();
            if (chartPoint!= null){
                mOnChartChangeListener.onDisplayChange(chartView.getAdapter().getDataProvider().getStartPosition(),chartView.getAdapter().getDataProvider().getEndPosition());
            }
        }
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return true;
    }

    private IChartView getChild(int x,int y){
        int count = mChartViews.size();

        IChartView chartView = null;

        for (int i = 0; i < count; i++) {
            ChartView temp = (ChartView) mChartViews.get(i);
            Rect rect = new Rect();
            int[] location = new int[2];
            temp.getLocationOnScreen(location);

            rect.left = location[0];
            rect.top = location[1];
            rect.right = rect.left + temp.getMeasuredWidth();
            rect.bottom = rect.top + temp.getMeasuredHeight();
            boolean flag = rect.contains(x,y);
            if (flag){
                chartView = temp;
                break;
            }
        }
        return chartView;
    }

    private MotionEvent getMotionEvent(IChartView chartView,MotionEvent event){
        int[] location = new int[2];
        ((ChartView)chartView).getLocationOnScreen(location);

        float localX = event.getRawX() - location[0];
        float localY = event.getRawY() - location[1];

        MotionEvent obtain = MotionEvent.obtain(event);
        obtain.setLocation(localX,localY);

        return obtain;
    }

    /**
     * 遍历寻找所有图形绘制的视图
     */
    private List<IChartView> searchChartView(View view){
        List<IChartView> chartViews = new ArrayList<>();

        if (view == null){
            return chartViews;
        }

        if (view instanceof ViewGroup){
            int count = ((ViewGroup)view).getChildCount();

            for (int i = 0; i < count; i++) {
                View childAt = ((ViewGroup) view).getChildAt(i);

                if (childAt instanceof IChartView){
                    chartViews.add((IChartView) childAt);
                }else {
                     chartViews.addAll(searchChartView(childAt));
                }
            }
        }
        
        return chartViews;
        
    }

    public boolean isScaleing() {
        return isScaleing;
    }

    public void setScaleing(boolean scaleing) {
        isScaleing = scaleing;
    }

    public boolean isLongPress() {
        return isLongPress;
    }

    public void setLongPress(boolean longPress) {
        isLongPress = longPress;
    }

    public void setOnChartChangeListener(OnChartChangeListener mOnChartChangeListener) {
        this.mOnChartChangeListener = mOnChartChangeListener;
    }
}
