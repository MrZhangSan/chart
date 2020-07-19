package com.byk.chartlib.data;

import com.byk.chartlib.bean.IEntry;

public interface IDataProvider<T extends IEntry> {
    //获取数据
    DataSet<T>[] getDataSet();

    //设置数据
    void setDataSet(DataSet<T>... dataSet);

    /**
     * 获取当前显示数量
     * @return
     */
    int getCurrentDisplayCount();

    /**
     * 获取开始位置
     * @return
     */
    int getStartPosition();

    /**
     * 设置开始位置
     * @param startPosition 开始位置
     */
    void setStartPosition(int startPosition);

    /**
     * 获取结束位置
     * @return
     */
    int getEndPosition();


    /**
     * 设置结束位置
     * @param endPosition 结束位置
     */
    void setEndPosition(int endPosition);
}
