package com.byk.chart.listener;

import android.view.MotionEvent;

public interface IGestureTouchListener {
    boolean onInterceptTouchEvent(MotionEvent ev);

    boolean onTouchEvent(MotionEvent event);
}
