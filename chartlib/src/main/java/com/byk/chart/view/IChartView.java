package com.byk.chart.view;

import android.content.Context;

import com.byk.chart.adapter.IAdapter;
import com.byk.chart.data.DataProvider;
import com.byk.chart.listener.GestureTouchListener;

public interface IChartView {
    Context getContext();

    GestureTouchListener getGestureTouchListener();

    IAdapter getAdapter();

    void syncData(DataProvider dataProvider);
}
