package com.tencent.smartplatform.Util.Bean;

import java.util.HashMap;

import static com.tencent.smartplatform.Config.MySQLConnectionType.MySQL_ATTR;
import static com.tencent.smartplatform.Config.MySQLConnectionType.NORMAL_MYSQL_CONNECTION;
import static com.tencent.smartplatform.Config.MySQLConnectionType.ZKNAME_MYSQL_CONNECTION;

public class MySQLConfig {
    private String                  ip;
    private String                  port;
    private String                  dbName;
    private String                  user;
    private String                  password;
    private HashMap<String, String> attr;
    private String                  mySQLConnectionType;
    private String                  zkname;


    /**
     * 构造函数
     * @param ip
     * @param port
     * @param dbName
     * @param user
     * @param password
     */
    public MySQLConfig(String ip, String port, String dbName, String user, String password){
        MySQLConfig(ip, port, dbName, user, password, MySQL_ATTR);
    }
    public MySQLConfig(String zkName, String dbName, String user, String password){
        MySQLConfig(zkName, dbName, user, password, MySQL_ATTR);
    }

    public MySQLConfig MySQLConfig(String ip, String port, String dbName, String user, String password, HashMap<String,String>attr){
        this.ip = ip;
        this.port = port;
        this.password = password;
        this.user = user;
        this.dbName = dbName;
        this.attr = attr;
        this.mySQLConnectionType = NORMAL_MYSQL_CONNECTION;
        return this;
    }



    public MySQLConfig MySQLConfig(String zkName, String dbName, String user, String password, HashMap<String,String>attr){
        this.zkname = zkName;
        this.password = password;
        this.user = user;
        this.dbName = dbName;
        this.attr = MySQL_ATTR;
        this.mySQLConnectionType = ZKNAME_MYSQL_CONNECTION;
        return this;
    }

    public String getZkname() {
        return zkname;
    }

    public void setZkname(String zkname) {
        this.zkname = zkname;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashMap<String, String> getAttr() {
        return attr;
    }

    public void setAttr(HashMap<String, String> attr) {
        this.attr = attr;
    }

    public String getMySQLConnectionType() {
        return mySQLConnectionType;
    }

    public void setMySQLConnectionType(String mySQLConnectionType) {
        this.mySQLConnectionType = mySQLConnectionType;
    }

}
