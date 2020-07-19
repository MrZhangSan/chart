package com.byk.chartlib.bean;

public class RectangleEntry extends Entry{
    private float high;
    public float low;

    public RectangleEntry(String date, float high, float low) {
        super(date);
        this.high = high;
        this.low = low;
    }

    @Override
    public float getcalculateMax() {
        return Math.max(high,low);
    }

    @Override
    public float getcalculateMin() {
        return Math.min(high,low);
    }
}
