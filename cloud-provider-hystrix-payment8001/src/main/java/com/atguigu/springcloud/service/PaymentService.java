package com.atguigu.springcloud.service;

import org.springframework.web.bind.annotation.PathVariable;

public interface PaymentService {
    // 正常访问
    String paymentInfo_OK(Integer id);

    // 超时方法
    String paymentInfo_TimeOut(Integer id);

    // 服务熔断测试方法
    String paymentCircuitBreaker(@PathVariable("id") Integer id);
}
