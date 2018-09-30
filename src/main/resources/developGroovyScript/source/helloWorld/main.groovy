package developGroovyScript.source.helloWorld

import com.tencent.smartplatform.Controller.Bean.RequestObj
import com.tencent.smartplatform.Service.EnvService


// env construction
EnvService env = (EnvService)env
RequestObj requestObj = (RequestObj)requestObj;
def ret = DailyJob.instance.doSomeWork(env)
def otherJob = TestAddJob6.instance.doOtherJob(env)
ret["otherJob"] = otherJob
ret["aaaaaaaa"] = "hello, smart platform"
ret["env"] = env.getEnvStr()
ret["requestObj"] = requestObj.getParams()

return ret