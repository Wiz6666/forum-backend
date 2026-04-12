package com.weisizhang.forumbackend.controller;

import com.weisizhang.forumbackend.common.AppResult;
import com.weisizhang.forumbackend.exception.ApplicationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "TestController 测试接口")
@RestController
@RequestMapping("/test")
public class TestController {

    @Operation(summary = "测试接口1，打印hello")
    @GetMapping("/hello")
    public String hello() {
        return "Hello, Spring Boot...";
    }

    @Operation(summary = "测试接口2，返回 你好+传入参数")
    @GetMapping("/helloByName")
    public AppResult<String> helloByName(@Parameter(description = "名字", required = true) String name) {
        return AppResult.success("你好：" + name);
    }

    @Operation(summary = "测试接口3，返回一个异常结果")
    @GetMapping("/exception")
    public String testException() throws Exception {
        throw new Exception("这是一个Exception");
    }

    @Operation(summary = "测试接口4，返回一个自定义异常结果")
    @GetMapping("/appException")
    public String testApplicationException() {
        throw new ApplicationException("这是一个自定义的ApplicationException");
    }
}
