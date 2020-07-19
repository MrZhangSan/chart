package com.byk.chart.draw;

import android.graphics.Canvas;

import com.byk.chart.data.DataProvider;
import com.byk.chart.format.ValueFormatter;

public abstract class MarkerDrawer extends BaseDrawer{
    protected ValueFormatter valueFormatter;

    public MarkerDrawer(DataProvider mDataProvider) {
        super(mDataProvider);
        valueFormatter = new ValueFormatter();
    }

    public abstract void draw(Canvas canvas);
}
