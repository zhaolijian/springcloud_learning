package com.atguigu.springcloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    // 开启feign的详细日志（FULL, 记录请求和响应的头信息，正文和元数据。）
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }
}
