package com.utsoft.jan.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2019/7/23.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.utils
 */
public class DateTimeUtil {
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yy-MM-dd",Locale.ENGLISH);

    public static String getSampleDate(Date date){
        return FORMAT.format(date);
    }
}
