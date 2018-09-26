package com.tencent.smartplatform.Util;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.tencent.smartplatform.Util.Bean.MySQLConfig;

import java.beans.PropertyVetoException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import static com.tencent.smartplatform.Config.MySQLConnectionType.NORMAL_MYSQL_CONNECTION;

public class MySQLConnectionPoolUtils {

    public static ComboPooledDataSource normalConnection(MySQLConfig mySQLConnection) {
        String ip = mySQLConnection.getIp();
        String port = mySQLConnection.getPort();
        String dbName = mySQLConnection.getDbName();
        String user = mySQLConnection.getUser();
        String password = mySQLConnection.getPassword();
        HashMap<String, String> attr = mySQLConnection.getAttr();
        final ArrayList<String> attrs = new ArrayList<>();
        attr.forEach((k,v)-> attrs.add(k +"=" + v));
        String attrStr = String.join("&",attrs);
        String mySQLURL = String.format("jdbc:mysql://%s:%s/%s?%s",ip,port,dbName,attrStr);
        Connection conn = null;
        try {
            ComboPooledDataSource ds = new ComboPooledDataSource();
            ds.setDriverClass("com.mysql.cj.jdbc.Driver");
            ds.setJdbcUrl(mySQLURL);
            ds.setUser(user);
            ds.setPassword(password);
            ds.setAcquireIncrement(5);          // 连接池中的连接耗尽的时候c3p0一次同时获取的连接数
            ds.setInitialPoolSize(10);          // 初始化数据库连接池时连接的数量
            ds.setMinPoolSize(10);              // 数据库连接池中的最小的数据库连接数
            ds.setMaxPoolSize(50);              // 数据库连接池中的最大的数据库连接数
            ds.setMaxIdleTime(10);              // 
            ds.setAutoCommitOnClose(true);          // 自动提交
            ds.setBreakAfterAcquireFailure(true);   // 失败自动断开
            ds.setMaxConnectionAge(600);            // 自动提交时间
            ds.setIdleConnectionTestPeriod(3);      //
            return ds;
        } catch (PropertyVetoException e) {
            e.printStackTrace();
            return null;
        }
    }



    /**
     *
     * @param mySQLConfig
     * @return
     */
    public static ComboPooledDataSource getMySQLConnectionPool(MySQLConfig mySQLConfig) throws NoSuchAlgorithmException {
        String connectType = dispatch(mySQLConfig);

        if(NORMAL_MYSQL_CONNECTION.equals(connectType)){
            return  normalConnection(mySQLConfig);
        }else{
            return null;
        }
    }


    public static String dispatch(MySQLConfig mySQLConnection){
        return mySQLConnection.getMySQLConnectionType();
    }

//    public static ConnectionPool normalConnection(MySQLConfig mySQLConnection){
//        String ip = mySQLConnection.getIp();
//        String port = mySQLConnection.getPort();
//        String dbName = mySQLConnection.getDbName();
//        String user = mySQLConnection.getUser();
//        String password = mySQLConnection.getPassword();
//        HashMap<String, String> attr = mySQLConnection.getAttr();
//        final ArrayList<String> attrs = new ArrayList<>();
//        attr.forEach((k,v)-> attrs.add(k +"=" + v));
//        String attrStr = String.join("&",attrs);
//        String mySQLURL = String.format("jdbc:mysql://%s:%s/%s?%s",ip,port,dbName,attrStr);
//        ConnectionPool poolInstance = new ConnectionPool(
//                "com.mysql.cj.jdbc.Driver",
//                mySQLURL,
//                user, password);
//        try {
//            poolInstance.createPool();
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return poolInstance;
//    }




    public static String getConnectKey(MySQLConfig mySQLConnection) throws NoSuchAlgorithmException {
        String ip = mySQLConnection.getIp();
        String port = mySQLConnection.getPort();
        String dbName = mySQLConnection.getDbName();
        String user = mySQLConnection.getUser();
        String password = mySQLConnection.getPassword();
        HashMap<String, String> attr = mySQLConnection.getAttr();
        String mySQLConnectionType = mySQLConnection.getMySQLConnectionType();

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update((user + "_" + password).getBytes());
        byte[] digest = md.digest();
        String ret = mySQLConnectionType + "_" + ip + "_" + port + "_" +dbName +"_"+convertByteToHex(digest);
        return ret;
    }


    private static String convertByteToHex(byte[] byteData) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}
