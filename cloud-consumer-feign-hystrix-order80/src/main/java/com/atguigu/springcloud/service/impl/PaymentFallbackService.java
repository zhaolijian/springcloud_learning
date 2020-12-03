package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.service.PaymentHystrixService;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService {

    @Override
    public String paymentInfo_OK(Integer id) {
        return "******PaymentFallbackService fallback paymentInfo_OK,wuwu";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "******PaymentFallbackService fallback paymentInfo_TimeOut,wuwu";
    }
}
