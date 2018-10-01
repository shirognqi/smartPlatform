package developGroovyScript.source.helloWorld

import com.tencent.smartplatform.Controller.Bean.RequestObj
import com.tencent.smartplatform.Service.EnvService

// env construction
EnvService env = (EnvService)env
RequestObj requestObj = (RequestObj)requestObj;

// map def
def ret = [:]
ret["theFirstTest"] = "hello, smart platform!"
Job.instance.doSomeWork(env,ret,requestObj)

return ret