package developGroovyScript.source.helloWorld

import com.tencent.smartplatform.Service.EnvService
import groovy.sql.Sql

import java.sql.Connection

/**
 * 获取连接池的封装方法
 */
@Singleton
class ConnectionUtils {
    def static getMySQLConnPool(EnvService env, connectionName){
        Connection mySQLConn = env.getMySQLPool(connectionName)
        def sql = new Sql(mySQLConn)
        return sql
    }
    def static getRediseConnPool(env, connectionName){
        def redisConn = env.getRedisPool(connectionName)
        return redisConn
    }
}
