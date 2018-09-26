package com.tencent.smartplatform.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    public static String getCurrentTime(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date.getTime());
        return time;
    }
}
