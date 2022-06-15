package com.fzg.imooc.aop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(@RequestParam("name") String name){
        return "Hello "+ name;
    }
    @GetMapping("hello1")
    public String hello1(@RequestParam("name") String name, @RequestParam("age") String age){
        return "Hello "+ name + " age=" + age;
    }
}

