package com.byk.chart.bean;

public class CandleEntry extends Entry {
    private float high;
    private float low;
    private float open;
    private float close;

    public CandleEntry(String date, float high, float low, float open, float close) {
        super(date);
        this.high = high;
        this.low = low;
        this.open = open;
        this.close = close;
    }

    @Override
    public float getMaster(){
        return close;
    }

    @Override
    public float getcalculateMax() {
        return high;
    }

    @Override
    public float getcalculateMin() {
        return low;
    }

    public float getHigh() {
        return high;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public float getLow() {
        return low;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public float getOpen() {
        return open;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public float getClose() {
        return close;
    }

    public void setClose(float close) {
        this.close = close;
    }
}
