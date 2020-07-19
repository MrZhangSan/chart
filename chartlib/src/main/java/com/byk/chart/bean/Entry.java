package com.byk.chart.bean;

public abstract class Entry implements IEntry {
    private String date;


    /**
     * 是否绘制
     */
    private boolean isDraw  = true;

    public Entry(String date) {
        this.date = date;
    }

    public  float getMaster(){
        return Float.NaN;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public void setDate(String date) {
        this.date = date;
    }

    public boolean isDraw() {
        return isDraw;
    }

    public void setDraw(boolean draw) {
        isDraw = draw;
    }
}
