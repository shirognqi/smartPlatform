package com.tencent.smartplatform.Service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.tencent.smartplatform.Util.Bean.MySQLConfig;
import com.tencent.smartplatform.Util.Bean.RedisConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import static com.tencent.smartplatform.Util.JedisPoolUtils.getRedisConnectionPool;
import static com.tencent.smartplatform.Util.MySQLConnectionPoolUtils.getMySQLConnectionPool;

@Service
public class EnvService {

    private String envStr;

    private  Cache<String, ComboPooledDataSource> mySQLPool = CacheBuilder.newBuilder().maximumSize(1000).recordStats().build();
    private  Cache<String, JedisPool> redisPool = CacheBuilder.newBuilder().maximumSize(1000).recordStats().build();

    public Connection getMySQLPool(String key) throws ExecutionException {

        try {
            ComboPooledDataSource var = (ComboPooledDataSource) mySQLPool.get(key, new Callable<ComboPooledDataSource>() {
                @Override
                public ComboPooledDataSource call() throws Exception {
                    return null;
                }
            });
            return var.getConnection();
        }catch (Exception e){
            return null;
        }
    }
    public  void setMySQLPool(String key, MySQLConfig mySQLConfig) throws NoSuchAlgorithmException {

        ComboPooledDataSource comboPooledDataSource = getMySQLConnectionPool(mySQLConfig);


        mySQLPool.put(key, comboPooledDataSource);
    }
    public void cleanMySQLPool(){
        mySQLPool.cleanUp();
    }


    public Jedis getRedisPool(String key) throws ExecutionException {

        try {
            JedisPool var = (JedisPool) redisPool.get(key, new Callable<JedisPool>() {
                @Override
                public JedisPool call() throws Exception {
                    return null;
                }
            });
            return var.getResource();
        }catch (Exception e){
            return null;
        }
    }
    public  void setRedisPool(String key, RedisConfig redisConfig) {
        JedisPool redisConnectionPool = getRedisConnectionPool(redisConfig);
        redisPool.put(key, redisConnectionPool);
    }
    public void cleanRedisPool(){
        redisPool.cleanUp();
    }

    public void cleanAll(){
        cleanRedisPool();
        cleanMySQLPool();
    }

    public void setEnvStr(String envStr){
        this.envStr = envStr;
    }
    public String getEnvStr(){
        return this.envStr;
    }


}
