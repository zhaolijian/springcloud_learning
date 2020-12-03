package com.atguigu.springcloud.service.impl;

import cn.hutool.core.util.IdUtil;
import com.atguigu.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "线程池：" + Thread.currentThread().getName() + "paymentInfo_OK,id:" + id + "\t" + "haha";
    }

    @Override
    // 执行时间超过3s，执行降级后的paymentInfo_TimeOutHandler方法，否则执行paymentInfo_TimeOut方法
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "3000")})
    public String paymentInfo_TimeOut(Integer id) {
        int timeNumber = 2;
        try{
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        int age = 10/0;
        return "线程池：" + Thread.currentThread().getName() + "paymentInfo_TimeOut,id:" + id + "\t" + "haha" + "   耗时" + timeNumber + "秒钟";
    }

    public String paymentInfo_TimeOutHandler(Integer id){
        return "线程池：" + Thread.currentThread().getName() + "系统繁忙或运行报错，请稍后再试,id:" + id + "\t" + "wuwu";
    }

    //    服务熔断部分
    // 在10次请求中若失败率大于等于60%，则开启服务熔断，并在10000ms=10s后再次尝试请求服务
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),    // 开启服务熔断
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),   // 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),     // 在10s后重试看看能不能调用服务
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),         // 失败率阈值，若高于这个值则开启服务熔断
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        // 负数异常出错，非负数正常
        if(id < 0){
            throw new RuntimeException("******id不能为负数");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t" + "调用成功，流水号：" + serialNumber;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id不能为负数，请稍后再试，wuwu, id:" + id;
    }
}
