package com.byk.chartlib.draw;

import android.content.Context;
import android.graphics.Canvas;

import com.byk.chartlib.R;
import com.byk.chartlib.bean.ChartPoint;
import com.byk.chartlib.bean.IEntry;
import com.byk.chartlib.data.DataProvider;
import com.byk.chartlib.data.DataSet;
import com.byk.chartlib.format.ValueFormatter;
import com.byk.chartlib.marker.DefultMarkerView;

import com.byk.chartlib.marker.MarkerView;
import com.byk.chartlib.marker.data.MarkBean;
import com.byk.chartlib.utils.Tranformer;
import com.byk.chartlib.utils.ViewHandler;

public class XMarkerDrawer extends MarkerDrawer {
    private MarkerView marker;
    public XMarkerDrawer(Context context,DataProvider mDataProvider) {
        super(mDataProvider);
        marker = new DefultMarkerView(context, R.layout.marker_layout);
    }

    @Override
    public void draw(Canvas canvas) {

        if (mDataProvider.isFollowFinger()&&mDataProvider.isTouchView()){
            ChartPoint chartPoint = mDataProvider.getChartPoint();

            marker.refreshContent(new MarkBean(valueFormatter.format(chartPoint.y)));

            //根据手指触摸的位置判断是左边显示，还是右边显示

            int center = mDataProvider.getStartPosition() + mDataProvider.getCurrentDisplayCount()/2;
            Tranformer tranformer = mDataProvider.getTranformer();

            if (chartPoint.x < center){

                ViewHandler viewHandler = mDataProvider.getTranformer().getViewHandler();
                float[] pts = new float[]{mDataProvider.getEndPosition()+1,chartPoint.y};
                tranformer.convertValuesToPixel(pts);
                float tranX = pts[0] - marker.getMeasuredWidth();
                float tranY = pts[1] - marker.getMeasuredHeight()/2;

                if ( viewHandler.getContentRect().bottom -  marker.getMeasuredHeight() < tranY ){
                    tranY = viewHandler.getContentRect().bottom -  marker.getMeasuredHeight();
                }
                if (viewHandler.getContentRect().top> tranY){
                    tranY = viewHandler.getContentRect().top;
                }
                marker.drawView(canvas,tranX,tranY);
            }else {

                ViewHandler viewHandler = mDataProvider.getTranformer().getViewHandler();
                float[] pts = new float[]{mDataProvider.getStartPosition(),chartPoint.y};
                tranformer.convertValuesToPixel(pts);
                float tranX = pts[0];
                float tranY = pts[1] - marker.getMeasuredHeight()/2;

                if ( viewHandler.getContentRect().bottom -  marker.getMeasuredHeight() < tranY ){
                    tranY = viewHandler.getContentRect().bottom -  marker.getMeasuredHeight();
                }
                if (viewHandler.getContentRect().top> tranY){
                    tranY = viewHandler.getContentRect().top;
                }

                marker.drawView(canvas,tranX,tranY);
            }

        }else if (mDataProvider.isMaster()){

            /**
             * 查找数据
             */
            DataSet[] dataSets = mDataProvider.getDataSet();

            DataSet<IEntry> dataSet = null;
            for (int i = 0; i < dataSets.length; i++) {
                if (dataSets[i].isMaster()){
                    dataSet = dataSets[i];
                    break;
                }
            }

            if (dataSet == null){
                return;
            }


            ChartPoint chartPoint = mDataProvider.getChartPoint();

            marker.refreshContent(new MarkBean(valueFormatter.format(chartPoint.y)));

            //根据手指触摸的位置判断是左边显示，还是右边显示

            int center = mDataProvider.getStartPosition() + mDataProvider.getCurrentDisplayCount()/2;
            Tranformer tranformer = mDataProvider.getTranformer();

            if (chartPoint.x < center){

                ViewHandler viewHandler = mDataProvider.getTranformer().getViewHandler();

                IEntry entry = dataSet.getEntry().get((int) chartPoint.x);
                float[] pts = new float[]{mDataProvider.getEndPosition()+1,entry.getMaster()};
                tranformer.convertValuesToPixel(pts);
                float tranX = pts[0] - marker.getMeasuredWidth();
                float tranY = pts[1] - marker.getMeasuredHeight()/2;

                if ( viewHandler.getContentRect().bottom -  marker.getMeasuredHeight() < tranY ){
                    tranY = viewHandler.getContentRect().bottom -  marker.getMeasuredHeight();
                }
                if (viewHandler.getContentRect().top> tranY){
                    tranY = viewHandler.getContentRect().top;
                }
                marker.drawView(canvas,tranX,tranY);
            }else {

                ViewHandler viewHandler = mDataProvider.getTranformer().getViewHandler();
                IEntry entry = dataSet.getEntry().get((int) chartPoint.x);
                float[] pts = new float[]{mDataProvider.getStartPosition(),entry.getMaster()};
                tranformer.convertValuesToPixel(pts);
                float tranX = pts[0];
                float tranY = pts[1] - marker.getMeasuredHeight()/2;

                if ( viewHandler.getContentRect().bottom -  marker.getMeasuredHeight() < tranY ){
                    tranY = viewHandler.getContentRect().bottom -  marker.getMeasuredHeight();
                }
                if (viewHandler.getContentRect().top> tranY){
                    tranY = viewHandler.getContentRect().top;
                }

                marker.drawView(canvas,tranX,tranY);
            }
        }
    }
}
