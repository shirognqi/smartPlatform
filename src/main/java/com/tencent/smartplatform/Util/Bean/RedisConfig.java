package com.tencent.smartplatform.Util.Bean;

import static com.tencent.smartplatform.Config.RedisConnectionType.NORMAL_REDIS_CONNECTION;
import static com.tencent.smartplatform.Config.RedisConnectionType.ZKNAME_REDIS_CONNECTION;

public class RedisConfig {
    private String ip;
    private String port;
    private String zkname;
    private String redisConnectionType;

    public RedisConfig(String ip, String port){
        this.ip = ip;
        this.port = port;
        this.redisConnectionType = NORMAL_REDIS_CONNECTION;
    }
    public RedisConfig(String zkname){
        this.zkname = zkname;
        this.redisConnectionType = ZKNAME_REDIS_CONNECTION;
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

    public String getZkname() {
        return zkname;
    }

    public void setZkname(String zkname) {
        this.zkname = zkname;
    }

    public String getRedisConnectionType() {
        return redisConnectionType;
    }

    public void setRedisConnectionType(String redisConnectionType) {
        this.redisConnectionType = redisConnectionType;
    }
}
