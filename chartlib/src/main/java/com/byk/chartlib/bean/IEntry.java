package com.byk.chartlib.bean;

import java.util.Date;

public interface IEntry {
    float getcalculateMax();

    float getcalculateMin();

    String getDate();

    void setDate(String date);

    float getMaster();
}
