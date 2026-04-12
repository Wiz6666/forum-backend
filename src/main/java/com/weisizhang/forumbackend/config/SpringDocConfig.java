package com.weisizhang.forumbackend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Springdoc OpenAPI 配置类
 * 等价于老师的 SwaggerConfig（Springfox 版本），但写法更简洁
 */
@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Forum论坛系统API")
                        .description("Forum论坛系统前后端分离API测试")
                        .version("1.0")
                        .contact(new Contact()
                                .name("weisizhang")
                                .url("https://github.com/Wiz6666")
                                .email("your-email@example.com")
                        )
                );
    }
}
