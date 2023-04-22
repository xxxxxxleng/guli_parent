package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.commonutils.R;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-03-28
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    // redis
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 登录
    @Override
    public String login(UcenterMember member) {
        // 获取手机号，密码
        String mobile = member.getMobile();
        String password = member.getPassword();
        // 判断非空
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "登录失败");
        }
        // 判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);                            // 条件
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);     // 查数据库
        if (mobileMember == null) {     // 无该手机号
            throw new GuliException(20001, "登录失败");
        }
        // 判断密码 数据库密码加密了，直接对比不了，自己书的密码也加密再去对比 加密做法：MD5.encrypt(password)
        if (!MD5.encrypt(password).equals(mobileMember.getPassword())) {
            throw new GuliException(20001, "登录失败");
        }
        // 判断用户是否被禁用
        if (mobileMember.getIsDeleted()) {
            throw new GuliException(20001, "登录失败");
        }
        // 可以登录，使用jwt工具类生成token字符串
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        return jwtToken;
    }

    // 自定义注册方法
    @Override
    public void register(RegisterVo registerVo) {
        // 获取用户提交的数据
        String mobile = registerVo.getMobile();     // 手机号
        String password = registerVo.getPassword(); // 密码
        String code = registerVo.getCode();         // 验证码
        String nickname = registerVo.getNickname(); // 昵称
        // 判断非空
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)) {
            throw new GuliException(20001, "注册失败");
        }
        // 对比redis验证码
        String redisCode = redisTemplate.opsForValue().get(mobile); // redis中验证码
        if (!code.equals(redisCode)) {   // 不一样
            throw new GuliException(20001, "登录失败");
        }
        // 判断手机号是否重复
        //   获取手机号
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        // 判断
        if (count > 0) {
            throw new GuliException(20001, "登录失败");
        }
        //   封装数据
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setPassword(MD5.encrypt(password));      // 密码加密
        member.setNickname(nickname);
        member.setIsDeleted(false);         // 不禁用
//        member.setAvatar("");           // 头像
        // 存入数据库
        baseMapper.insert(member);
    }

    //判断数据表里面是否存在相同微信信息，根据openid判断
    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }
}
