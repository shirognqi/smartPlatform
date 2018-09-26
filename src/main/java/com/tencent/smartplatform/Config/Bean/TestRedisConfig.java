package com.tencent.smartplatform.Config.Bean;


import com.tencent.smartplatform.Util.Bean.RedisConfig;

public class TestRedisConfig {
        // 常规Redis连接库实例
        public static RedisConfig REDIS_TEST_CONNECTION        = new RedisConfig("127.0.0.1","6379");
        // 非常规Redis连接库实例
        public static RedisConfig REDIS_TEST_ZKNAME_CONNECTION = new RedisConfig("testzkname");
}
