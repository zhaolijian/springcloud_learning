package com.atguigu.springboot.alibaba.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// 支持nacos的动态刷新功能（刷新获取config server中的信息，无须微服务重启，只需要发送curl命令）
@RefreshScope
public class ConfigClientController {
    // 8848 nacos上的配置文件中的内容
    @Value("${config.info}")
    private String configInfo;

    @GetMapping("/config/info")
    public String getConfigInfo(){
        return configInfo;
    }
}
