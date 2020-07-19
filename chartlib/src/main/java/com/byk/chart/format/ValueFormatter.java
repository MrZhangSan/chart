package com.byk.chart.format;

import android.text.TextUtils;

import java.text.DecimalFormat;

public class ValueFormatter {
    private static String DEFAULT = "0.00";
    private DecimalFormat mFormat;
    private String mPattern = DEFAULT;

    public ValueFormatter() {
        mFormat = new DecimalFormat();
    }

    public void setPattern(String mPattern) {
        this.mPattern = mPattern;
    }

    public String format(double value){
        if (TextUtils.isEmpty(mPattern)){
            mPattern = DEFAULT;
        }
        mFormat.applyPattern(mPattern);
       return mFormat.format(value);
    }
}
