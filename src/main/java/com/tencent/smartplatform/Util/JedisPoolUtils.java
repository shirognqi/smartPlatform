package com.tencent.smartplatform.Util;

import com.tencent.smartplatform.Util.Bean.RedisConfig;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import static com.tencent.smartplatform.Config.RedisConnectionType.NORMAL_REDIS_CONNECTION;


public class JedisPoolUtils {

    public static JedisPool getRedisConnectionPool(RedisConfig redisConfig) {

        String connectType = dispatch(redisConfig);
        if(NORMAL_REDIS_CONNECTION.equals(connectType)){
            return getNormalConnectionPool(redisConfig);
        }
        return null;
    }

    public static String dispatch(RedisConfig redisConfig){
        return redisConfig.getRedisConnectionType();
    }

    public static JedisPool getNormalConnectionPool(RedisConfig redisConfig){
        String ip = redisConfig.getIp();
        String port = redisConfig.getPort();
        JedisPool pool = null;
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(50);
        config.setMaxIdle(5);
        config.setMaxWaitMillis(1000*10);
        config.setMinEvictableIdleTimeMillis(1000);
        config.setTimeBetweenEvictionRunsMillis(100);
        //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
        config.setTestOnBorrow(true);
        //new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
        pool = new JedisPool(config, ip, Integer.valueOf(port));
        return pool;

    }


}
