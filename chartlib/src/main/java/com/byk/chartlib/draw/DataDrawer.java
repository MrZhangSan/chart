package com.byk.chartlib.draw;

import android.graphics.Canvas;

import com.byk.chartlib.bean.IEntry;
import com.byk.chartlib.data.DataProvider;
import com.byk.chartlib.data.DataSet;

public abstract class DataDrawer<T extends DataSet<? extends IEntry>> extends BaseDrawer {

    public DataDrawer(DataProvider mDataProvider) {
        super(mDataProvider);
    }

    abstract public void draw(Canvas canvas, T dataSet);
}
