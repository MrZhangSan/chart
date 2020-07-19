package com.byk.chart.listener;

import android.view.MotionEvent;

/**
 * 长按滑动监听
 */
public interface OnLongPressSlideListener {

    /**
     * 长按滑动
     * @param event
     */
    void onLongPressBegin(MotionEvent event);

    /**
     * 长按滑动
     * @param event
     */
    void onLongPressSlide(MotionEvent event);

    /**
     * 长按释放
     * @param event
     */
    void onLongPressFinish(MotionEvent event);
}
