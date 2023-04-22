package com.atguigu.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义异常类
 */
@Data
@AllArgsConstructor     // 有参
@NoArgsConstructor      // 无参
public class GuliException extends RuntimeException {
    // 状态码
    private Integer code;

    // 信息
    private String msg;
}
