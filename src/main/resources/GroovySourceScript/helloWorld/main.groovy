package GroovySourceScript.helloWorld
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