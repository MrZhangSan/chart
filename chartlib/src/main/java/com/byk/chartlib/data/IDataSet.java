package com.byk.chartlib.data;

import com.byk.chartlib.draw.ShapeType;
import com.byk.chartlib.bean.IEntry;

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
