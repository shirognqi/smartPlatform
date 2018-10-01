package developGroovyScript.source.helloWorld

import redis.clients.jedis.Jedis

import java.text.SimpleDateFormat

@Singleton
class Job {

    def doSomeWork(env){
        return "doSomeWork!"
    }
}
