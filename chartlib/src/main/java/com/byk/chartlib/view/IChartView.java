package com.byk.chartlib.view;

import android.content.Context;

import com.byk.chartlib.adapter.IAdapter;
import com.byk.chartlib.data.DataProvider;
import com.byk.chartlib.listener.GestureTouchListener;

public interface IChartView {
    Context getContext();

    GestureTouchListener getGestureTouchListener();

    IAdapter getAdapter();

    void syncData(DataProvider dataProvider);
}
