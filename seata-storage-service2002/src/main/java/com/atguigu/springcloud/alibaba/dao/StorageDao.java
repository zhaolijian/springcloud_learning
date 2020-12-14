package com.atguigu.springcloud.alibaba.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StorageDao {
//    根据某个库存id修改数量
    void decrease(@Param("productId") Long productId, @Param("count") Integer count);
}
