package com.byk.chart.draw;

import com.byk.chart.adapter.IAdapter;

public class DrawFactory {

    public IDrawer createDraw(IAdapter adapter, ShapeType shapeType){
        return adapter.getDrawer(shapeType);
    }
}
