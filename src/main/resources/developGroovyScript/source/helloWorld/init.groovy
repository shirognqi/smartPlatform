package developGroovyScript.source.helloWorld

import com.tencent.smartplatform.Util.Bean.MySQLConfig
import com.tencent.smartplatform.Util.Bean.RedisConfig

/**
 * 初始化连接池资源
 */
MySQLConfig mySQLConfig = new MySQLConfig("127.0.0.1","3306","qa","root", "kuanglong!989712")
RedisConfig redisConfig = new RedisConfig("127.0.0.1","6379")
env.setMySQLPool("testMySQLPool",mySQLConfig)
env.setRedisPool("testRedisPool",redisConfig)