package com.byk.chart.bean;
public class LineEntry extends Entry{
    private float y;

    public LineEntry(String date, float y) {
        super(date);
        this.y = y;
    }

    @Override
    public float getMaster() {
        return y;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public float getcalculateMax() {
        return y;
    }

    @Override
    public float getcalculateMin() {
        return y;
    }
}
