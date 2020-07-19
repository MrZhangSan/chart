package com.byk.chart.draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.byk.chart.bean.LineEntry;
import com.byk.chart.data.DataProvider;
import com.byk.chart.data.LineDataSet;
import com.byk.chart.utils.Tranformer;

import java.util.List;


public class LineDrawer extends DataDrawer<LineDataSet<LineEntry>> {

    private Paint paint;

    public LineDrawer(DataProvider mDataProvider) {
        super(mDataProvider);
        paint = new Paint();
    }

    @Override
    public void draw(Canvas canvas, LineDataSet<LineEntry> dataSet) {
        int start = mDataProvider.getStartPosition();
        int end = mDataProvider.getEndPosition();

        paint.setColor(dataSet.getColor());
        paint.setStrokeWidth(dataSet.getStrokeWidth());
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        List<LineEntry> entry = dataSet.getEntry();

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

        Path path = new Path();
        List<LineEntry> lineEntries = entry.subList(start, end + 1);

        int size = lineEntries.size();
        for (int i = 0; i < size; i++) {
            LineEntry lineEntry = lineEntries.get(i);

            if (lineEntry.isDraw()){

                if (i == 0){
                    path.moveTo(start + i + 1/2f,lineEntry.getY());
                }else {
                    path.lineTo(start + i + 1/2f ,lineEntry.getY());
                }
            }

            float[] pts = new float[]{start + i + 1/2f,lineEntry.getY()};
            tranformer.convertValuesToPixel(pts);
            System.out.println("--");
        }




        tranformer.convertValueToPixel(path);

        canvas.drawPath(path,paint);


        //TODO 绘制
    }

}
