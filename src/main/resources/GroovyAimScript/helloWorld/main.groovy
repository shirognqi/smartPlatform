package GroovyAimScript.helloWorld

import com.tencent.smartplatform.Service.EnvService


// env construction
EnvService env = (EnvService)env

def ret = DailyJob.instance.doSomeWork(env)
def otherJob = TestAddJob6.instance.doOtherJob(env)
ret["otherJob"] = otherJob
ret["aaaaaaaa"] = "hello, smart platform"
ret["envStr"] = env.getEnvStr()



//def printThreadInfo(){
//    Thread currentThread = Thread.currentThread()
//    println "Current Thread is ${currentThread}"
//}
//printThreadInfo()
//Thread.start{
//    for(int i=0; i<50; i++){
//        printThreadInfo()
//    }
//    return
//}

return ret

import com.mchange.v2.c3p0.ComboPooledDataSource
import com.tencent.smartplatform.Service.EnvService
import groovy.sql.Sql

import java.sql.Connection

@Singleton
class ConnectionUtils {
    def getMySQLConn(EnvService env, connectionName){
        ComboPooledDataSource mySQLConnectionPool = env.getMySQLPool(connectionName)
        Connection mySQLConn = mySQLConnectionPool.getConnection()
        def sql = new Sql(mySQLConn)
        return sql
    }
    def getRediseConn(env, connectionName){
        def redisConnectionPool = env.getRedisPool(connectionName)
        def redisConn = redisConnectionPool.getResource()
        return redisConn
    }
}


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
        def cacheDate = redisConn.get("aaaaaa")
        redisConn.close()

        def ret=[:]
        ret["dbData"] = databaseRet
        ret["cacheData"] = "来自于redis的连接池 ，记得close()哈，这个地方还是有坑的：" + cacheDate
        return ret
    }
}


import com.tencent.smartplatform.Service.EnvService

@Singleton
class TestAddJob {
    def doOtherJob(EnvService env){
        return "尝试添加其他任务"
    }
}


import com.tencent.smartplatform.Service.EnvService
import groovy.transform.CompileStatic

@CompileStatic
@Singleton
class TestAddJob6 {
    def doOtherJob(EnvService env){
        return "尝试添加其他任务6"
    }
}


@Singleton
class ThreadTest {

}
