package com.atguigu.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
// 消费者这时Sink(中文水槽，盛水),生产者这是Source(源头，生产水)
@EnableBinding(Sink.class)
@Slf4j
public class ReceiveMessageListenerController {

    @Value("${server.port}")
    private String serverPort;

    // 监听队列，用于消费者队列的消息接收
    @StreamListener(Sink.INPUT)
    public void input(Message<String> message){
        log.info("消费者2号，------>接收到的消息：" + message.getPayload() + "\t port: " + serverPort);
    }
}
