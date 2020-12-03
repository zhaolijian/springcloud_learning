package com.atguigu.springcloud;

import com.atguigu.myrule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;


// 消费者微服务模块80端口，调用8001支付服务模块
@SpringBootApplication
@EnableEurekaClient
// Ribbon用于负载均衡，Eureka中默认使用轮询方式
// 自定义配置类（即configuration参数指定的配置类）不能放在@ComponentScan(@SpringBootApplication注解中有该注解)所扫描的当前包以及子包下(即springcloud包下)，
// 否则我们自定义的这个配置类就会被所有的Ribbon客户端所共享，不能达到特殊定制化的目的了。
//@RibbonClient(name = "CLOUD-PAYMENT-SERVICE", configuration = MySelfRule.class)
public class OrderMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMain80.class, args);
    }
}
