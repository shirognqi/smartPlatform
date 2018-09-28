package developGroovyScript.source.helloWorld
import com.tencent.smartplatform.Service.EnvService


// env construction
EnvService env = (EnvService)env

def ret = DailyJob.instance.doSomeWork(env)
def otherJob = TestAddJob6.instance.doOtherJob(env)
ret["otherJob"] = otherJob
ret["aaaaaaaa"] = "hello, smart platform"
ret["env"] = env.getEnvStr()

return ret