package com.atguigu.oss.service.impl;

import com.aliyun.oss.*;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    // 上传文件
    @Override
    public String uploadOssFileAvatar(MultipartFile file) {
        //Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            //上传文件流
            InputStream inputStream = file.getInputStream();
            // 获取文件名称
            String filename = file.getOriginalFilename();
            // 生成随机数字
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            // 重新组成名字
            filename = uuid + filename;
            // 将文件按日期分类 2023/3/18
            String datePath = new DateTime().toString("yyyy/MM/dd");
            // 拼接 2023/3/18/dkfs.jpg
            filename = datePath + "/" + filename;
            // 调用oss方法实现上传
            ossClient.putObject(bucketName, filename, inputStream);
            //关闭osSClient。
            ossClient.shutdown();
            // 手动返回文件地址
            String url = "https://" + bucketName + "." + endpoint + "/" + filename;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
