package GroovySourceScript.helloWorld

import redis.clients.jedis.Jedis

import java.text.SimpleDateFormat

@Singleton
class DailyJob {

    def doSomeWork(env){

        // 获取连接池
        def sql         = ConnectionUtils.instance.getMySQLConn(env,"testMySQLPool")
        Jedis redisConn   = ConnectionUtils.instance.getRediseConn(env, "testRedisPool")

        def databaseRet = [:]
        def i=0
        def row = sql.eachRow("SELECT * FROM t_test"){
            databaseRet[i] = "来自于数据库的连接池aaa" + it["str"]
            i++
        }
        sql.close()

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        String systime = df.format(new Date())
        redisConn.set("aaa",systime)
        def cacheDate = redisConn.get("aaa")
        redisConn.close()

        def ret=[:]
        ret["dbData"] = databaseRet
        ret["cacheData"] = "来自于redis的连接池 ，记得close()哈，这个地方还是有坑的：" + cacheDate
        return ret
    }
}
