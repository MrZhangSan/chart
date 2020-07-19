package com.byk.chartlib.draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;


import com.byk.chartlib.bean.CandleEntry;
import com.byk.chartlib.data.CandleDataSet;
import com.byk.chartlib.data.DataProvider;
import com.byk.chartlib.utils.Tranformer;

import java.util.List;

public class CandleDrawer extends DataDrawer<CandleDataSet<CandleEntry>>  {
    private Paint mPaint;

    public CandleDrawer(DataProvider mDataProvider) {
        super(mDataProvider);
        mPaint = new Paint();
    }

    @Override
    public void draw(Canvas canvas, CandleDataSet<CandleEntry> dataSet) {

        mPaint.setStrokeWidth(dataSet.getStrokeWidth());
        mPaint.setAntiAlias(true);

        int start = mDataProvider.getStartPosition();
        int end = mDataProvider.getEndPosition();

        List<CandleEntry> entry = dataSet.getEntry();

        //没有数据则不绘制
        if (entry == null||entry.isEmpty()){
            return;
        }

        int count = entry.size();
        if (start < 0) {
            start = 0;
        }
        if (end > count) {
            end = count;
        }

        Tranformer tranformer = mDataProvider.getTranformer();

        List<CandleEntry> candleEntries = entry.subList(start, end + 1);

        int size = candleEntries.size();

        for (int i = 0; i < size; i++) {
            CandleEntry candleEntry = candleEntries.get(i);

            float left = start + i;

            float right = left + 1;
            float top;
            float bottom;

            if (candleEntry.getClose()>candleEntry.getOpen()){
                mPaint.setColor(dataSet.getRedColor());
                mPaint.setStyle(Paint.Style.STROKE);
                top = candleEntry.getClose();
                bottom = candleEntry.getOpen();
            }else if (candleEntry.getClose()<candleEntry.getOpen()){
                mPaint.setColor(dataSet.getGreenColor());
                mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                top = candleEntry.getOpen();
                bottom = candleEntry.getClose();
            }else {
                mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                mPaint.setColor(dataSet.getGrayColor());
                top = candleEntry.getOpen();
                bottom = candleEntry.getClose();
            }

            RectF rectF = new RectF(left,top,right,bottom);

            tranformer.convertValuesToPixel(rectF);
            rectF.left = rectF.left + dataSet.getOffsetLeft();
            rectF.right = rectF.right - dataSet.getOfffsetRight();
            canvas.drawRect(rectF,mPaint);
        }
    }
}
