package com.byk.chartlib.draw;

import com.byk.chartlib.adapter.IAdapter;

public class DrawFactory {

    public IDrawer createDraw(IAdapter adapter, ShapeType shapeType){
        return adapter.getDrawer(shapeType);
    }
}
