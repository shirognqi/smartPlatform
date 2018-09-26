package com.tencent.smartplatform.Config;

import java.util.HashMap;

public class MySQLConnectionType {
    public final static HashMap<String, String> MySQL_ATTR = new HashMap<String, String>(){{
        put("useUnicode", "true");
        put("characterEncoding", "UTF-8");
        put("useSSL","false");
        put("serverTimezone","UTC");
    }};
    public final static String NORMAL_MYSQL_CONNECTION = "normal";
    public final static String ZKNAME_MYSQL_CONNECTION = "zkname";
}
