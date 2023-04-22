package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component                   // 交给spring容器管理
// 调用哪个服务 fallback远程调用超时执行的类中的方法 熔断
@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)
public interface VodClient {

    // 定义调用方法的路径 远程调用service-vod服务
    // 根据视频id删视频 调用这个方法
    @DeleteMapping("/eduvod/video/removeAlyVideo/{id}")
    public R removeVideo(@PathVariable("id") String id);    // (@PathVariable后要加参数名称 不然报错

    // 定义调用方法的路径 远程调用service-vod服务
    // 删除多个阿里云视频 传多个视频id
    @DeleteMapping("/eduvod/video/delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
