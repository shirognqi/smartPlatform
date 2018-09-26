package com.tencent.smartplatform.Controller;

import com.tencent.smartplatform.Service.StartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {
    @Autowired
    StartService startService;
    @RequestMapping("/hello")
    public String index() {
        return "Hello Spring Boot";
    }

    @RequestMapping("/test")
    public String test(){
        return "Test";
    }

}
