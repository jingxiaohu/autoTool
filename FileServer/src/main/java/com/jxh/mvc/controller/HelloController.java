package com.jxh.mvc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController extends BaseController{

    @RequestMapping("/index")
    @ResponseBody
    public String index() throws Exception {
    	System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        return "Greetings from Spring Boot!";
    }
}
