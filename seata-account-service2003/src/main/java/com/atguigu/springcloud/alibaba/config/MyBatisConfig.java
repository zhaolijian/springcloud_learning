package com.atguigu.springcloud.alibaba.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

//@Configuration
//告诉mybatis和dao层相关
//@dao层中配置了@Mapper注解，@MapperScan就不用写了，即该类没有存在的必要，如果dao层不使用@Mapper的话，使用@MapperScan可以免除@Mapper注解的使用
@MapperScan({"com.atguigu.springcloud.alibaba.dao"})
public class MyBatisConfig {
}
