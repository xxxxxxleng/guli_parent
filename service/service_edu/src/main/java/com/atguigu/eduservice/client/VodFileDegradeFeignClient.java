package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient {
    // 根据视频id删视频，调用这个方法 该方法的兜底方法
    @Override
    public R removeVideo(String id) {
        return R.error().message("time out");
    }

    // 删除多个阿里云视频，传多个视频id 该方法的兜底方法
    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("time out");
    }
}
