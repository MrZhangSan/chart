package com.byk.chart.format;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间格式化器
 * Created by tifezh on 2016/6/21.
 */

public class DateFormatterUtils {
    public static final String FORMAT_YMD = "yyyy-MM-dd";
    public static final String FORMAT_YMD_CN = "yyyy年MM月dd";
    public static final String FORMAT_YMD_NUMBER = "yyyyMMdd";
    public static final String FORMAT_YMD_DIVIDE = "yyyy/MM/dd";
    public static final String FORMAT_HM = "HH:mm";
    public static final String FORMAT_MD = "MM-dd";

    public static String format(Date date, String format) {
        if (date == null) {
            return null;
        }
        if (format == null || format.length() == 0) {
            format = FORMAT_YMD;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String s = sdf.format(date);
        return s;
    }
}
