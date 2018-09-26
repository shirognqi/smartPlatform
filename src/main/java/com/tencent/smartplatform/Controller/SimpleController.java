package com.tencent.smartplatform.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    @RequestMapping("/hello")
    public String index() {
        return "Hello Spring Boot";
    }

    @RequestMapping("/test")
    public String test(){
        return "Test";
    }

}
