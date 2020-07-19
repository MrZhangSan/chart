package com.byk.chart.draw;

import android.graphics.Canvas;

import com.byk.chart.bean.IEntry;
import com.byk.chart.data.DataProvider;
import com.byk.chart.data.DataSet;

public abstract class DataDrawer<T extends DataSet<? extends IEntry>> extends BaseDrawer {

    public DataDrawer(DataProvider mDataProvider) {
        super(mDataProvider);
    }

    abstract public void draw(Canvas canvas, T dataSet);
}
