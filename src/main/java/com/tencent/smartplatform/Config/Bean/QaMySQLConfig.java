package com.tencent.smartplatform.Config.Bean;


import com.tencent.smartplatform.Util.Bean.MySQLConfig;

public class QaMySQLConfig {
    // 常规MySQL连接库实例
    public static MySQLConfig TEST_CONNECTION        = new MySQLConfig("127.0.0.1","3306","qa","root", "kuanglong!989712");
    public static MySQLConfig TEST_ZKNAME_CONNECTION = new MySQLConfig("zkname","qa","root","kuanglong!989712");
}
