package com.weisizhang.forumbackend.interceptor;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppInterceptorConfigurer implements WebMvcConfigurer {

    @Resource
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加登陆拦截器
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**") // 拦截所有请求
                .excludePathPatterns("/sign-in.html") //排除登录HTML
                .excludePathPatterns("/sign-up.html") //排除注册HTML
                .excludePathPatterns("/user/login") // 排除登录api接口
                .excludePathPatterns("/user/register") //排除注册api接口
                .excludePathPatterns("/user/logout") //排除退出api接口
                .excludePathPatterns("/swagger-ui.html") // Springdoc UI入口
                .excludePathPatterns("/swagger-ui/**") // Springdoc 静态资源
                .excludePathPatterns("/v3/api-docs/**") // OpenAPI 文档接口
                .excludePathPatterns("/dist/**")
                .excludePathPatterns("/image/**")
                .excludePathPatterns("/*.ico") //图标也排除
                .excludePathPatterns("/js/**");
    }


}
