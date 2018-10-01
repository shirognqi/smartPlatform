package developGroovyScript.aim.helloWorld


import com.tencent.smartplatform.Controller.Bean.RequestObj
import com.tencent.smartplatform.Service.EnvService
import groovy.json.JsonSlurper
import redis.clients.jedis.Jedis

import java.text.SimpleDateFormat

// env construction
EnvService env = (EnvService)env
RequestObj requestObj = (RequestObj)requestObj;

// map test
def ret = [:]
ret["theFirstTest"] = "hello, smart platform!"

// 获取参数测试
ret["requestTest"] = requestObj.getParams()

// 全局变量测试
ret["env"] = env.getEnvStr()
// MySQL连接池测试，获取连接池的配置，和init.groovy里的设置一样
def sql         = ConnectionUtils.getMySQLConnPool(env,"testMySQLPool")
def databaseRet = [:]
def i=0
sql.eachRow("SELECT * FROM t_test"){
    databaseRet[i] = "来自Mysql连接池C3P0数据源的数据：" + it["str"]
    i++
}

ret["dbDataTest"] = databaseRet
// 归还连接池连接，连接池最好手动回收，因为连接池自动回收需要耗费线程去回收，
// 这样做性能不是最好的，就给关闭了，一般手动回收；
sql.close()

// redis测试
SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
String systime = df.format(new Date())
Jedis redisConn   = ConnectionUtils.getRediseConnPool(env, "testRedisPool")
redisConn.set("aaa",systime)
ret["setCacheDataTest"] = "设定Redis的连接池Jedis数据：" + systime
def cacheDate = redisConn.get("aaa")
ret["getCacheDataTest"] = "来自于Redis的连接池Jedis的数据：" + cacheDate
// 归还连接池连接，连接池最好手动回收，因为连接池自动回收需要耗费线程去回收，
// 这样做性能不是最好的，就给关闭了，一般手动回收；
redisConn.close()

// 类单例测试
def doSomeWork = Job.instance.doSomeWork(env)
ret["doSomeWorkTest"] = doSomeWork

// JSON解析测试
def jsonStr = "{\"state\":{\"name\":\"smart platform\",\"developer\":\"kuanglong\"}}"
ret["jsonStr"] = jsonStr
def slurper = new JsonSlurper()
def jsonStrParse2Obj = slurper.parseText(jsonStr)
ret["jsonStrParse2ObjTest"] = jsonStrParse2Obj

// http请求测试
def header = [:]
header["User-Agent"] = "Mozilla/5.0 反正是随便写的，就给个demo"
def httpResponse = HttpUtils.doGet("http://127.0.0.1:8080/index", header,300,300,300)
ret["httpRequestText"] = httpResponse

return ret


import com.mchange.v2.c3p0.ComboPooledDataSource
import com.tencent.smartplatform.Service.EnvService
import groovy.sql.Sql

import java.sql.Connection

@Singleton
class ConnectionUtils {
    def static getMySQLConnPool(EnvService env, connectionName){
        ComboPooledDataSource mySQLConnectionPool = env.getMySQLPool(connectionName)
        Connection mySQLConn = mySQLConnectionPool.getConnection()
        def sql = new Sql(mySQLConn)
        return sql
    }
    def static getRediseConnPool(env, connectionName){
        def redisConnectionPool = env.getRedisPool(connectionName)
        def redisConn = redisConnectionPool.getResource()
        return redisConn
    }
}


import org.apache.http.HttpEntity
import org.apache.http.NameValuePair
import org.apache.http.client.ClientProtocolException
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils
@Singleton
class HttpUtils {

    static String doGet(String url,Map<String, String>header, Integer connTime=3000, Integer requestTime=3000,
                               Integer transferTime=3000) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = "";
        try {
            // 通过址默认配置创建一个httpClient实例
            httpClient = HttpClients.createDefault();
            // 创建httpGet远程连接实例
            HttpGet httpGet = new HttpGet(url);
            // 设置请求头信息，鉴权
            for(String key :header.keySet()){
                httpGet.setHeader(key, header.get(key))
            }

            // 设置配置请求参数
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connTime)    // 连接主机服务超时时间
                .setConnectionRequestTimeout(requestTime)                                       // 请求超时时间
                .setSocketTimeout(transferTime)                                                 // 数据读取超时时间
                .build();
            // 为httpGet实例设置配置
            httpGet.setConfig(requestConfig);
            // 执行get请求得到返回对象
            response = httpClient.execute(httpGet);
            // 通过返回对象获取返回数据
            HttpEntity entity = response.getEntity();
            // 通过EntityUtils中的toString方法将结果转换为字符串
            result = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace()
        } catch (IOException e) {
            e.printStackTrace()
        } finally {
            // 关闭资源
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    static String doPost(String url, Map<String, Object> paramMap, Map<String, String>header, Integer connTime=3000, Integer requestTime=3000,
                          Integer transferTime=3000) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        String result = "";
        // 创建httpClient实例
        httpClient = HttpClients.createDefault();
        // 创建httpPost远程连接实例
        HttpPost httpPost = new HttpPost(url);
        // 配置请求参数实例
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connTime)    // 设置连接主机服务超时时间
                .setConnectionRequestTimeout(requestTime)                                   // 设置连接请求超时时间
            .setSocketTimeout(transferTime)                                                 // 设置读取数据连接超时时间
                .build();
        // 为httpPost实例设置配置
        httpPost.setConfig(requestConfig);
        // 设置请求头
        for(String key :header.keySet()){
            httpPost.setHeader(key, header.get(key))
        }
        // 封装post请求参数
        if (null != paramMap && paramMap.size() > 0) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            // 通过map集成entrySet方法获取entity
            Set<Map.Entry<String, Object>> entrySet = paramMap.entrySet();
            // 循环遍历，获取迭代器
            Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> mapEntry = iterator.next();
                nvps.add(new BasicNameValuePair(mapEntry.getKey(), mapEntry.getValue().toString()));
            }

            // 为httpPost设置封装好的请求参数
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        try {
            // httpClient对象执行post请求,并返回响应参数对象
            httpResponse = httpClient.execute(httpPost);
            // 从响应对象中获取响应内容
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}


import redis.clients.jedis.Jedis

import java.text.SimpleDateFormat

@Singleton
class Job {

    def doSomeWork(env){


        return "doSomeWork!"
    }
}


@Singleton
class ThreadTest {

}
