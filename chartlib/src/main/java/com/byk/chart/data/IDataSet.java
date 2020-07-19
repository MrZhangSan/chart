package com.byk.chart.data;

import com.byk.chart.draw.ShapeType;
import com.byk.chart.bean.IEntry;

public interface IDataSet<T extends IEntry> {
    ShapeType getShapeType();

    /**
     * 获取一段数据最大值
     * @param start
     * @param end
     * @return
     */
    float getCalculateMax(int start,int end);

    /**
     * 获取一段数据最小值
     * @return
     */
    float getCalculateMin(int start,int end);
}
