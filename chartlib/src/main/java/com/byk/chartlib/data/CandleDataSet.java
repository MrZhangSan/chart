package com.byk.chartlib.data;

import com.byk.chartlib.bean.CandleEntry;
import com.byk.chartlib.bean.Entry;
import com.byk.chartlib.draw.ShapeType;

import java.util.List;

public class CandleDataSet<T extends Entry> extends DataSet<CandleEntry> {
    private static final ShapeType SHAPE_TYPE = ShapeType.CANDLE;

    /**
     * 上涨时红烛颜色
     */
    private int mRedColor;

    /**
     * 下跌时绿柱颜色
     */
    private int mGreenColor;

    /**
     * 平盘灰色柱颜色
     */
    private int mGrayColor;

    /**
     * 线条宽度
     */
    private int mStrokeWidth;

    /**
     * 上涨是否填充
     */
    private boolean mIsRedFill = false;

    /**
     * 下跌是否填充
     */
    private boolean mIsGreenFill = true;


    private float mOffsetLeft = 0f;
    private float mOfffsetRight = 0f;


    public CandleDataSet(List<CandleEntry> mEntry) {
        super(mEntry);
    }

    @Override
    public ShapeType getShapeType() {
        return SHAPE_TYPE;
    }

    public int getRedColor() {
        return mRedColor;
    }

    public void setRedColor(int mRedColor) {
        this.mRedColor = mRedColor;
    }

    public int getGreenColor() {
        return mGreenColor;
    }

    public void setGreenColor(int mGreenColor) {
        this.mGreenColor = mGreenColor;
    }

    public int getStrokeWidth() {
        return mStrokeWidth;
    }

    public void setStrokeWidth(int mStrokeWidth) {
        this.mStrokeWidth = mStrokeWidth;
    }

    public boolean isRedFill() {
        return mIsRedFill;
    }

    public void setIsRedFill(boolean mIsRedFill) {
        this.mIsRedFill = mIsRedFill;
    }

    public boolean isGreenFill() {
        return mIsGreenFill;
    }

    public void setIsGreenFill(boolean mIsGreenFill) {
        this.mIsGreenFill = mIsGreenFill;
    }

    public int getGrayColor() {
        return mGrayColor;
    }

    public void setGrayColor(int mGrayColor) {
        this.mGrayColor = mGrayColor;
    }

    public float getOffsetLeft() {
        return mOffsetLeft;
    }

    public void setOffsetLeft(float offsetLeft) {
        this.mOffsetLeft = offsetLeft;
    }

    public float getOfffsetRight() {
        return mOfffsetRight;
    }

    public void setOfffsetRight(float offfsetRight) {
        this.mOfffsetRight = offfsetRight;
    }
}
