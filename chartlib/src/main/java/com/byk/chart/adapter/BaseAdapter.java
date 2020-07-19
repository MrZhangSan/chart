package com.byk.chart.adapter;

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;

import com.byk.chart.draw.ShapeType;
import com.byk.chart.bean.IEntry;
import com.byk.chart.data.DataProvider;
import com.byk.chart.data.DataSet;
import com.byk.chart.draw.IDrawer;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseAdapter<T extends DataSet<? extends IEntry>> implements IAdapter<T> {
    /**
     * 上下文
     */
    protected Context mContext;

    /**
     * 数据源
     */
    protected DataProvider mDataProvider;

    /**
     * 绘制句柄
     */
    protected Map<ShapeType, IDrawer> mDraws = new HashMap<>();

    private DataSetObservable mDataSetObservable = new DataSetObservable();


    public BaseAdapter(Context mContext, DataSet... dataSet) {
        this.mContext = mContext;
        mDataProvider = new DataProvider();
        mDataProvider.setDataSet(dataSet);
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public DataProvider getDataProvider() {
        return mDataProvider;
    }

    @Override
    public IDrawer getDrawer(ShapeType shapeType) {
        IDrawer iDrawer = mDraws.get(shapeType);
        if (iDrawer == null){
            iDrawer = shapeType.create(mDataProvider);
        }
        addDrawer(iDrawer,shapeType);
        return iDrawer;
    }

    void addDrawer(IDrawer drawer,ShapeType shapeType) {
        mDraws.put(shapeType,drawer);
    }

    public void notifyDataSetChanged() {
        mDataSetObservable.notifyChanged();
    }

    @Override
    public void notifyInvalidate() {
        mDataSetObservable.notifyInvalidated();
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }
}
