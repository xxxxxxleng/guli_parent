package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-03-19
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    // 注入vodClient
    @Autowired
    private VodClient vodClient;

    // 根据课程id删小节
    @Override
    public void removeVideoByCourseId(String courseId) {
        // 1 根据课程id查课程所有视频id
        QueryWrapper<EduVideo> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("course_id", courseId);
        wrapper1.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(wrapper1);
        // List<EduVideo>转为 List<String>
        List<String> videoIds = new ArrayList<>();
        for (int i = 0; i < videoIds.size(); i++) {
            // 得到每个对象
            EduVideo eduVideo = eduVideoList.get(i);
            // 得到每个对象的id
            String videoSourceId = eduVideo.getVideoSourceId();
            // 不为空才放入
            if (!StringUtils.isNotEmpty(videoSourceId)) {
                // 存入 List<String>集合
                videoIds.add(videoSourceId);
            }
        }
        // 判断不为空才调用
        if (videoIds.size() > 0) {
            // 删除多个阿里云视频 传多个视频id
            vodClient.deleteBatch(videoIds);
        }

        // 删小节
        // delete()需要条件对象
        // todo 删小节还要把对应视频删了 写在了controller中
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }
}
