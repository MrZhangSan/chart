package com.byk.chartlib.draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import com.byk.chartlib.bean.ChartPoint;
import com.byk.chartlib.bean.IEntry;
import com.byk.chartlib.data.DataProvider;
import com.byk.chartlib.data.DataSet;
import com.byk.chartlib.utils.Tranformer;


public class CrossDrawer extends BaseDrawer{
    private Paint mCrossPaint;

    private int mMarkPaint;

    /**
     * 网格线宽度
     */
    protected float mGridLinesWidth = 4f;

    /**
     * 网格线颜色
     */
    protected int mGridLinesColor = 0xffff0000;


    public CrossDrawer(DataProvider mDataProvider) {
        super(mDataProvider);
        mCrossPaint = new Paint();
    }


    public void onCrossDraw(Canvas canvas){
        mCrossPaint.setAntiAlias(true);
        mCrossPaint.setStyle(Paint.Style.STROKE);
        mCrossPaint.setColor(mGridLinesColor);
        mCrossPaint.setStrokeWidth(mGridLinesWidth);


        Tranformer tranformer = mDataProvider.getTranformer();

        ChartPoint chartPoint = mDataProvider.getChartPoint();
        float x = chartPoint.x;
        float y = chartPoint.y;

        Path path = new Path();
        path.moveTo(x + 1/2f,mDataProvider.getMaxValue());
        path.lineTo(x + 1/2f,mDataProvider.getMinValue());
        tranformer.convertValueToPixel(path);
        canvas.drawPath(path,mCrossPaint);

        if (mDataProvider.isLongPress()){

            if (mDataProvider.isFollowFinger()&&mDataProvider.isTouchView() ){
                path.reset();
                path.moveTo(mDataProvider.getStartPosition(),y);
                path.lineTo(mDataProvider.getEndPosition()+1,y);
                tranformer.convertValueToPixel(path);
                canvas.drawPath(path,mCrossPaint);
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
                IEntry entry = dataSet.getEntry().get((int) chartPoint.x);

                path.reset();
                path.moveTo(mDataProvider.getStartPosition(),entry.getMaster());
                path.lineTo(mDataProvider.getEndPosition()+1,entry.getMaster());
                tranformer.convertValueToPixel(path);
                canvas.drawPath(path,mCrossPaint);
            }
        }

    }
}
