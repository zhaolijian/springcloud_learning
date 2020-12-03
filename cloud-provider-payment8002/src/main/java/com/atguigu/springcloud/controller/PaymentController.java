package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping("/payment/create")
    // @RequestBody用来获取前端传递给后端的json字符串的数据（请求体中的数据），用于post请求，get请求没有请求体
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("******插入结果：" + result);
        if(result > 0){
            return new CommonResult(200, "插入数据成功,serverPort:" + serverPort, result);
        }else{
            return new CommonResult(444, "插入数据库失败");
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("******查询出结果：" + payment);
        if(payment != null){
            return new CommonResult(200, "查询数据成功,serverPort:" + serverPort, payment);
        }else{
            return new CommonResult(444, "查询数据失败");
        }
    }

    @GetMapping("/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }
}