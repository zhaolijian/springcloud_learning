package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface LoadBalancer {
    // 获得下一个实例
    ServiceInstance getNextInstance(List<ServiceInstance> serviceInstances);
}
