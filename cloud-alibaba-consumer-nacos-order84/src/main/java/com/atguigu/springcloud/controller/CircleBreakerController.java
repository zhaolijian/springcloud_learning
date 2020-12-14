package com.atguigu.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentFallbackService;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class CircleBreakerController {
    @Value("${service.url}")
    private String SERVICE_URL;

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/fallback/{id}")
//    @SentinelResource(value = "fallback")
//    @SentinelResource(value = "fallback", blockHandler = "blockHanlder")
//    @SentinelResource(value = "fallback", fallback = "handlerFallback")
    // exceptionsToIgnore: 如果报该参数指定的异常，则不再有fallback兜底方法了，没有降级效果了。
    @SentinelResource(value = "fallback", blockHandler = "blockHandler", fallback = "handlerFallback", exceptionsToIgnore = {IllegalArgumentException.class})
    public CommonResult<Payment> fallback(@PathVariable("id") Long id){
        CommonResult<Payment> result = restTemplate.getForObject(SERVICE_URL + "/paymentSQL/" + id, CommonResult.class, id);
        log.info(result.getMessage() + id.toString());
        if(id == 4){
            throw new IllegalArgumentException("IllegalArgumentException, 非法参数异常");
        }else if(result.getData() == null){
            throw new NullPointerException("NullPointerException, 该ID没有对应记录，空指针异常");
        }
        return result;
    }

//    blockHandler
    public CommonResult blockHandler(@PathVariable("id") Long id, BlockException blockException){
        Payment payment = new Payment(id, "null");
        return new CommonResult<Payment>(445, "blockHandler-sentinel限流，blockException内容：" + blockException.getMessage(), payment);
    }

//    fallback兜底方法
    public CommonResult handlerFallback(@PathVariable("id") Long id, Throwable e){
        Payment payment = new Payment(id, "null");
        return new CommonResult<Payment>(444, "兜底异常handlerFallback,exception内容：" + e.getMessage(), payment);
    }

    //  OpenFeign
    @Resource
    private PaymentService paymentService;

    @GetMapping("/consumer/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id){
        return paymentService.paymentSQL(id);
    }
}
