package com.tencent.smartplatform.Service;

import com.tencent.smartplatform.Controller.Bean.RequestObj;
import com.tencent.smartplatform.Service.Bean.ResultSchema;
import groovy.lang.Binding;
import groovy.lang.MissingPropertyException;
import groovy.lang.Script;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.tencent.smartplatform.Util.FileUtils.uniteSourceFile;
import static com.tencent.smartplatform.Util.LocalCache.*;
import static com.tencent.smartplatform.Util.TimeUtils.getCurrentTime;

@Service
public class StartService {
    @Value(value = "${envStr}")
    private String envStr;

    public Object ScriptRun(RequestObj requestObj) throws ResourceException, ScriptException, IOException {
        try {
            String startTime = getCurrentTime();
            Long startTimeLong = System.currentTimeMillis();
            String actionName       = "helloWorld";
            String actionDir        = "src/main/resources/" +this.envStr+ "GroovyScript/aim/"+actionName+"/";
            String oriActionDir     = "src/main/resources/" +this.envStr+ "GroovyScript/source/"+actionName+"/";
            String actionInitFile   = oriActionDir + "init.groovy";
            String actionFile       = actionDir + "main.groovy";

            tryToUpdateScript(actionName, oriActionDir, actionDir);

            // 获取初始化配置文件
            File initFile = new File(actionInitFile);
            File mainFile = new File(actionFile);
            if(!initFile.exists() || !mainFile.exists()){
                // 直接报警
                return "";
            }
            // 尝试加载配置文件
            Boolean initFileLoadState = tryToLoadInitFile(actionName, initFile, oriActionDir);
            // 尝试加载脚本
            Script script = tryToLoadMainScript(actionName, mainFile, actionDir, initFileLoadState);

            Binding binding = script.getBinding();
            binding.setVariable("requestObj", requestObj);
            script.setBinding(binding);
            Object result = script.run();
            ResultSchema resultSchema = new ResultSchema();
            resultSchema.setStartTime(startTime);
            resultSchema.setCost(System.currentTimeMillis()-startTimeLong);
            resultSchema.setData(result);
            resultSchema.setEnv(envStr);

            return resultSchema;


        }catch (MissingPropertyException missingPropertyException){
            return "class reload : " + missingPropertyException.getMessage();
        }catch (Exception e){
            return e.getMessage();
        }
    }


    /**
     * 获取缓存中配置文件的时间
     * @param actionName
     * @param initFile
     * @param oriActionDir
     * @return
     */
    public boolean tryToLoadInitFile(String actionName, File initFile, String oriActionDir){

        String initFileNameKey = "iniFile_" + actionName;
        Long fileCacheModifyTime = getFileModifyTime(initFileNameKey);
        // 获取真正配置文件时间
        Long initFileModifiedTime = initFile.lastModified();
        // 第一次加载配置文件
        if(fileCacheModifyTime == null)
        {
            try {
                // 初始化配置
                EnvService envService = new EnvService();
                envService.setEnvStr(envStr);
                Binding initBinding = new Binding();
                initBinding.setVariable("env", envService);
                GroovyScriptEngine engine = new GroovyScriptEngine(oriActionDir);
                engine.run("init.groovy", initBinding);
                putEnv(actionName, envService);
                putFileModifyTime(initFileNameKey, initFileModifiedTime);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                return true;
            }

        } else if(!initFileModifiedTime.equals(fileCacheModifyTime)) {
            try {
                EnvService envService = getEnv(actionName);
                if (envService != null) {
                    envService.cleanAll();
                }
                Binding initBinding = new Binding();
                initBinding.setVariable("env", envService);
                envService.setEnvStr(envStr);
                GroovyScriptEngine engine = new GroovyScriptEngine(oriActionDir);
                engine.run("init.groovy", initBinding);
                putEnv(actionName, envService);
                putFileModifyTime(initFileNameKey, initFileModifiedTime);

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                return true;
            }
        }
        return false;
    }


    public Script tryToLoadMainScript(String actionName, File mainFile, String actionDir, Boolean initFileLoadState){
        String mainFileNameKey = "mainFile_" + actionName;
        Long fileCacheModifyTime = getFileModifyTime(mainFileNameKey);
        // 获取真正执行文件时间
        Long mainFileModifiedTime = mainFile.lastModified();
        // 加载执行文件
        if(fileCacheModifyTime == null || !mainFileModifiedTime.equals(fileCacheModifyTime) || initFileLoadState)
        {
            try {
                Binding binding = new Binding();
                GroovyScriptEngine engine = new GroovyScriptEngine(actionDir);
                // 获取环境
                EnvService envService = getEnv(actionName);
                if(envService == null){
                    // 直接报警
                    return null;
                }
                binding.setVariable("env", envService);
                Script script = engine.createScript("main.groovy",binding);
                putScript(actionName, script);
                putFileModifyTime(mainFileNameKey, mainFileModifiedTime);
                return script;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        } else {
            return getScript(actionName);
        }
    }

    public void tryToUpdateScript(String actionName,String oriActionDir, String actionDir) throws Exception {
        File oriScriptDir   = new File(oriActionDir);
        String scriptDirKey = "script_dir_" + actionName;
        Long fileCacheModifyTime = getFileModifyTime(scriptDirKey);
        Long scriptDirModifiedTime = oriScriptDir.lastModified();

        if(fileCacheModifyTime==null || !scriptDirModifiedTime.equals(fileCacheModifyTime)){
            // 启动脚本引擎
            File[] files = oriScriptDir.listFiles();
            ArrayList<String> fileNames = new ArrayList<String>();
            fileNames.add(oriActionDir + "main.groovy");
            for (File it : files) {
                if(it.getName().equals("main.groovy") || it.getName().equals("init.groovy")){
                    continue;
                }
                fileNames.add(oriScriptDir+"//"+it.getName());
            }

            String aimPackageName = "package " + this.envStr+ "GroovyScript.aim." + actionName + "\n";
            String oriPackageName = "package"  + this.envStr+ "GroovyScript.source." + actionName;
            uniteSourceFile(fileNames, actionDir + "main.groovy",aimPackageName, oriPackageName);
            putFileModifyTime(scriptDirKey, scriptDirModifiedTime);
        }
    }










}
