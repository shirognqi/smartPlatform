package com.tencent.smartplatform.Service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class EnvService {

    private  Cache<String, ComboPooledDataSource> mySQLPool = CacheBuilder.newBuilder().maximumSize(1000).recordStats().build();
    private  Cache<String, JedisPool> redisPool = CacheBuilder.newBuilder().maximumSize(1000).recordStats().build();

    public ComboPooledDataSource getMySQLPool(String key) throws ExecutionException {

        try {
            ComboPooledDataSource var = (ComboPooledDataSource) mySQLPool.get(key, new Callable<ComboPooledDataSource>() {
                @Override
                public ComboPooledDataSource call() throws Exception {
                    return null;
                }
            });
            return var;
        }catch (Exception e){
            return null;
        }
    }
    public  void setMySQLPool(String key, ComboPooledDataSource value) {
        mySQLPool.put(key, value);
    }
    public void cleanMySQLPool(){
        mySQLPool.cleanUp();
    }


    public JedisPool getRedisPool(String key) throws ExecutionException {

        try {
            JedisPool var = (JedisPool) redisPool.get(key, new Callable<JedisPool>() {
                @Override
                public JedisPool call() throws Exception {
                    return null;
                }
            });
            return var;
        }catch (Exception e){
            return null;
        }
    }
    public  void setRedisPool(String key, JedisPool value) {
        redisPool.put(key, value);
    }
    public void cleanRedisPool(){
        redisPool.cleanUp();
    }

    public void cleanAll(){
        cleanRedisPool();
        cleanMySQLPool();
    }




}
