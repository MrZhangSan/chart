package com.byk.chartlib.draw;

import android.graphics.Canvas;

import com.byk.chartlib.data.DataProvider;
import com.byk.chartlib.format.ValueFormatter;

public abstract class MarkerDrawer extends BaseDrawer{
    protected ValueFormatter valueFormatter;

    public MarkerDrawer(DataProvider mDataProvider) {
        super(mDataProvider);
        valueFormatter = new ValueFormatter();
    }

    public abstract void draw(Canvas canvas);
}
