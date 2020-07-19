package com.byk.chartlib.adapter;

import android.content.Context;
import android.graphics.Canvas;
import com.byk.chartlib.bean.IEntry;
import com.byk.chartlib.data.DataProvider;
import com.byk.chartlib.data.DataSet;
import com.byk.chartlib.draw.CrossDrawer;
import com.byk.chartlib.draw.DataDrawer;
import com.byk.chartlib.draw.DrawFactory;
import com.byk.chartlib.draw.IDrawer;
import com.byk.chartlib.draw.MarkerDrawer;
import com.byk.chartlib.draw.XAxisDrawer;
import com.byk.chartlib.draw.XMarkerDrawer;
import com.byk.chartlib.draw.YAxisDrawer;
import com.byk.chartlib.draw.YMarkerDrawer;
import com.byk.chartlib.utils.ViewHandler;

/**
 * 绘制适配器
 */
public class ChartAdapter extends BaseAdapter<DataSet<? extends IEntry>> {

    DrawFactory drawFactory = new DrawFactory();

    YAxisDrawer mYAxisDrawer;
    XAxisDrawer mXAxisDrawer;

    CrossDrawer mCrossDrawer;


    //标签
    MarkerDrawer mXMarkerDrawer;
    MarkerDrawer mYMarkerDrawer;

    public ChartAdapter(Context mContext, DataSet<?>... dataSet) {
        super(mContext, dataSet);
        mYAxisDrawer = new YAxisDrawer(mDataProvider);
        mXAxisDrawer = new XAxisDrawer(mDataProvider);
        mCrossDrawer = new CrossDrawer(mDataProvider);
        mXMarkerDrawer = new XMarkerDrawer(mContext,mDataProvider);
        mYMarkerDrawer = new YMarkerDrawer(mContext,mDataProvider);
    }

    @Override
    public void onDraw(Canvas canvas) {
        DataSet[] dataSet = mDataProvider.getDataSet();

        ViewHandler mViewHandler = mDataProvider.getTranformer().getViewHandler();
        if (dataSet == null){
            return;
        }


        mYAxisDrawer.drawGridLines(canvas);

        mXAxisDrawer.drawGridLines(canvas);

        int count = dataSet.length;

        int saveId = canvas.save();
        canvas.clipRect(mViewHandler.getContentLeft(),mViewHandler.getContentTop(),mViewHandler.getContentRight(),mViewHandler.getContentBottom());
        for (int i = 0; i < count; i++) {
            DataSet temp = dataSet[i];
            IDrawer draw = drawFactory.createDraw(this, temp.getShapeType());

            if (draw != null){
                if (draw instanceof DataDrawer){
                    ((DataDrawer)draw).draw(canvas,temp);
                }
            }
        }
        canvas.restoreToCount(saveId);

        if (mDataProvider.getChartPoint()!=null&&mDataProvider.isLongPress()) {
            mCrossDrawer.onCrossDraw(canvas);

            mXMarkerDrawer.draw(canvas);
            mYMarkerDrawer.draw(canvas);
        }
    }

    @Override
    public DataProvider getDataProvider() {
        return mDataProvider;
    }

    public YAxisDrawer getYAxisDrawer() {
        return mYAxisDrawer;
    }

    public XAxisDrawer getXAxisDrawer() {
        return mXAxisDrawer;
    }

    public MarkerDrawer getXMarkerDrawer() {
        return mXMarkerDrawer;
    }
}
