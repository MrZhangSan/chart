package com.byk.chartlib.format;

import java.text.DecimalFormat;
import java.util.Locale;

/**
 * 对较大数据进行格式化
 */

public class BigValueFormatter {

    //必须是排好序的
    private static int[] values={10000,100000000};
    private static String[] units={"万","亿"};

    public static String format(float value) {
        String unit="";
        int i=values.length-1;
        while (i>=0)
        {
            if(value>values[i]) {
                value /= values[i];
                unit = units[i];
                break;
            }
            i--;
        }
        return String.format(Locale.getDefault(),"%.2f", value)+unit;
    }

    /**
     * 格式化大数字
     * @param value
     * @return
     */
    public static String format(double value) {
        if (value < -100000000) {
            return "-" + new DecimalFormat("0.00").format(Math.abs(value) * 1.0 / 100000000) + "亿";
        }else if (value < -10000) {
            return "-" + new DecimalFormat("0.00").format(Math.abs(value) * 1.0 / 10000) + "万";
        }else if (value < 10000) {
            return new DecimalFormat("0.00").format(value);
        }else if (value <  100000000) {
            return new DecimalFormat("0.00").format(value * 1.0 / 10000) + "万";
        }else {
            return new DecimalFormat("0.00").format(value * 1.0 / 100000000) + "亿";
        }
    }

    /**
     * 格式化大数字,如果在10000和-10000之间不格式化,直接转成字符串
     * @param value
     * @return
     */
    public static String format2(double value) {
        if (value < -100000000) {
            return "-" + new DecimalFormat("0.00").format(Math.abs(value) * 1.0 / 100000000) + "亿";
        }else if (value < -10000) {
            return "-" + new DecimalFormat("0.00").format(Math.abs(value) * 1.0 / 10000) + "万";
        }else if (value < 10000) {
            return String.valueOf((int)value);
        }else if (value <  100000000) {
            return new DecimalFormat("0.00").format(value * 1.0 / 10000) + "万";
        }else {
            return new DecimalFormat("0.00").format(value * 1.0 / 100000000) + "亿";
        }
    }

    /**
     * 格式化数字为字符串,保留两位小数
     * @param value
     * @return
     */
    public static String format3(double value) {
        return new DecimalFormat("0.00").format(value);
    }

}
