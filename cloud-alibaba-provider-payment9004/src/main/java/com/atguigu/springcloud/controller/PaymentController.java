package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@Slf4j
public class PaymentController {
    @Value("${server.port}")
    private String serverPort;

    public static HashMap<Long, Payment> hashMap = new HashMap<Long, Payment>();
    static {
        hashMap.put(1L, new Payment(1L, "f31612d4c5b0404bb2b34504b9498fcb"));
        hashMap.put(2L, new Payment(2L, "e8ee66c71c3c4901b2d4299212459535"));
        hashMap.put(3L, new Payment(3L, "e8ee66c71c3c4901b2d4299212459535"));
    }

    @GetMapping("/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id){
        Payment payment = hashMap.get(id);
        CommonResult<Payment> result = new CommonResult(200, "from mysql, serverPort: " + serverPort, payment);
        return result;
    }
}
