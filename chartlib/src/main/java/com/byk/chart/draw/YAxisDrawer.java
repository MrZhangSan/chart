package com.byk.chart.draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.byk.chart.bean.IEntry;
import com.byk.chart.data.DataProvider;
import com.byk.chart.data.DataSet;
import com.byk.chart.utils.TextUtils;
import com.byk.chart.utils.Tranformer;
import com.byk.chart.utils.ViewHandler;

import java.util.List;

public class YAxisDrawer extends AxisDrawer {
    public YAxisDrawer(DataProvider mDataProvider) {
        super(mDataProvider);
        mOutLine = OutLine.TOP_BOTTOM;
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

        float itemWidth = (float)mDataProvider.getCurrentDisplayCount()/(float)(mGridLinesCount -1);
        for (int i = 0; i < mGridLinesCount; i++) {
            path.reset();

            float[] pts = new float[]{itemWidth*i,mDataProvider.getMaxValue()};
            tranformer.convertValuesToPixel(pts);
            float[] pts2 = new float[]{itemWidth*i,mDataProvider.getMinValue()};
            tranformer.convertValuesToPixel(pts2);

            path.moveTo(itemWidth*i + start,mDataProvider.getMaxValue());
            path.lineTo(itemWidth*i + start,mDataProvider.getMinValue());

            tranformer.convertValueToPixel(path);
            drawGridLine(canvas,path);
        }

        //外部轮廓显示
        drawOutLine(canvas);

        //绘制文字
        drawTexts(canvas);
    }


    private void drawOutLine(Canvas canvas){

        switch (mOutLine){
            case TOP:
                drawOutLineTop(canvas);
                break;
            case BOTTOM:
                drawOutLineBottom(canvas);
                break;
            case TOP_BOTTOM:
                drawOutLineTop(canvas);
                drawOutLineBottom(canvas);
                break;
        }

    }

    /**
     * 顶部轮廓
     * @param canvas
     */
    private void drawOutLineTop(Canvas canvas){
        ViewHandler viewHandler = mDataProvider.getTranformer().getViewHandler();

        canvas.drawLine(viewHandler.getContentLeft(),viewHandler.getContentTop(),viewHandler.getContentLeft(),0,mGridLinePaint);

        canvas.drawLine(viewHandler.getContentRight(),viewHandler.getContentTop(),viewHandler.getContentRight(),0,mGridLinePaint);

        canvas.drawLine(viewHandler.getContentLeft(),mGridLinesWidth/2,viewHandler.getContentRight(),mGridLinesWidth/2,mGridLinePaint);
    }

    /**
     * 底部轮廓
     * @param canvas
     */
    private void drawOutLineBottom(Canvas canvas){
        ViewHandler viewHandler = mDataProvider.getTranformer().getViewHandler();

        canvas.drawLine(viewHandler.getContentLeft(),viewHandler.getContentBottom(),viewHandler.getContentLeft(),viewHandler.getChartHeight(),mGridLinePaint);

        canvas.drawLine(viewHandler.getContentRight(),viewHandler.getContentBottom(),viewHandler.getContentRight(),viewHandler.getChartHeight(),mGridLinePaint);

        canvas.drawLine(viewHandler.getContentLeft(),viewHandler.getChartHeight()- mGridLinesWidth/2,viewHandler.getContentRight(),viewHandler.getChartHeight()- mGridLinesWidth/2,mGridLinePaint);
    }

    /**
     * 绘制网格
     * @param canvas
     * @param path
     */
    private void drawGridLine(Canvas canvas,Path path){
        canvas.drawPath(path,mGridLinePaint);
    }

    @Override
    public void drawTexts(Canvas canvas) {
        mTextPaint.setTextSize(getTextSize());
        mTextPaint.setColor(getTextColor());
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        DataSet<IEntry>[] dataSet = mDataProvider.getDataSet();

        if (dataSet == null||dataSet.length == 0){
            return;
        }
        int start = mDataProvider.getStartPosition();
        int end = mDataProvider.getEndPosition();


        DataSet<IEntry> dataset = dataSet[0];
        List<IEntry> iEntries = dataset.getEntry().subList(start, end + 1);

        Tranformer tranformer = mDataProvider.getTranformer();

        float itemWidth = (float)mDataProvider.getCurrentDisplayCount()/(float)(mGridLinesCount -1);
        for (int i = 0; i < mGridLinesCount; i++) {



            float[] pts = new float[]{itemWidth*i + start,mDataProvider.getMinValue()};
            tranformer.convertValuesToPixel(pts);


            float labelLineWidth = TextUtils.calcTextWidth(mTextPaint, "99999999");
            float labelLineHeight = TextUtils.calcTextHeight(mTextPaint, "99999999");
            if (i == 0){
                canvas.drawText(iEntries.get(0).getDate(),pts[0] + labelLineWidth/2,pts[1]+ labelLineHeight,mTextPaint);
            }else if (i == mGridLinesCount -1){
                canvas.drawText(iEntries.get(iEntries.size() -1).getDate(),pts[0] - labelLineWidth/2,pts[1]+ labelLineHeight ,mTextPaint);
            }else {
                canvas.drawText(iEntries.get((int) (itemWidth*i)).getDate(),pts[0],pts[1]+ labelLineHeight,mTextPaint);
            }
        }

    }

}
