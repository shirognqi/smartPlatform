package developGroovyScript.source.helloWorld

@Singleton
class ThreadTest {

    def doThread(env){

        Thread.start{
            def sql         = ConnectionUtils.getMySQLConnPool(env,"testMySQLPool")
            for(int i=0; i<50; i++){
                printThreadInfo(sql,i)
            }
            return
        }
        return 123 // 执行doThread方法123会立刻返回，线程在这里thread.start已经开启了新的线程去执行了，不会阻塞doThread的运行范围；
    }

    def printThreadInfo(sql,index){
        def stmt = 'update t_test set time_col=now(), where id=?'
        def row = sql.executeUpdate(stmt, [index])
    }
}