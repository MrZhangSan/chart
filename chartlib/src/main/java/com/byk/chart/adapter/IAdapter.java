package com.byk.chart.adapter;


import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;

import com.byk.chart.draw.ShapeType;
import com.byk.chart.bean.IEntry;
import com.byk.chart.data.DataProvider;
import com.byk.chart.data.DataSet;
import com.byk.chart.draw.IDrawer;

/**
 * 数据适配器基类
 */
public interface IAdapter<T extends DataSet<? extends IEntry>> {
    Context getContext();

    /**
     * 绘制图形
     * @param canvas
     */
    void onDraw(Canvas canvas);


    DataProvider getDataProvider();

    IDrawer getDrawer(ShapeType shapeType);

    /**
     * 注册一个数据观察者
     *
     * @param observer 数据观察者
     */
    void registerDataSetObserver(DataSetObserver observer);

    /**
     * 移除一个数据观察者
     *
     * @param observer 数据观察者
     */
    void unregisterDataSetObserver(DataSetObserver observer);

    /**
     * 当数据发生变化时调用
     */
    void notifyDataSetChanged();

    /**
     * 通知刷新显示
     */
    void notifyInvalidate();

}
