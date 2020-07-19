package com.byk.chartlib.data;

import com.byk.chartlib.bean.IEntry;

import java.util.List;

/**
 * 数据列表实体类
 *
 * @param <T>
 */
public abstract class DataSet<T extends IEntry> implements IDataSet<T> {

    private List<T> mEntry;

    /**
     * 是否佣金计算临界值
     */
    private boolean mIsCalcuLimit = true;
    /**
     * 是否是主图
     */
    private boolean mIsMaster;

    public DataSet(List<T> mEntry) {
        this.mEntry = mEntry;
    }


    public List<T> getEntry() {
        return mEntry;
    }

    public boolean isMaster() {
        return mIsMaster;
    }

    public void setMaster(boolean mIsMaster) {
        this.mIsMaster = mIsMaster;
    }

    public boolean isCalcuLimit() {
        return mIsCalcuLimit;
    }

    public void setCalcuLimit(boolean mIsCalcuLimit) {
        this.mIsCalcuLimit = mIsCalcuLimit;
    }

    @Override
    public float getCalculateMax(int start, int end) {
        if (mEntry == null || mEntry.isEmpty()) {
            return Float.NaN;
        }

        int size = mEntry.size();

        if (start < 0) {
            start = 0;
        }

        if (end >= size) {
            end = size - 1;
        }

        float temp = Float.MIN_VALUE;
        for (int i = start; i <= end; i++) {
            IEntry entry = mEntry.get(i);
            float max = entry.getcalculateMax();

            if (temp < max) {
                temp = max;
            }
        }

        return temp;
    }

    @Override
    public float getCalculateMin(int start, int end) {
        if (mEntry == null || mEntry.isEmpty()) {
            return Float.NaN;
        }

        int size = mEntry.size();

        if (start < 0) {
            start = 0;
        }

        if (end >= size) {
            end = size - 1;
        }

        float temp = Float.MAX_VALUE;
        for (int i = start; i <= end; i++) {
            IEntry entry = mEntry.get(i);
            float min = entry.getcalculateMin();
            if (temp > min) {
                temp = min;
            }
        }

        return temp;
    }
}
