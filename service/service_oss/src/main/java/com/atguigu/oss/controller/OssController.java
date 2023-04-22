package com.atguigu.oss.controller;

import com.atguigu.commonutils.R;
import com.atguigu.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin    // 解决跨域问题
@RestController
@RequestMapping("/eduoss/fileoss")
public class OssController {

    @Autowired
    private OssService ossService;

    // 上传头像
    @PostMapping
    public R uploadOssFile(MultipartFile file) {
        // 获取上传文件
        // 返回上传到oss的路径
        String url = ossService.uploadOssFileAvatar(file);
        return R.ok().data("url", url);
    }


}
