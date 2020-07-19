package com.byk.chartlib.draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.byk.chartlib.data.DataProvider;
import com.byk.chartlib.utils.TextUtils;
import com.byk.chartlib.utils.Tranformer;
import com.byk.chartlib.utils.ViewHandler;

public class XAxisDrawer extends AxisDrawer {

    public XAxisDrawer(DataProvider mDataProvider) {
        super(mDataProvider);
        mOutLine = OutLine.LEFT_RIGHT;
    }

    @Override
    public void drawGridLines(Canvas canvas) {
        int start = mDataProvider.getStartPosition();
        int end = mDataProvider.getEndPosition();

        mGridLinePaint.setStrokeWidth(mGridLinesWidth);
        mGridLinePaint.setStyle(Paint.Style.STROKE);
        mGridLinePaint.setColor(mGridLinesColor);
        mGridLinePaint.setAntiAlias(true);

        Path path = new Path();
        Tranformer tranformer = mDataProvider.getTranformer();

        float itemWidth = (mDataProvider.getMaxValue()- mDataProvider.getMinValue()) /(mGridLinesCount -1);

        for (int i = 0; i < mGridLinesCount; i++) {
            path.reset();

            float[] pts = new float[]{start,itemWidth*i};
            tranformer.convertValuesToPixel(pts);
            float[] pts2 = new float[]{end,itemWidth*i};
            tranformer.convertValuesToPixel(pts2);

            path.moveTo(start,itemWidth*i + mDataProvider.getMinValue());
            path.lineTo(end + 1,itemWidth*i + mDataProvider.getMinValue());

            tranformer.convertValueToPixel(path);
            drawGridLine(canvas,path);

            drawOutLine(canvas);

            drawTexts(canvas);
        }
    }


    private void drawOutLine(Canvas canvas){

        switch (mOutLine){
            case LEFT:
                drawOutLineLeft(canvas);
                break;
            case RIGHT:
                drawOutLineRight(canvas);
                break;
            case LEFT_RIGHT:
                drawOutLineLeft(canvas);
                drawOutLineRight(canvas);
                break;
        }

    }

    /**
     * 左边轮廓
     * @param canvas
     */
    private void drawOutLineLeft(Canvas canvas){
        ViewHandler viewHandler = mDataProvider.getTranformer().getViewHandler();

        canvas.drawLine(0,viewHandler.getContentTop(),viewHandler.getContentLeft(),viewHandler.getContentTop(),mGridLinePaint);

        canvas.drawLine(0,viewHandler.getContentBottom(),viewHandler.getContentLeft(),viewHandler.getContentBottom(),mGridLinePaint);

        canvas.drawLine(mGridLinesWidth/2,viewHandler.getContentTop(),mGridLinesWidth/2,viewHandler.getContentBottom(),mGridLinePaint);
    }

    /**
     * 右边轮廓
     * @param canvas
     */
    private void drawOutLineRight(Canvas canvas){
        ViewHandler viewHandler = mDataProvider.getTranformer().getViewHandler();

        canvas.drawLine(viewHandler.getChartWidth(),viewHandler.getContentTop(),viewHandler.getContentRight(),viewHandler.getContentTop(),mGridLinePaint);

        canvas.drawLine(viewHandler.getChartWidth(),viewHandler.getContentBottom(),viewHandler.getContentRight(),viewHandler.getContentBottom(),mGridLinePaint);

        canvas.drawLine(viewHandler.getChartWidth() - mGridLinesWidth/2,viewHandler.getContentTop(),viewHandler.getChartWidth() - mGridLinesWidth/2,viewHandler.getContentBottom(),mGridLinePaint);
    }


    private void drawGridLine(Canvas canvas,Path path){
        canvas.drawPath(path,mGridLinePaint);
    }

    @Override
    public void drawTexts(Canvas canvas) {
        mTextPaint.setTextSize(getTextSize());
        mTextPaint.setColor(getTextColor());
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        switch (mTextPosition){
            case BOTH_INSIDE:{
                mTextPaint.setTextAlign(Paint.Align.LEFT);
                drawText(canvas,mDataProvider.getStartPosition());

                mTextPaint.setTextAlign(Paint.Align.RIGHT);
                drawText(canvas,mDataProvider.getEndPosition() + 1);
            }
                break;
            case BOTH_OUTSIDE:
                mTextPaint.setTextAlign(Paint.Align.RIGHT);
                drawText(canvas,mDataProvider.getStartPosition());

                mTextPaint.setTextAlign(Paint.Align.LEFT);
                drawText(canvas,mDataProvider.getEndPosition() + 1);
                break;
            case LEFT_INSIDE:
                mTextPaint.setTextAlign(Paint.Align.LEFT);
                drawText(canvas,mDataProvider.getStartPosition());
                break;
            case RIGHT_INSID:
                mTextPaint.setTextAlign(Paint.Align.RIGHT);
                drawText(canvas,mDataProvider.getEndPosition() + 1);
                break;
            case LEFT_OUTSIDE:
                mTextPaint.setTextAlign(Paint.Align.RIGHT);
                drawText(canvas,mDataProvider.getStartPosition());
                break;
            case RIGHT_OUTSIDE:
                mTextPaint.setTextAlign(Paint.Align.LEFT);
                drawText(canvas,mDataProvider.getEndPosition() + 1);
                break;
        }
    }

    private void drawText(Canvas canvas,int position){
        float maxValue = mDataProvider.getMaxValue();
        float minValue = mDataProvider.getMinValue();

        int count = getGridLinesCount();
        float delta = maxValue - minValue;

        float dist = delta/(count-1);

        Tranformer tranformer = mDataProvider.getTranformer();

        for (int i = 0; i < count; i++) {
            float value = minValue + i*dist;
            float[] pts = new float[]{position,value};
            tranformer.convertValuesToPixel(pts);

            float labelLineHeight = TextUtils.calcTextHeight(mTextPaint, value+"");
            if (i == 0){
                canvas.drawText(mValueFormatter.format(value),pts[0],pts[1],mTextPaint);
            }else if (i == count -1){
                canvas.drawText(mValueFormatter.format(value),pts[0],pts[1] + labelLineHeight,mTextPaint);
            }else {
                canvas.drawText(mValueFormatter.format(value),pts[0],pts[1] + labelLineHeight/2,mTextPaint);
            }
        }
    }
}
