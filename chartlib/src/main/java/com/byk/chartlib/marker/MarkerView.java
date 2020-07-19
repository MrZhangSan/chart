package com.byk.chartlib.marker;

import android.content.Context;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import com.byk.chartlib.marker.data.IMarkBean;

public abstract class MarkerView<T extends IMarkBean> extends RelativeLayout implements IMarker<T>{

    public MarkerView(Context context,int layout) {
        super(context);
        setLayoutResource(layout);
    }


    @Override
    public void drawView(Canvas canvas, float tranX,float tranY) {
        int saveId = canvas.save();
        canvas.translate(tranX, tranY);
        draw(canvas);
        canvas.restoreToCount(saveId);
    }

    @Override
    public void setLayoutResource(int layout) {
        LayoutInflater.from(getContext()).inflate(layout, this);
    }
}
