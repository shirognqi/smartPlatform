package com.tencent.smartplatform.Controller;

import com.tencent.smartplatform.Controller.Bean.RequestObj;
import com.tencent.smartplatform.Service.StartService;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class SmartPlatformController {
    @Autowired
    StartService startService;

    @RequestMapping("/innerIndex")
    public Object index(RequestObj requestObj) throws ResourceException, ScriptException, IOException {
        return startService.ScriptRun(requestObj);
    }

    @RequestMapping("/index")
    public String test(){
        return "Test";
    }

}
