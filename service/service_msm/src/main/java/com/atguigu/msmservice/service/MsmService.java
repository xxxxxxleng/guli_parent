package com.atguigu.msmservice.service;

import java.util.Map;

public interface MsmService {

    // 发短信
    boolean send(Map<String, Object> param, String phone);
}
