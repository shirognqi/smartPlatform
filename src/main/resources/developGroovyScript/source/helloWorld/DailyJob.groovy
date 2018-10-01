package developGroovyScript.source.helloWorld

import redis.clients.jedis.Jedis

import java.text.SimpleDateFormat

@Singleton
class DailyJob {

    def doSomeWork(env){


        return "doSomeWork!"
    }
}
