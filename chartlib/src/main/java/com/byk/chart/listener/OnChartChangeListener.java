package com.byk.chart.listener;

/**
 * 点击事件监听
 */
public interface OnChartChangeListener {
    void onLongPress(int position);
    void onDisplayChange(int start,int end);
}
