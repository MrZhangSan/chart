package com.byk.chart.bean;

import android.graphics.PointF;

/**
 * 坐标点处理
 */
public class ChartPoint extends PointF {
    public float xPx;
    public float yPx;

    public float screenX;

    public float screenY;

    public ChartPoint() {
    }

    public ChartPoint(float x, float y) {
        super(x, y);
    }

    public float getxPx() {
        return xPx;
    }

    public void setxPx(float xPx) {
        this.xPx = xPx;
    }

    public float getyPx() {
        return yPx;
    }

    public void setyPx(float yPx) {
        this.yPx = yPx;
    }

    public float getScreenX() {
        return screenX;
    }

    public void setScreenX(float screenX) {
        this.screenX = screenX;
    }

    public float getScreenY() {
        return screenY;
    }

    public void setScreenY(float screenY) {
        this.screenY = screenY;
    }
}
