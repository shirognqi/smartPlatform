package com.tencent.smartplatform.Util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.tencent.smartplatform.Service.EnvService;
import groovy.lang.Script;

import java.util.concurrent.Callable;

public class LocalCache {

    private static Cache<String, Long>       initFileModifyTime = CacheBuilder.newBuilder().maximumSize(1000).recordStats().build();
    private static Cache<String, EnvService> env            = CacheBuilder.newBuilder().maximumSize(1000).recordStats().build();
    private static Cache<String, Script>     script         = CacheBuilder.newBuilder().maximumSize(1000).recordStats().build();


    /**
     *  获取文件更新时间；
     */

    public static Long getFileModifyTime(String key){

        try {
            Long var = (Long) initFileModifyTime.get(key, new Callable<Long>() {
                @Override
                public Long call() throws Exception {
                    return null;
                }
            });
            return var;
        }catch (Exception e){
            return null;
        }
    }

    /**
     *  缓存的MySQL连接池；
     */
    public static void putFileModifyTime(String key, Long value) {
        initFileModifyTime.put(key, value);
    }


    /**
     *  获取文件更新时间；
     */

    public static EnvService getEnv(String key){

        try {
            EnvService var = (EnvService) env.get(key, new Callable<EnvService>() {
                @Override
                public EnvService call() throws Exception {
                    return null;
                }
            });
            return var;
        }catch (Exception e){
            return null;
        }
    }

    /**
     *  缓存的MySQL连接池；
     */
    public static void putEnv(String key, EnvService value) {
        env.put(key, value);
    }


    /**
     *  获取文件更新时间；
     */

    public static Script getScript(String key){

        try {
            Script var = (Script) script.get(key, new Callable<Script>() {
                @Override
                public Script call() throws Exception {
                    return null;
                }
            });
            return var;
        }catch (Exception e){
            return null;
        }
    }

    /**
     *  缓存的MySQL连接池；
     */
    public static void putScript(String key, Script value) {
        script.put(key, value);
    }

}
