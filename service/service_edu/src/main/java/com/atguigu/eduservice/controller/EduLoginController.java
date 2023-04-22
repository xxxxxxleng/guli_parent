package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import org.springframework.web.bind.annotation.*;

@CrossOrigin    // 解决跨域问题
@RestController
@RequestMapping("/eduService/user")
public class EduLoginController {

    // login
    @PostMapping("/login")
    public R login() {
        return R.ok().data("token", "admin");
    }

    // info
    @GetMapping("/info")
    public R info() {
        return R.ok().data("roles", "admin").data("name", "小冷").data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
