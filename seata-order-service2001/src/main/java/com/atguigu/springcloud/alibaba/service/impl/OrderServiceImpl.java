package com.atguigu.springcloud.alibaba.service.impl;

import com.atguigu.springcloud.alibaba.dao.OrderDao;
import com.atguigu.springcloud.alibaba.domain.Order;
import com.atguigu.springcloud.alibaba.service.AccountService;
import com.atguigu.springcloud.alibaba.service.OrderService;
import com.atguigu.springcloud.alibaba.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao orderDao;

    @Resource
    private StorageService storageService;

    @Resource
    private AccountService accountService;

    @Override
//    name只要保证唯一性就行，rollbackFor = Exception.class：只要遇到异常就会回滚
    @GlobalTransactional(name = "fsp-create-order", rollbackFor = Exception.class)
//    下订单-减库存-减金额-改状态
    public void create(Order order) {
        log.info("------>开始新建订单");
        orderDao.create(order);
        log.info("------>订单微服务开始调用库存，做扣减库存操作");
        storageService.decrease(order.getProductId(), order.getCount());
        log.info("------>订单微服务开始调用库存，做扣减库存操作end");
        log.info("------>订单微服务开始调用账户，做扣钱操作");
        accountService.decrease(order.getUserId(), order.getMoney());
        log.info("------>订单微服务开始调用账户，做扣钱操作end");
        log.info("------>订单微服务开始调用订单，做修改订单状态");
        orderDao.update(order.getUserId(), 0);
        log.info("------>订单微服务开始调用订单，做修改订单状态end");
        log.info("------>下订单操作结束");
    }
}
