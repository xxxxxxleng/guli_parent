package com.atguigu.msmservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.msmservice.service.MsmService;
import com.atguigu.msmservice.utils.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/edumsm/msm")
public class MsmController {

    // 短信发送服务
    @Autowired
    private MsmService msmService;

    // redis服务
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 发短信
    @GetMapping("send/{phone}")
    public R sendMsm(@PathVariable String phone) {
        // 1 redis中能取到就不重新发了
        String code = redisTemplate.opsForValue().get(phone);
        if (StringUtils.isNotEmpty(code)) {
            return R.ok();
        }

        // 2 获取不到，重新发
        // 生成随机数
        code = RandomUtil.getFourBitRandom();
        // 构建map集合传参
        Map<String, Object> param = new HashMap<>();
        // 随机数存入集合
        param.put("code", code);
        // 控制台输出验证码
        log.info("验证码=============================>" + code);
        // 发短信
//        boolean isSend = msmService.send(param, phone);
        // 判断是否发送成功
        if (StringUtils.isNotEmpty(code)) {
            // 验证码存入redis 并设置有效时间5分钟 (TimeUnit.MINUTES的意思)
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return R.ok();
        } else {
            return R.error().message("短信发送失败.......");
        }
    }
}
