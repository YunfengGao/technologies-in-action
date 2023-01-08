package com.github.yunfeng.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Demo {
    @GetMapping("/message")
    @SentinelResource("HelloWorld")
    public String getMessage(@RequestParam String userId) {
        return userId;
    }
}
