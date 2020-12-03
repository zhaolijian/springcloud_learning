package com.atguigu.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//因为是前后端分离,和前端交互传的是CommonResult的jsoin封装体
public class CommonResult<T> {
    // 编码，如404
    private Integer code;
    // 提示消息
    private String message;
    private T data;

    public CommonResult(Integer code, String message){
        this(code, message, null);
    }
}
