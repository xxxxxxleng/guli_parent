package com.atguigu.educenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-03-28
 */
@CrossOrigin
@RestController
@RequestMapping("/educenter/member")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    // 登录
    @PostMapping("login")
    public R loginUser(@RequestBody UcenterMember member) {
        // 调登录方法
        String token = memberService.login(member);
        // 返回token值，使用jwt生成
        return R.ok().data("token", token);
    }

    // 注册
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo) {
        // 调自定义注册方法
        memberService.register(registerVo);
        return R.ok();
    }

    // 根据token获取用户信息
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        // 调jwt工具类方法，根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        // 根据id获取用户信息
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("userInfo", member);
    }

    // 根据用户id查用户对象 供order服务远程调用
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id) {
        // 根据id获取用户信息
        UcenterMember member = memberService.getById(id);
        // 对象拷贝，转换成UcenterMemberOrder类型返回
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member, ucenterMemberOrder);
        return ucenterMemberOrder;
    }

}

