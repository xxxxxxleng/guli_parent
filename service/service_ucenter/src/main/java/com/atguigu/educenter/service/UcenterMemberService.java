package com.atguigu.educenter.service;

import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-03-28
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    // 登录方法
    String login(UcenterMember member);

    // 自定义注册方法
    void register(RegisterVo registerVo);

    //判断数据表里面是否存在相同微信信息，根据openid判断
    UcenterMember getOpenIdMember(String openid);
}
