package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.service.IMessageProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

import javax.annotation.Resource;
import java.util.UUID;

// @EnableBinding作用：将管道channel和exchange绑定到一起
// 定义消息的推送管道,生产者这是Source(源，生产水)，消费者这是Sink(水槽，盛水)
@EnableBinding(Source.class)
@Slf4j
public class MessageProviderImpl implements IMessageProvider {
    @Resource
    private MessageChannel output;       // 消息发送管道

    @Override
    public String send() {
        String serial = UUID.randomUUID().toString();
        // send(): 将消息发送到channel中
        // MessageBuilder： 默认的消息构建器
        // withPayload()参数是需要发送的消息
        // 调用build()方法产生一个Message对象
        output.send(MessageBuilder.withPayload(serial).build());
        log.info("******serial: " + serial);
        return null;
    }
}
