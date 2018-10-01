package developGroovyScript.source.helloWorld

import groovy.json.JsonSlurper
import redis.clients.jedis.Jedis

import java.text.SimpleDateFormat

/**
 * 对于自定义类的支持
 */
@Singleton
class Job {

    def doSomeWork(env,ret, requestObj){
        // 获取参数测试
        ret["requestTest"] = requestObj.getParams()

        // 全局变量测试
        ret["env"] = env.getEnvStr()
        // MySQL连接池测试，获取连接池的配置，和init.groovy里的设置一样
        def sql         = ConnectionUtils.getMySQLConnPool(env,"testMySQLPool")
        def databaseRet = [:]
        def i=0
        sql.eachRow("SELECT * FROM t_test"){
            databaseRet[i] = "来自Mysql连接池C3P0数据源的数据：" + it["str"]
            i++
        }

        ret["dbDataTest"] = databaseRet
        // 归还连接池连接，连接池最好手动回收，因为连接池自动回收需要耗费线程去回收，
        // 这样做性能不是最好的，就给关闭了，一般手动回收；
        sql.close()

        // redis测试
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        String systime = df.format(new Date())
        Jedis redisConn   = ConnectionUtils.getRediseConnPool(env, "testRedisPool")
        redisConn.set("aaa",systime)
        ret["setCacheDataTest"] = "设定Redis的连接池Jedis数据：" + systime
        def cacheDate = redisConn.get("aaa")
        ret["getCacheDataTest"] = "来自于Redis的连接池Jedis的数据：" + cacheDate
        // 归还连接池连接，连接池最好手动回收，因为连接池自动回收需要耗费线程去回收，
        // 这样做性能不是最好的，就给关闭了，一般手动回收；
        redisConn.close()

        // JSON解析测试
        def jsonStr = "{\"state\":{\"name\":\"smart platform\",\"developer\":\"kuanglong\"}}"
        ret["jsonStr"] = jsonStr
        def slurper = new JsonSlurper()
        def jsonStrParse2Obj = slurper.parseText(jsonStr)
        ret["jsonStrParse2ObjTest"] = jsonStrParse2Obj

        // http请求测试
        def header = [:]
        header["User-Agent"] = "Mozilla/5.0 反正是随便写的，就给个demo"
        def httpResponse = HttpUtils.doGet("http://127.0.0.1:8080/index", header,300,300,300)
        ret["httpRequestText"] = httpResponse
    }
}
