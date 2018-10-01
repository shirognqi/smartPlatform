package developGroovyScript.source.helloWorld

import com.tencent.smartplatform.Service.EnvService
import groovy.json.JsonSlurper
import groovy.transform.CompileStatic

@CompileStatic
@Singleton
class TestAddJob6 {
    def doOtherJob(String jsonStr){

        def slurper = new JsonSlurper()
        def jsonObj = slurper.parseText(jsonStr)
        return jsonObj
    }
}
