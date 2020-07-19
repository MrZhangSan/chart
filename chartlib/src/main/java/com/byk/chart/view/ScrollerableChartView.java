package com.byk.chart.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.byk.chart.adapter.IAdapter;
import com.byk.chart.data.DataProvider;
import com.byk.chart.listener.GestureTouchListener;
import com.byk.chart.listener.OnLongPressSlideListener;

public class ScrollerableChartView extends ChartView implements OnLongPressSlideListener {

    GestureTouchListener mGestureTouchListener;
    
    public ScrollerableChartView(Context context) {
        this(context, null);
    }

    public ScrollerableChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollerableChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView() {
        mGestureTouchListener = new GestureTouchListener(this);
    }

    @Override
    public void setAdapter(IAdapter mAdapter) {
        super.setAdapter(mAdapter);
    }


    @Override
    public void onLongPressBegin(MotionEvent event) {
        if (mAdapter == null){
            return;
        }

        DataProvider dataProvider = mAdapter.getDataProvider();
        if (dataProvider == null){
            return;
        }

        dataProvider.setChartPoint(event.getX(),event.getY());
        dataProvider.setLongPress(true);
        dataProvider.setTouchView(true);
        onInvalidate();
    }

    @Override
    public void onLongPressSlide(MotionEvent event) {
        if (mAdapter == null){
            return;
        }

        DataProvider dataProvider = mAdapter.getDataProvider();
        if (dataProvider == null){
            return;
        }
        dataProvider.setChartPoint(event.getX(),event.getY());
        dataProvider.setLongPress(true);
        dataProvider.setTouchView(true);

        onInvalidate();
    }

    @Override
    public void onLongPressFinish(MotionEvent event) {
        if (mAdapter == null){
            return;
        }

        DataProvider dataProvider = mAdapter.getDataProvider();
        if (dataProvider == null){
            return;
        }

        dataProvider.setChartPoint(event.getX(),event.getY());
        dataProvider.setLongPress(false);
        dataProvider.setTouchView(false);
        onInvalidate();
    }

    @Override
    public GestureTouchListener getGestureTouchListener() {
        return mGestureTouchListener;
    }
}
