package com.byk.chart.marker;

import android.graphics.Canvas;

import com.byk.chart.marker.data.IMarkBean;

public interface IMarker< T extends IMarkBean> {
    void drawView(Canvas canvas, float tranX,float tranY);

    void refreshContent(T bean);

    void setLayoutResource(int layout);

}
