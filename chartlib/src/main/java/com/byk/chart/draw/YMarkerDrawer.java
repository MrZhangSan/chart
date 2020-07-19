package com.byk.chart.draw;


import android.content.Context;
import android.graphics.Canvas;

import com.byk.chart.R;
import com.byk.chart.bean.ChartPoint;
import com.byk.chart.bean.IEntry;
import com.byk.chart.data.DataProvider;
import com.byk.chart.data.DataSet;
import com.byk.chart.marker.DefultMarkerView;
import com.byk.chart.marker.MarkerView;
import com.byk.chart.marker.data.MarkBean;
import com.byk.chart.utils.Tranformer;
import com.byk.chart.utils.ViewHandler;

public class YMarkerDrawer extends MarkerDrawer{

    private YMakerPosition mYMakerPosition = YMakerPosition.OUT;

    MarkerView marker;
    public enum YMakerPosition{
        OUT,INNER;
    }


    public YMarkerDrawer(Context context, DataProvider mDataProvider) {
        super(mDataProvider);
        marker = new DefultMarkerView(context, R.layout.marker_layout);
    }

    @Override
    public void draw(Canvas canvas) {
        DataSet<IEntry>[] dataSets = mDataProvider.getDataSet();

        ChartPoint chartPoint = mDataProvider.getChartPoint();
        Tranformer tranformer = mDataProvider.getTranformer();



        marker.refreshContent(new MarkBean(dataSets[0].getEntry().get((int) chartPoint.x).getDate()));

        float[] pts = new float[]{chartPoint.x, mDataProvider.getMinValue()};
        tranformer.convertValuesToPixel(pts);

        ViewHandler viewHandler = mDataProvider.getTranformer().getViewHandler();

        pts[0] = pts[0] - marker.getMeasuredWidth()/2;
        if (pts[0] + marker.getMeasuredWidth() > viewHandler.getContentRight()){
            pts[0] = viewHandler.getContentRight() - marker.getMeasuredWidth();
        }
        if (pts[0] < viewHandler.getContentLeft()){
            pts[0] = viewHandler.getContentLeft() ;
        }

        marker.drawView(canvas,pts[0],pts[1]);
    }
}
