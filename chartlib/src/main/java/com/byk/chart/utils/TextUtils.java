package com.byk.chart.utils;

import android.graphics.Paint;
import android.graphics.Rect;

public class TextUtils {
    private static Rect mCalcTextHeightRect = new Rect();

    public static int calcTextHeight(Paint paint, String demoText) {

        Rect r = mCalcTextHeightRect;
        r.set(0, 0, 0, 0);
        paint.getTextBounds(demoText, 0, demoText.length(), r);
        return r.height();
    }

    public static int calcTextWidth(Paint paint, String demoText){
         return (int) paint.measureText(demoText);
    };
}
