package com.weisizhang.forumbackend.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

// 指定MyBatis的扫描范围
// 在 Spring Boot 3 配合 MyBatis-Plus 环境下，仅需此注解即可，无需手动配置 SqlSessionFactory
@Configuration
@MapperScan("com.weisizhang.forumbackend.dao")
public class MyBatisConfig {
}
