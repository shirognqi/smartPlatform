package dailyGroovyScript.source.helloWorld

@Singleton
class ThreadTest {

    def doThread(){
        printThreadInfo()
        Thread.start{
            for(int i=0; i<50; i++){
                printThreadInfo()
            }
            return
        }
    }

    def printThreadInfo(){
        Thread currentThread = Thread.currentThread()
        println "Current Thread is ${currentThread}"
    }
}