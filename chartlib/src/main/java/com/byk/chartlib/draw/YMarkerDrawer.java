package com.byk.chartlib.draw;


import android.content.Context;
import android.graphics.Canvas;

import com.byk.chartlib.R;
import com.byk.chartlib.bean.ChartPoint;
import com.byk.chartlib.bean.IEntry;
import com.byk.chartlib.data.DataProvider;
import com.byk.chartlib.data.DataSet;
import com.byk.chartlib.marker.DefultMarkerView;
import com.byk.chartlib.marker.MarkerView;
import com.byk.chartlib.marker.data.MarkBean;
import com.byk.chartlib.utils.Tranformer;
import com.byk.chartlib.utils.ViewHandler;

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
