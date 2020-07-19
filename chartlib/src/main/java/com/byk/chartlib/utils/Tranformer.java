package com.byk.chartlib.utils;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;

/**
 * 将数据转换为坐标的值
 */
public class Tranformer {
    /**
     * 将数值转换为像素
     */
    protected Matrix mMatrixValueToPx = new Matrix();

    protected Matrix mMatrixViewInvert = new Matrix();

    protected Matrix mMatrixViewOffset = new Matrix();

    /**
     * 将像素转换为数值
     */
    protected Matrix mMatrixPxToValue = new Matrix();

    protected Matrix mMatrixValueInvert = new Matrix();

    protected Matrix mMatrixValueOffset = new Matrix();


    protected ViewHandler mViewHandler;

    public Tranformer(ViewHandler viewHandler) {
        this.mViewHandler = viewHandler;
    }


    /**
     * 将数值转换成像素坐标
     * @param deltaX
     * @param deltaY
     * @param chartMinX
     * @param chartMinY
     */
    public void prepareMatrixValuePx(float deltaX,float deltaY,float chartMinX,float chartMinY){

        float scaleX = mViewHandler.getContentWidth()/deltaX;
        float scaleY = mViewHandler.getContentHeight()/deltaY;

        mMatrixValueToPx.reset();
        mMatrixValueToPx.postTranslate(-chartMinX,-chartMinY);
        mMatrixValueToPx.postScale(scaleX,scaleY);

        mMatrixViewInvert.reset();
        mMatrixViewInvert.postScale(1,-1);
        mMatrixViewInvert.postTranslate(0,mViewHandler.getContentHeight());

        mMatrixViewOffset.reset();
        mMatrixViewOffset.postTranslate(mViewHandler.getOffsetLeft(), mViewHandler.getOffsetTop());
    }

    /**
     *
     * @param deltaX
     * @param deltaY
     */
    public void prepareMatrixPxValue(float deltaX,float deltaY,float chartMinX,float chartMinY){

        float scaleX = deltaX/mViewHandler.getContentWidth();
        float scaleY = deltaY/mViewHandler.getContentHeight();

        mMatrixPxToValue.reset();
        mMatrixPxToValue.postTranslate(-mViewHandler.getOffsetLeft(),-mViewHandler.getOffsetTop());
        mMatrixPxToValue.postScale(scaleX,scaleY);

        mMatrixValueInvert.reset();
        mMatrixValueInvert.postScale(1,-1);
        mMatrixValueInvert.postTranslate(0,deltaY);

        mMatrixValueOffset.reset();
        mMatrixValueOffset.postTranslate(chartMinX,chartMinY);
    }


    /**
     * 数值点转换成像素点
     * @param pts
     */
    public void convertValuesToPixel(float[] pts){


        mMatrixValueToPx.mapPoints(pts);

        mMatrixViewInvert.mapPoints(pts);

        mMatrixViewOffset.mapPoints(pts);
    }

    /**
     *将数值转换为像素
     * @param path
     */
    public void convertValueToPixel(Path path) {
        path.transform(mMatrixValueToPx);
        path.transform(mMatrixViewInvert);
        path.transform(mMatrixViewOffset);
    }

    /**
     * 数值点转换成像素点
     * @param rectF
     */
    public void convertValuesToPixel(RectF rectF){

        mMatrixValueToPx.mapRect(rectF);
        mMatrixViewInvert.mapRect(rectF);
        mMatrixViewOffset.mapRect(rectF);
    }

    /**
     * 将像素转换成数值
     * @param pts
     */
    public void convertPixelToValues(float[] pts){
        mMatrixPxToValue.mapPoints(pts);
        mMatrixValueInvert.mapPoints(pts);
        mMatrixValueOffset.mapPoints(pts);
    }

    /**
     * 将像素转换成数值
     * @param rectF
     */
    public void convertPixelToValues(RectF rectF){
        mMatrixPxToValue.mapRect(rectF);
        //mMatrixValueInvert.mapRect(rectF);
        mMatrixValueOffset.mapRect(rectF);
    }

    /**
     * 将像素转变为数值
     * @param path
     */
    public void convertPixelToValues(Path path){
        path.transform(mMatrixPxToValue);
        //path.transform(mMatrixValueInvert);
        path.transform(mMatrixValueOffset);
    }

    public ViewHandler getViewHandler() {
        return mViewHandler;
    }
}
