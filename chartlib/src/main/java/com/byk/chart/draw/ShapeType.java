package com.byk.chart.draw;

import com.byk.chart.data.DataProvider;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public enum ShapeType {
    LINE(LineDrawer.class),
    CANDLE(CandleDrawer.class);
    private Class<? extends IDrawer> drawClass;
    ShapeType(Class<? extends IDrawer> clzss){
        drawClass = clzss;
    }

    /**
     * 创建绘制者
     * @return
     */
    public IDrawer create(DataProvider dataProvider){
        try {
            Constructor<? extends IDrawer> constructor = drawClass.getConstructor(DataProvider.class);

            IDrawer iDrawer = constructor.newInstance(dataProvider);
            return iDrawer;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}
