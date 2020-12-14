package com.atguigu.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class FlowLimitController {
    @GetMapping("/testA")
    public String testA(){
        return "******testA";
    }

    @GetMapping("/testB")
    public String testB(){
        log.info(Thread.currentThread().getName() + "\t" + "...testB");
        return "******testB";
    }

    @GetMapping("/testD")
    public String testD(){
        try{
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("testD 测试RT（平均响应时间）");
        return "******testD";
    }

    @GetMapping("/testE")
    public String testE(){
        log.info("testE 异常比例");
        int age = 10/0;
        return "******testE";
    }

    @GetMapping("/testF")
    public String testF(){
        log.info("testF 异常数");
        int age = 10/0;
        return "******testF";
    }

    @GetMapping("/testHotKey")
    // value = "testHotKey"：对应热点规则中的资源名，blockHandler:违背时的处理方法，如果没有配置该参数，则出现异常时会返回错误页面，而不是sentinel系统默认提示
    @SentinelResource(value = "testHotKey", blockHandler = "deal_testHotKey")
    public String testHotKey(@RequestParam(value = "p1", required = false) String p1,
                             @RequestParam(value = "p2", required = false) String p2){
        return "******testHotKey";
    }

    public String deal_testHotKey(String p1, String p2, BlockException blockException){
        // sentinel系统默认提示：blocked by sentinel（flow limiting）
        return "******deal_testHotKey, wuwu";
    }
}
