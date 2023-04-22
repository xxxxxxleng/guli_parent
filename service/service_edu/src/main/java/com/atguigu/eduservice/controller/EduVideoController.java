package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器 小节
 * </p>
 *
 * @author testjava
 * @since 2023-03-19
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;

    // 添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return R.ok();
    }

    // 删除小节 todo 删小节还要删对应视频
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {     // 这是小节id
        // 根据小节id获取小节对象
        EduVideo eduVideo = videoService.getById(id);
        // 根据小节对象获取视频id
        String videoSourceId = eduVideo.getVideoSourceId();
        // 判断小节对象里是否有id 有就删 无不删
        if (!StringUtils.isNotEmpty(videoSourceId)) {
            // 删视频 根据上面得到的视频id远程调用实现删视频
            R result = vodClient.removeVideo(videoSourceId);// 需要视频id
            // 判断是否触发熔断机制
            if (result.getCode() == 20001) {
                throw new GuliException(20001, "删除失败，触发熔断器......");
            }
        }
        // 删小节
        videoService.removeById(id);
        return R.ok();
    }

    // 修改小节 todo

}

