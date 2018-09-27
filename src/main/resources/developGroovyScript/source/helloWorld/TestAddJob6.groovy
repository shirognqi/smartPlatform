package developGroovyScript.source.helloWorld

import com.tencent.smartplatform.Service.EnvService
import groovy.transform.CompileStatic

@CompileStatic
@Singleton
class TestAddJob6 {
    def doOtherJob(EnvService env){
        return "尝试添加其他任务6"
    }
}
