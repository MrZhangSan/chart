package com.byk.chartlib.utils;

import android.graphics.RectF;

public class ViewHandler {
    /**
     * 视图的宽度
     */
    protected float mChartWidth = 0f;

    /**
     * 视图高度
     */
    protected float mChartHeight = 0f;


    protected RectF mContentRect = new RectF();


    public ViewHandler() {

    }


    public void setChartDimens(float width, float height){

        float offsetLeft = getOffsetLeft();
        float offsetTop = getOffsetTop();
        float offsetRight = getOffsetRight();
        float offsetBottom = getOffsetBottom();
        mChartHeight = height;
        mChartWidth = width;

        setOffsets(offsetLeft, offsetTop,offsetRight,offsetBottom);
    }


    public void setOffsets(float offsetLeft, float offsetTop,float offsetRight,float offsetBottom){
        mContentRect.set(offsetLeft,offsetTop,mChartWidth - offsetRight, mChartHeight - offsetBottom);
    }


    public float getOffsetLeft(){
        return mContentRect.left;
    }


    public float getOffsetRight(){
        return mChartWidth - mContentRect.right;
    }

    public float getOffsetTop(){
        return mContentRect.top;
    }

    public float getOffsetBottom(){
        return mChartHeight - mContentRect.bottom;
    }

    public float getContentWidth(){
        return mContentRect.width();
    }

    public float getContentHeight(){
        return mContentRect.height();
    }

    public float getContentLeft(){
        return mContentRect.left;
    }

    public float getContentRight(){
        return mContentRect.right;
    }

    public float getContentTop(){
        return mContentRect.top;
    }

    public float getContentBottom(){
        return mContentRect.bottom;
    }

    public RectF getContentRect() {
        return mContentRect;
    }

    public float getChartWidth() {
        return mChartWidth;
    }

    public float getChartHeight() {
        return mChartHeight;
    }
}
