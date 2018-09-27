package GroovySourceScript.helloWorld
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
