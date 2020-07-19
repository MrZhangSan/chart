package com.byk.chart.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.byk.chart.adapter.IAdapter;
import com.byk.chart.data.DataProvider;

import com.byk.chart.utils.Tranformer;
import com.byk.chart.utils.ViewHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public abstract class ChartView extends ViewGroup implements IChartView{

    protected IAdapter mAdapter;

    private ViewHandler mViewHandler = new ViewHandler();

    private Tranformer mTranformer;

    private boolean mIsMaster;

    /**
     * 同步的视图
     */
    private List<IChartView> mSyncChartView = new ArrayList<>();

    private DataSetObserver mDataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            notifyChanged();
        }

        @Override
        public void onInvalidated() {
            onInvalidate();
        }
    };

    public ChartView(Context context) {
        super(context);
        setWillNotDraw(false);
        init();
    }

    public ChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        init();
    }

    public ChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        init();
    }

    private void init(){
        mTranformer = new Tranformer(mViewHandler);
    }

    @Override
    public IAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mViewHandler.setChartDimens(w, h);
        mViewHandler.setOffsets(getPaddingLeft(),getPaddingTop(),getPaddingRight(),getPaddingBottom());
        //修改项的宽度
        fix();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).layout(left, top, right, bottom);
        }
    }

    private void fix(){
        //修改项的宽度
        calculate();

        //同步关联视图数据
        syncChartView();
    }


    private void syncChartView(){
        DataProvider dataProvider = mAdapter.getDataProvider();

        int size = mSyncChartView.size();

        for (int i = 0; i < size; i++) {
            IChartView chartView = mSyncChartView.get(i);
            chartView.syncData(dataProvider);
        }
    }

    @Override
    public void syncData(DataProvider dataProvider) {
        DataProvider innerDataProvider = mAdapter.getDataProvider();
        innerDataProvider.snycData(dataProvider);
        calculate();
        postInvalidate();
    }

    private void calculate(){
        DataProvider dataProvider = mAdapter.getDataProvider();
        dataProvider.calculate();
        mTranformer.prepareMatrixValuePx((dataProvider.getEndPosition() - dataProvider.getStartPosition()) + 1,
                (dataProvider.getMaxValue() - dataProvider.getMinValue()),
                dataProvider.getStartPosition(),dataProvider.getMinValue());
        mTranformer.prepareMatrixPxValue((dataProvider.getEndPosition() - dataProvider.getStartPosition()) + 1,
                (dataProvider.getMaxValue() - dataProvider.getMinValue()),
                dataProvider.getStartPosition(),dataProvider.getMinValue());
    }

    /**
     * 重新计算并刷新线条
     */
    public void notifyChanged() {
        // 修正起始位置
        fix();
        invalidate();
    }

    /**
     * 重新计算并刷新线条
     */
    public void onInvalidate() {
        fix();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mAdapter.onDraw(canvas);
        //TODO 绘制逻辑
    }

    public void setAdapter(IAdapter adapter) {
        this.mAdapter = adapter;
        if (mAdapter != null) {
            mAdapter.getDataProvider().setTranformer(mTranformer);
            mAdapter.getDataProvider().setMaster(mIsMaster);
            mAdapter.registerDataSetObserver(mDataSetObserver);
        }
    }


    /**
     * 添加需要同步的视图
     * @param chartView
     */
    public void addSyncChartView(IChartView...chartView){
        mSyncChartView.addAll(Arrays.asList(chartView));
    }

    public void isMaster(boolean isMaster){
       this.mIsMaster = isMaster;
       if (mAdapter != null){
           mAdapter.getDataProvider().setMaster(mIsMaster);
       }
    };
}
