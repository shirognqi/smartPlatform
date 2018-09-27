package GroovySourceScript.helloWorld

import com.tencent.smartplatform.Util.Bean.MySQLConfig
import com.tencent.smartplatform.Util.Bean.RedisConfig

import static com.tencent.smartplatform.Util.JedisPoolUtils.getRedisConnectionPool
import static com.tencent.smartplatform.Util.MySQLConnectionPoolUtils.getMySQLConnectionPool


def mySQLConnectionPool = getMySQLConnectionPool(new MySQLConfig("127.0.0.1","3306","qa","root", "kuanglong!989712"))
def redisConnectionPool = getRedisConnectionPool(new RedisConfig("127.0.0.1","6379"))
env.setMySQLPool("testMySQLPool",mySQLConnectionPool)
env.setRedisPool("testRedisPool",redisConnectionPool)