package com.byk.chart.data;


import com.byk.chart.draw.ShapeType;
import com.byk.chart.bean.Entry;
import com.byk.chart.bean.LineEntry;

import java.util.List;

public class LineDataSet<T extends Entry> extends DataSet<LineEntry> {
    private static final ShapeType SHAPE_TYPE = ShapeType.LINE;
    /**
     * 线条颜色
     */
    private int mColor;

    /**
     * 线条宽度
     */
    private int mStrokeWidth;


    public LineDataSet(List<LineEntry> mEntry) {
        super(mEntry);
    }

    @Override
    public ShapeType getShapeType() {
        return SHAPE_TYPE;
    }

    public int getColor() {
        return mColor;
    }

    public int getStrokeWidth() {
        return mStrokeWidth;
    }

    public void setColor(int mColor) {
        this.mColor = mColor;
    }

    public void setStrokeWidth(int mStrokeWidth) {
        this.mStrokeWidth = mStrokeWidth;
    }
}
