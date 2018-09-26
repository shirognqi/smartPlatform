package com.tencent.smartplatform.GroovySourceScript.helloWorld

import com.tencent.smartplatform.Service.EnvService

@Singleton
class TestAddJob {
    def doOtherJob(EnvService env){
        return "尝试添加其他任务"
    }
}
