package com.byk.chartlib.draw;

import android.graphics.Canvas;

import com.byk.chartlib.data.DataProvider;
public abstract class BaseDrawer implements IDrawer {

    protected DataProvider mDataProvider;

    /**
     * 是否有效
     */
    private boolean mIsEnable = true;

    public BaseDrawer(DataProvider mDataProvider) {
        this.mDataProvider = mDataProvider;
    }

    public boolean isEnable() {
        return mIsEnable;
    }

    public void setEnable(boolean isEnable) {
        this.mIsEnable = isEnable;
    }
}
